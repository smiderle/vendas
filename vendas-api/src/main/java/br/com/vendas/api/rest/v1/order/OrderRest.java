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
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.exception.VendasException;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.services.order.OrderService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/order")
@Controller
public class OrderRest {
	
	private static final Logger LOG = Logger.getLogger(OrderRest.class);
	
	@Inject
	private OrderService orderService;
	
	/*@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody OrderWrapper orderWrapper) {
		LOG.debug("LOGGER");
		return new ApiResponse();
		try {
			ServiceResponse<Installment> payload =  installmentService.saveOrUpdate(installment);
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}*/
	
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody String orderWrapper) {
		try {
			OrderWrapper order = new ObjectMapperHelper().readValue(orderWrapper, OrderWrapper.class);
			orderService.save(order);
						
			return new ApiResponse();
		} catch (VendasException e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}	
		
		/*try {
			ServiceResponse<Installment> payload =  installmentService.saveOrUpdate(installment);
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}*/		
	}

}
