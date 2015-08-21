package br.com.vendas.services.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.builder.CustomerBuilder;
import br.com.vendas.builder.InstallmentBuilder;
import br.com.vendas.builder.PriceTableBuilder;
import br.com.vendas.builder.ProductBuilder;
import br.com.vendas.dao.user.UserDAO;
import br.com.vendas.dao.user.UserRoleDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.application.License;
import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.domain.user.Role;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.domain.user.UserProfile;
import br.com.vendas.domain.user.UserRole;
import br.com.vendas.enumeration.VersionType;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.exception.VendasException;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.application.LicenseService;
import br.com.vendas.services.application.MenuApplicationService;
import br.com.vendas.services.customer.CustomerService;
import br.com.vendas.services.order.installment.InstallmentService;
import br.com.vendas.services.order.pricetable.PriceTableService;
import br.com.vendas.services.organization.BranchOfficeService;
import br.com.vendas.services.organization.OrganizationService;
import br.com.vendas.services.product.ProductService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
/**
 * 
 * @author Ladair C. Smiderle Junior 06/08/2014 - 21:35:06
 * Em todos os metodos onde retorna o usuario, após o retorno dos usuarios do DAO, é utilizado o Hibernate.initialize para inicializar a
 * lista de menus, pois na classe User os menus estão como Lazy pois, se deixar com EAGER,
 * por causa do joins que são feitos internamentes pelo hibernate, nem todos os registros são retornados.
 *
 */

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService {

	public static final int DIAS_DEMONSTRACAO = 20;



	private final static Integer INITIAL_BRANCH_OFFICE_ID = 1;

	@Inject
	private UserDAO userDAO;

	@Inject
	private UserRoleDAO userRoleDAO;

	@Inject
	private UserRoleService userRoleService;

	@Inject
	private UserActionService userActionService;

	@Inject
	private OrganizationService organizationService;

	@Inject
	private BranchOfficeService branchOfficeService;


	@Inject
	private UserBranchOfficeService userBranchOfficeService;

	@Inject
	private UserProfileService userProfileService;

	@Inject
	private MenuApplicationService menuApplicationService;

	@Inject
	private ProductService productService;

	@Inject
	private CustomerService customerService;

	@Inject
	private PriceTableService priceTableService;

	@Inject
	private InstallmentService installmentService;

	@Inject
	private LicenseService licenseService;



	@Override
	public ServiceResponse<List<UserDTO>> findAllByOrganizationID(Integer organizationID, Integer offset) {
		List<User> users = userDAO.findAllByOrganizationID(organizationID, offset, LimitQuery.LIMIT_USER.value());
		List<UserDTO> usersPojo = new ArrayList<>();
		for (User user : users) {
			/*Hibernate.initialize(user.getMenusApplication());
			Hibernate.initialize(user.getUserBranches());
			Hibernate.initialize(user.getUserRoles());*/
			UserDTO userPojo = new UserDTO(user, null, null, null);
			usersPojo.add(userPojo);
		}
		return ServiceResponseFactory.create(usersPojo);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<User> save(User user) {
		user.setChangeTime(new Date());
		ServiceResponse<User> toReturn = ServiceResponseFactory.create(userDAO.save(user));
		//userRoleService.saveDefaultRoles(toReturn.getValue());
		return toReturn;
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<User> saveOrUpdate(Integer fromUserID, User user) throws RegistrationException {
		boolean isNewUser = user.getUserID() == null;

		List<UserBranchOffice> userBranches = null;
		Set<UserRole> userRoles = null;

		//Se for um novo usuario e o email já estiver cadastrado
		if(isNewUser){
			if(!isAvailableEmail(user.getEmail()).getValue()){
				throw new RegistrationException("80","Esse email já esta sendo utilizado por outro usuário");
			}
			//Seta null nas filiais se o usuario for novo, pois o usuario ainda não tem um ID. Posteriormente, após salvar é setado o userID que foi gerado,e é feito o update/insert das filiais.
			userBranches = user.getUserBranches();
			userRoles = user.getUserRoles();
			user.setUserBranches(null);
			user.setUserRoles(null);
		} else {
			//Remove as permissões de acesso do usuario para inserir posteriormente, pois no update não estava removendo da tabela USUARIO_PERMISSAO
			userRoleDAO.deleteByUserID(user.getUserID());
		}



		user.setChangeTime(new Date());
		ServiceResponse<User> newUser = ServiceResponseFactory.create(userDAO.saveOrUpdate(user));

		if(isNewUser){
			Integer userID = newUser.getValue().getUserID();
			for (UserBranchOffice userBranchOffice : userBranches) {
				userBranchOffice.setUserID(userID);
			}


			for (UserRole userRole : userRoles) {
				userRole.setUserID(userID);
			}
			user.setUserBranches(userBranches);
			user.setUserRoles(userRoles);

			UserAction userSave = UserActionHelper.createUserSave(fromUserID, user);
			userActionService.save(userSave);
		} else {

			UserAction userEdit = UserActionHelper.createUserEdit(fromUserID, user);
			userActionService.save(userEdit);

		}
		return newUser;
	}

	/**
	 * Retorna o usuario por email. Carrega todos os objetos lazy e seta no pojo.
	 */
	@Override
	public ServiceResponse<UserDTO> findUserByEmail(String email) {
		User user = userDAO.findUserByEmail(email);
		UserDTO userPojo = null;
		if(user != null){
			Hibernate.initialize(user.getMenusApplication());
			Hibernate.initialize(user.getUserBranches());
			Hibernate.initialize(user.getUserRoles());
			userPojo = new UserDTO(user, user.getMenusApplication() , user.getUserBranches(), user.getUserRoles());
			//userPojo = new UserPojo(user, null , null, null);
		}
		return ServiceResponseFactory.create(userPojo);
	}

	@Override
	public ServiceResponse<Boolean> isAvailableEmail(String email) {
		return ServiceResponseFactory.create(userDAO.findUserByEmail(email) == null);
	}

	/**
	 * Converte o filtro para um long para setar no usuarioID, pois pode ser que no filtro tenha sido digitado o código do usuario.
	 */
	@Override
	public ServiceResponse<List<UserDTO>> findUsersByUserIDOrNameOrEmail(
			String filter, Integer organizationID, Integer offset) {
		Integer userID = 0;
		try{
			userID = Integer.parseInt(filter);
		} catch(NumberFormatException  e){}

		List<User> users = userDAO.findUsersByUserIDOrNameOrEmail(filter, organizationID,userID, offset, LimitQuery.LIMIT_USER.value());
		List<UserDTO> usersPojo = new ArrayList<>();
		for (User user : users) {
			UserDTO userPojo = new UserDTO(user, null, null, null);
			usersPojo.add(userPojo);
			//Hibernate.initialize(user.getMenusApplication());
		}
		return ServiceResponseFactory.create(usersPojo);
	}

	@Override
	public ServiceResponse<List<UserDTO>> findOtherUsersByOrganizationID(Integer organizationID, Integer userID, Integer offset) {
		List<User> users = userDAO.findOtherUsersByOrganizationID(organizationID, userID,offset, LimitQuery.LIMIT_USER.value());
		List<UserDTO> usersPojo = new ArrayList<>();
		for (User user : users) {
			/*Hibernate.initialize(user.getMenusApplication());
			Hibernate.initialize(user.getUserBranches());
			Hibernate.initialize(user.getUserRoles());*/
			UserDTO userPojo = new UserDTO(user, null, null, null);
			usersPojo.add(userPojo);
		}
		return ServiceResponseFactory.create(usersPojo);
	}

	@Override
	public ServiceResponse<UserDTO> findUserByID(Integer userID) {
		User user = userDAO.findUserByID(userID);

		UserDTO userPojo = null;
		if(user != null){
			//Hibernate.initialize(user.getMenusApplication());
			//Hibernate.initialize(user.getUserBranches());
			//Hibernate.initialize(user.getUserRoles());
			userPojo = new UserDTO(user,null ,null, null);
		}
		return ServiceResponseFactory.create(userPojo);
	}


	@Override
	public ServiceResponse<List<UserDTO>> findAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {

		List<User> users = userDAO.findAllByChangeGreaterThan(new Date(date), organizationID, offset, LimitQuery.LIMIT_SYNC_INIT_LOAD.value());

		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			Hibernate.initialize(user.getUserBranches());

			UserDTO userPojo = new UserDTO(user, null, user.getUserBranches(), null);
			usersDTO.add(userPojo);
		}

		return ServiceResponseFactory.create(usersDTO);

	}

	@Override
	public ServiceResponse<UserDTO> findUserBasicByEmail(String email) {


		User user = userDAO.findUserByEmail(email);
		UserDTO userPojo = null;
		if(user != null){
			userPojo = new UserDTO(user, null , null,null);
		}

		return ServiceResponseFactory.create(userPojo);

	}

	@Override
	public ServiceResponse<UserDTO> findUserByEmailAndPassword(String email, String password) throws VendasException {

		User user = userDAO.findUserByEmailAndPassword( email, password );

		UserDTO userPojo = null;

		if( user != null ) {

			userPojo = new UserDTO(user, null , null, null );

		} else {

			throw new VendasException( "Usuário ou Senha inválidos" );
		}

		return ServiceResponseFactory.create(userPojo);
	}

	@Override
	@Transactional(readOnly=false)
	public ServiceResponse<UserDTO> generateNewUser(String organizationName, String userName, String email, String password, String serial) throws VendasException {


		ServiceResponse<UserDTO> findUserByEmail = findUserByEmail( email );

		if( findUserByEmail.getRowCount() > 0 ) {

			String message = "Esse e-mail já esta cadastro em nosso sistema.";

			throw new VendasException( message );
		}



		//Cria uma epresa
		Organization newOrganization = organizationService.save(new Organization(organizationName)).getValue();

		//Cria uma filial
		BranchOffice branchOffice = new BranchOffice(INITIAL_BRANCH_OFFICE_ID,newOrganization,organizationName,organizationName, "L", "L");

		BranchOffice newBranchOffice = branchOfficeService.save(null, branchOffice).getValue();

		//Cria um usuario
		User user = new User(newOrganization.getOrganizationID(),email,password,userName,true,true);
		user.setPictureUrl("https://s3-sa-east-1.amazonaws.com/vendas.pictures.default/30b29b76-0ae7-11e5-a6c0-1697f925ec7b-user.jpg");
		user.setSkype("skype");
		user.setLinkFacebook("facebook");
		user.setLinkGooglePlus("googlePluss");
		user.setPhoneNumber("12345678");
		user.setCellPhone("12346454");

		//Seta todos os menus para o usuario.
		Set<MenuApplication> menusApplication = new HashSet<>( menuApplicationService.findAll().getValue() );
		user.setMenusApplication(menusApplication);

		user.setChangeTime(new Date());
		User newUser = userDAO.save(user);


		//Cria o usuario-filial
		UserBranchOffice userBranchOffice = new UserBranchOffice(newBranchOffice,newUser.getUserID(), true);
		userBranchOfficeService.save(userBranchOffice);


		//Cria roles para o usuario
		List<UserRole> userRoles= new ArrayList<UserRole>();
		userRoles.add(new UserRole(newUser.getUserID(), Role.ROLE_USER));
		userRoles.add(new UserRole(newUser.getUserID(), Role.ROLE_ADMIN));
		//user.setUserRoles(userRoles);


		userRoleService.save(userRoles);


		//Cria o perfil do usuario
		UserProfile userProfile = new UserProfile();
		userProfile.setBirthDate(new Date());
		userProfile.setPhoneNumber("1234567890");
		userProfile.setUserID(newUser.getUserID());
		userProfileService.save(userProfile);


		License license = new License();
		license.setChangeTime( new Date() );
		license.setRegistrationDate( new Date() );
		license.setSerial(serial);
		license.setUser(newUser);
		license.setVersionType(VersionType.DEMONSTRACAO);

		licenseService.save(license);


		Calendar calendar = new GregorianCalendar();
		calendar.add( Calendar.DAY_OF_MONTH, DIAS_DEMONSTRACAO );

		license.setExpirationDate( calendar.getTime() );


		//Cria alguns produtos
		productService.saveOrUpdate( new ProductBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(),
				userBranchOffice.getBranchOffice().getBranchOfficeID()));

		//Criar alguns clientes
		customerService.save( new CustomerBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(),
				userBranchOffice.getBranchOffice().getBranchOfficeID()) );

		//Criar algumas tabelas de preços
		priceTableService.save(new PriceTableBuilder().create( userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(),
				userBranchOffice.getBranchOffice().getBranchOfficeID() ));

		//Criar alguns parcelamentos
		installmentService.save(new InstallmentBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(),
				userBranchOffice.getBranchOffice().getBranchOfficeID()));

		UserDTO userPojo = new UserDTO(newUser, null, null, null);

		return ServiceResponseFactory.create(userPojo);


	}

}
