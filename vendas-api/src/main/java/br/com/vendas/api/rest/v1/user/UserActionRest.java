package br.com.vendas.api.rest.v1.user;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.user.UserAction;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserActionService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/userAction")
@Controller
public class UserActionRest {
	
private static final Logger LOG = Logger.getLogger(UserActionRest.class);
	
	@Inject
	private UserActionService userActionService;
	

	@RequestMapping(value="getAllByUserID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByUserID(Integer userID, Integer offset, Integer limit){
		try {
			ServiceResponse<List<UserAction>> payload = userActionService.findByUser(userID, offset, limit);
			LOG.debug("List<UserAction> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	

}
