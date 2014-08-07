package br.com.vendas.api.organization;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.services.organization.BranchOfficeService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;

@RequestMapping(value="/branchOffice")
@Controller
public class BranchOfficeController {
	
	private static final Logger LOG = Logger.getLogger(BranchOfficeController.class);
	
	@Inject
	private BranchOfficeService service;

	@RequestMapping(value="getAllByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByOrganizationID(Long organizationID){
		final ApiResponse apiResponse = new ApiResponse();		
		ServiceResponse<List<BranchOffice>> payload =  service.findAllByOrganizationID(organizationID);
		apiResponse.setPayload(payload);
		LOG.debug("List<BranchOffice> Size: "+payload.getRowCount());
		return apiResponse;
	}
	
	
	@RequestMapping(value="getByOrganizationIDAndBranchOfficeID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByOrganizationIDAndBranchOfficeID(Long organizationID, Long branchOfficeID){
		final ApiResponse apiResponse = new ApiResponse();		
		ServiceResponse<BranchOffice> payload =  service.findByOrganizationIDAndBranchOfficeID(organizationID, branchOfficeID);
		apiResponse.setPayload(payload);
		LOG.debug("BranchOffice Size: "+payload.getRowCount());
		return apiResponse;
	}

}
