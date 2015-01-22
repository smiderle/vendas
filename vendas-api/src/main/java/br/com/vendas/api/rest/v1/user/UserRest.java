package br.com.vendas.api.rest.v1.user;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.user.User;
import br.com.vendas.exception.ApplicationException;
import br.com.vendas.exception.ParseJsonException;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserAccessService;
import br.com.vendas.services.user.UserService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/private/v1/user")
@Controller
public class UserRest {

	private static final Logger LOG = Logger.getLogger(UserRest.class);

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
	public @ResponseBody ApiResponse getUsersByOrganizationID(Integer organizationID, Integer offset){
		try {
			ServiceResponse<List<UserDTO>> payload =  service.findAllByOrganizationID(organizationID, offset);
			LOG.debug("List<User> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	/**
	 * Retorna todos os usarios de determinada empresa, com exceção do usuário passado por parametro.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getOtherUsersByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getOtherUsersByOrganizationID(Integer organizationID, Integer userID, Integer offset){
		try {
			ServiceResponse<List<UserDTO>> payload =  service.findOtherUsersByOrganizationID(organizationID,userID, offset);
			LOG.debug("getOtherUsersByOrganizationID Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
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
			ServiceResponse<UserDTO> payload =  service.findUserByEmail(email);
			LOG.debug("getUserByEmail Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
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
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}



	/**
	 * Salva o usuario.
	 * @param email
	 * @return
	 */
	@RequestMapping(value="saveUser", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveUser(@RequestHeader(value="userID") Integer userID,  @RequestBody String userWrapper) {
		try{			
			
			User user = new ObjectMapperHelper().readValue(userWrapper, User.class);
			
			ServiceResponse<User> payload =  service.saveOrUpdate(userID, user);			
			LOG.debug("saveUser Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (ApplicationException | ParseJsonException e) {
			LOG.error(e.getMessage(), e);
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
	public @ResponseBody ApiResponse getUsersByUserIDOrNameOrEmail(Integer organizationID, String filter, Integer offset){
		try {
			ServiceResponse<List<UserDTO>> payload =  service.findUsersByUserIDOrNameOrEmail(filter, organizationID, offset);
			LOG.debug("getUsersByUserIDOrNameOrEmail Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}


	/**
	 * Salva a hora de acesso do usuario
	 * @param userAcess
	 */
	@RequestMapping(value="addUserAccess", method = RequestMethod.POST)
	public void addUserAccess(@RequestBody Integer userID){
		try {
			userAccessService.save(userID);
			LOG.debug("addUserAccess userID: "+userID);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);				
		}
	}

}
