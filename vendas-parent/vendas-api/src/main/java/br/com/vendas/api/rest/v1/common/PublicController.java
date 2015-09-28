package br.com.vendas.api.rest.v1.common;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/public/v1/signin")
@Controller
public class PublicController {

	@Inject
	private UserService userService;


	private static final Logger LOG = Logger.getLogger(PublicController.class);


	@RequestMapping(value="generateNewUser", method = RequestMethod.POST)
	public @ResponseBody ApiResponse generateNewUser(String organizationName, String userName, String email, String password, String serial){


		try {
			ServiceResponse<UserDTO> payload =  userService.generateNewUser( organizationName, userName, email, password, serial );

			LOG.debug("generateNewUser Size: "+payload.getRowCount());

			return ResponseBuilder.build( payload );

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
	@RequestMapping(value="getUserByEmailAndPassword", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getUserByEmailAndPassword(String email, String password){
		try{
			ServiceResponse<UserDTO> payload =  userService.findUserByEmailAndPassword(email, password);
			LOG.debug("getUserByEmailAndPassword Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}



	@RequestMapping(value="gerarConfirmacaoCadastro", method = RequestMethod.GET)
	public @ResponseBody ApiResponse gerarConfirmacaoCadastro( String email ) {
		try{
			ServiceResponse<Boolean> payload =  userService.gerarConfirmacaoCadastro(email);
			LOG.debug("gerarConfirmacaoCadastro Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	@RequestMapping(value="validaCodigo", method = RequestMethod.GET)
	public @ResponseBody ApiResponse validaCodigo( String email, String codigo ) {
		try{
			ServiceResponse<Boolean> payload =  userService.validarCodigo(email, codigo);
			LOG.debug("validaCodigo Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}




}
