package br.com.vendas.api.v1.user;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.user.UserBranchOfficeService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/userBranch")
@Controller
public class UserBranchController {
	
	private static final Logger LOG = Logger.getLogger(UserBranchController.class);
	
	@Inject
	private UserBranchOfficeService userBranchService;
	
	
	@RequestMapping(value="getAllByUserID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByUserID(Long userID){
		try {
			ServiceResponse<List<UserBranchOffice>> payload = userBranchService.findAllByUserID(userID);
			LOG.debug("List<UserBranchOffice> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

}
