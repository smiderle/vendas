package br.com.vendas.api.user;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.xml.ws.Response;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.user.User;
import br.com.vendas.exception.ApplicationException;
import br.com.vendas.pojo.user.UserPojo;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserAccessService;
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
	private UserAccessService userAccessService;
	
	/**
	 * Retorna todos os usarios de determinada empresa.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getUsersByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getUsersByOrganizationID(Long organizationID, Integer offset){
		try {
			ServiceResponse<List<UserPojo>> payload =  service.findAllByOrganizationID(organizationID, offset);
			LOG.debug("List<User> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

	/**
	 * Retorna o usuario por email.
	 * @param email
	 * @return
	 */
	@RequestMapping(value="getUserByEmail", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getUserByEmail(String email){
		try{
			ServiceResponse<UserPojo> payload =  service.findUserByEmail(email);
			LOG.debug("getUserByEmail Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
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
			LOG.debug("isAvailableEmail Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	
	/**
	 * Salva o usuario.
	 * @param email
	 * @return
	 */
	@RequestMapping(value="saveUser", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveUser(@RequestBody User user){
		try{			
			ServiceResponse<User> payload =  service.saveOrUpdate(user);			
			LOG.debug("saveUser Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (ApplicationException e) {
			LOG.error(e);
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
	@RequestMapping(value="getUsersByUserIDOrNameOrEmail", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getUsersByUserIDOrNameOrEmail(Long organizationID, String filter, Integer offset){
		try {
			ServiceResponse<List<UserPojo>> payload =  service.findUsersByUserIDOrNameOrEmail(filter, organizationID, offset);
			LOG.debug("getUsersByUserIDOrNameOrEmail Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	/**
	 * Salva a hora de acesso do usuario
	 * @param userAcess
	 */
	@RequestMapping(value="addUserAccess", method = RequestMethod.POST)
	public void addUserAccess(@RequestBody Long userID){
		try {
			userAccessService.save(userID);
			LOG.debug("addUserAccess userID: "+userID);
		} catch (Exception e) {
			LOG.error(e);						
		}
	}
	
	
}
