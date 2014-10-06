package br.com.vendas.services.user;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserDAO;
import br.com.vendas.dao.user.UserRoleDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.converter.MenuApplicationConverter;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.domain.user.UserRole;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.pojo.user.UserPojo;
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
public class UserServiceImpl implements UserService{
	

	@Inject
	private UserDAO userDAO;
	
	@Inject
	private UserRoleDAO userRoleDAO;
	
	@Inject
	private UserRoleService userRoleService;


	@Override
	public ServiceResponse<List<UserPojo>> findAllByOrganizationID(Integer organizationID, Integer offset) {
		List<User> users = userDAO.findAllByOrganizationID(organizationID, offset, LimitQuery.LIMIT_USER.value());
		List<UserPojo> usersPojo = new ArrayList<>();
		for (User user : users) {
			/*Hibernate.initialize(user.getMenusApplication());
			Hibernate.initialize(user.getUserBranches());
			Hibernate.initialize(user.getUserRoles());*/
			UserPojo userPojo = new UserPojo(user, null, null, null);
			usersPojo.add(userPojo);
		}
		return ServiceResponseFactory.create(usersPojo);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<User> save(User user) {
		user.setChangeTime(new GregorianCalendar());
		ServiceResponse<User> toReturn = ServiceResponseFactory.create(userDAO.save(user));
		userRoleService.saveDefaultRoles(toReturn.getValue());
		return toReturn;
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<User> saveOrUpdate(User user) throws RegistrationException {
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
		

		
		user.setChangeTime(new GregorianCalendar());
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
		}
		return newUser;	
	}

	/**
	 * Retorna o usuario por email. Carrega todos os objetos lazy e seta no pojo.
	 */
	@Override
	public ServiceResponse<UserPojo> findUserByEmail(String email) {
		User user = userDAO.findUserByEmail(email);
		UserPojo userPojo = null;
		if(user != null){
			Hibernate.initialize(user.getMenusApplication());
			Hibernate.initialize(user.getUserBranches());
			Hibernate.initialize(user.getUserRoles());
			userPojo = new UserPojo(user, user.getMenusApplication() , user.getUserBranches(), user.getUserRoles());
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
	public ServiceResponse<List<UserPojo>> findUsersByUserIDOrNameOrEmail(
			String filter, Integer organizationID, Integer offset) {
		Integer userID = 0;
		try{
			userID = Integer.parseInt(filter);
		} catch(NumberFormatException  e){}

		List<User> users = userDAO.findUsersByUserIDOrNameOrEmail(filter, organizationID,userID, offset, LimitQuery.LIMIT_USER.value());
		List<UserPojo> usersPojo = new ArrayList<>();
		for (User user : users) {
			UserPojo userPojo = new UserPojo(user, null, null, null);
			usersPojo.add(userPojo);
			//Hibernate.initialize(user.getMenusApplication());
		}
		return ServiceResponseFactory.create(usersPojo);
	}

}
