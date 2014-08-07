package br.com.vendas.api.user;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserTest;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserRoleService;
import br.com.vendas.services.user.UserService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/user")
@Controller
public class UserController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Inject
	private UserService service;
	
	@Inject
	private UserRoleService userRoleService;

	/**
	 * Retorna todos os usarios de determinada empresa.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getAllByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByOrganizationID(Long organizationID, Integer offset){
		try {
			ServiceResponse<List<User>> payload =  service.findAllByOrganizationID(organizationID, offset);
			LOG.debug("List<User> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

	/**
	 * Retorna o usuario por email.
	 * @param email
	 * @return
	 */
	@RequestMapping(value="findUserByEmail", method = RequestMethod.GET)
	public @ResponseBody ApiResponse findUserByEmail(String email){
		try{
			ServiceResponse<User> payload =  service.findUserByEmail(email);
						
			LOG.debug("User : "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}


	/**
	 * Verifica se o email esta disponivel ou em uso
	 * @param email
	 * @return
	 */
	@RequestMapping(value="isAvailableEmail", method = RequestMethod.GET)
	public @ResponseBody ApiResponse isAvailableEmail(String email){
		try{
			ServiceResponse<Boolean> payload =  service.isAvailableEmail(email);
			LOG.debug("User : "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	
	/**
	 * Retorna o usuario por email.
	 * @param email
	 * @return
	 */
	@RequestMapping(value="saveUser", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveUser(@RequestBody User user){
		try{			
			ServiceResponse<User> payload =  service.save(user);		
			userRoleService.saveDefaultRoles(payload.getValue());
			LOG.debug("User : "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}	
	
	
	/**
	 * Retorna todos os usarios que o nome ou email comecem com o filtro , ou código. 
	 * @param organizationID
	 * @param filter
	 * @param offset
	 * @return
	 */
	@RequestMapping(value="findUsersByUserIDOrNameOrEmail", method = RequestMethod.GET)
	public @ResponseBody ApiResponse findUsersByUserIDOrNameOrEmail(Long organizationID, String filter, Integer offset){
		try {
			ServiceResponse<List<User>> payload =  service.findUsersByUserIDOrNameOrEmail(filter, organizationID, offset);
			LOG.debug("List<User> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
}
