package br.com.vendas.api.rest.v1.order;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.order.Installment;
import br.com.vendas.services.order.installment.InstallmentService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/installment")
@Controller
public class InstallmentRest {
	
	private static final Logger LOG = Logger.getLogger(InstallmentRest.class);
	
	@Inject
	private InstallmentService installmentService;
	
	

	@RequestMapping(value="findAllByBranche", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAll(Integer organizationID, Integer branchID){
		try {
			ServiceResponse<List<Installment>> payload =  installmentService.findAllByBranche(organizationID, branchID);
			LOG.debug("findAllByBranche Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody Installment installment){
		try {
			ServiceResponse<Installment> payload =  installmentService.saveOrUpdate(installment);
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}


}
