package br.com.vendas.api.v1.organization;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.services.organization.OrganizationService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;

@RequestMapping(value="/v1/organization")
@Controller
public class OrganizationRest {
	private static final Logger LOG = Logger.getLogger(OrganizationRest.class);
	
	@Inject  
	private OrganizationService organizationService;
	
	@RequestMapping(value="getOrganizations", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAll(){
		final ApiResponse apiResponse = new ApiResponse();		
		ServiceResponse<List<Organization>> payload =  organizationService.findAll();
		apiResponse.setPayload(payload);
		LOG.debug("List<Organization> Size: "+payload.getRowCount());
		return apiResponse;
	}
	
	@RequestMapping(value="addOrganization", method = RequestMethod.GET)
	public ApiResponse addOrganization(){
		final ApiResponse apiResponse = new ApiResponse();
		ServiceResponse<Organization> org = organizationService.save(new Organization(""));
		apiResponse.setPayload(org);		
		return apiResponse;
	}
}
