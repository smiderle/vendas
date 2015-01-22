package br.com.vendas.api.rest.v1.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.builder.CustomerBuilder;
import br.com.vendas.builder.InstallmentBuilder;
import br.com.vendas.builder.PriceTableBuilder;
import br.com.vendas.builder.ProductBuilder;
import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.domain.user.Role;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.domain.user.UserProfile;
import br.com.vendas.domain.user.UserRole;
import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.application.MenuApplicationService;
import br.com.vendas.services.customer.CustomerService;
import br.com.vendas.services.order.installment.InstallmentService;
import br.com.vendas.services.order.pricetable.PriceTableService;
import br.com.vendas.services.organization.BranchOfficeService;
import br.com.vendas.services.organization.OrganizationService;
import br.com.vendas.services.product.ProductService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserBranchOfficeService;
import br.com.vendas.services.user.UserProfileService;
import br.com.vendas.services.user.UserRoleService;
import br.com.vendas.services.user.UserService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.HTTPStatusCode;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/public/v1/signin")
@Controller
public class PublicController {
		
	private final static Integer INITIAL_BRANCH_OFFICE_ID = 1;
	@Inject
	private OrganizationService organizationService;
	
	@Inject
	private BranchOfficeService branchOfficeService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private UserBranchOfficeService userBranchOfficeService;
		
	@Inject 
	private UserRoleService userRoleService;
	
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
	
	private static final Logger LOG = Logger.getLogger(PublicController.class);
	
	@RequestMapping(value="generateNewUser", method = RequestMethod.POST)
	public @ResponseBody ApiResponse generateNewUser(String organizationName, String userName, String email, String password){
		final ApiResponse apiResponse = new ApiResponse(); 
		try {
			
			
			ServiceResponse<UserDTO> findUserByEmail = userService.findUserByEmail( email );
			
			if( findUserByEmail.getRowCount() > 0 ) {
				String message = "Esse e-mail já esta cadastro em nosso sistema.";
				apiResponse.setPayload(new VendasExceptionWapper(HTTPStatusCode.SERVER_ERROR_500.getCode(), message));
				apiResponse.setMessage(message);
				apiResponse.setCode("500");
				return apiResponse;
			}
			
			
			LOG.debug("Criando novo Usuário para o sistema");
			//Cria uma epresa
			Organization newOrganization = organizationService.save(new Organization(organizationName)).getValue();
			
			//Cria uma filial
			BranchOffice branchOffice = new BranchOffice(INITIAL_BRANCH_OFFICE_ID,newOrganization,organizationName,organizationName, "L", "L");
			
			BranchOffice newBranchOffice = branchOfficeService.save(null, branchOffice).getValue();
			
			//Cria um usuario
			User user = new User(newOrganization.getOrganizationID(),email,password,userName,true,true);
			user.setPictureUrl("https://s3-sa-east-1.amazonaws.com/vendas.pictures.product/23ab5f12-fd42-4f54-a17d-775e1d63eb0a");		
			user.setSkype("skype");
			user.setLinkFacebook("facebook");
			user.setLinkGooglePlus("googlePluss");
			user.setPhoneNumber("12345678");
			user.setCellPhone("12346454");			

			//Seta todos os menus para o usuario.
			Set<MenuApplication> menusApplication = new HashSet<>(menuApplicationService.findAll().getValue());
			user.setMenusApplication(menusApplication);
			User newUser = userService.save(user).getValue();
			
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
			
			
			//Cria alguns produtos
			productService.saveOrUpdate( new ProductBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(), 
					userBranchOffice.getBranchOffice().getBranchOfficeID()));
			
			//Criar alguns clientes
			customerService.save( new CustomerBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(), 
					userBranchOffice.getBranchOffice().getBranchOfficeID()) );
			
			//Criar algumas tabelas de preços
			priceTableService.save(new PriceTableBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(), 
					userBranchOffice.getBranchOffice().getBranchOfficeID()));
			
			//Criar alguns parcelamentos
			installmentService.save(new InstallmentBuilder().create(userBranchOffice.getBranchOffice().getOrganization().getOrganizationID(), 
					userBranchOffice.getBranchOffice().getBranchOfficeID()));
			
			
			
			apiResponse.setPayload(newUser);
			apiResponse.setCode(HTTPStatusCode.SUCESS_200.getCode());
		} catch (Exception e) {
			LOG.error(e);
			apiResponse.setPayload(new VendasExceptionWapper(HTTPStatusCode.SERVER_ERROR_500.getCode(),e.getMessage()));
			apiResponse.setMessage("Ocorreu algum problema. Nossa equipe já esta trabalhando para resolver. Tente novamente em alguns instantes.");
		}
		return apiResponse;
	}

}
