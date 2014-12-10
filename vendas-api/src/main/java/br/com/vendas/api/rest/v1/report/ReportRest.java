package br.com.vendas.api.rest.v1.report;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.services.order.OrderService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/report")
@Controller
public class ReportRest {
	
	private static final Logger LOG = Logger.getLogger(ReportRest.class);
	
	@Inject
	private OrderService orderService;
	
	@RequestMapping(value="getCurrentCountSalesByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByBranch( Integer organizationID, Integer branchID ) {
		try {
			ServiceResponse<Long> payload = orderService.getCurrentCountSalesByBranch( organizationID, branchID );
			
			LOG.debug("getCurrentCountSalesByBranch - Size: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

}
