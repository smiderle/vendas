package br.com.vendas.api.organization;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.services.organization.BranchOfficeService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/branchOffice")
@Controller
public class BranchOfficeController {
	
	private static final Logger LOG = Logger.getLogger(BranchOfficeController.class);
	
	@Inject
	private BranchOfficeService service;

	@RequestMapping(value="getBranchesByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getBranchesByOrganizationID(Long organizationID){
		try {
			ServiceResponse<List<BranchOffice>> payload =  service.findAllByOrganizationID(organizationID);
			LOG.debug("List<BranchOffice> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}				
	}
	
	
	@RequestMapping(value="getByOrganizationIDAndBranchOfficeID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByOrganizationIDAndBranchOfficeID(Long organizationID, Long branchOfficeID){
		try {
			ServiceResponse<BranchOffice> payload =  service.findByOrganizationIDAndBranchOfficeID(organizationID, branchOfficeID);
			LOG.debug("BranchOffice getByOrganizationIDAndBranchOfficeID Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse save(@RequestBody BranchOffice branchOffice){
		try {
			ServiceResponse<BranchOffice> payload =  service.save(branchOffice);
			LOG.debug("BranchOffice save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	
	@RequestMapping(value="saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveOrUpdate(@RequestBody BranchOffice branchOffice){
		try {
			ServiceResponse<BranchOffice> payload =  service.saveOrUpdate(branchOffice);
			LOG.debug("BranchOffice saveOrUpdate Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

}
