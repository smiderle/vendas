package br.com.vendas.api.location;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.location.State;
import br.com.vendas.services.location.StateServiceImpl;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.HTTPStatusCode;

@RequestMapping(value="/states")
@Controller
public class StateController {

	private static final Logger LOG = Logger.getLogger(StateController.class);
	
	@Autowired
	private StateServiceImpl stateServiceImpl;

	@RequestMapping(value="getStates", method = RequestMethod.GET)
	public @ResponseBody ApiResponse loadStates() {
		final ApiResponse apiResponse = new ApiResponse();
		
		ServiceResponse<List<State>> payload  = stateServiceImpl.findAll();
		apiResponse.setPayload(payload);
		apiResponse.setStatus(ApiResponse.STATUS_SUCCESS);
		apiResponse.setCode(HTTPStatusCode.SUCESS_200.getCode());
		LOG.debug("states/loadStates");
		return apiResponse;
	}
}
