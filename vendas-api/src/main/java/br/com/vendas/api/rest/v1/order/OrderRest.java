package br.com.vendas.api.rest.v1.order;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.support.ApiResponse;

@RequestMapping(value="/v1/order")
@Controller
public class OrderRest {
	
	private static final Logger LOG = Logger.getLogger(OrderRest.class);
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody OrderWrapper orderWrapper){
		LOG.debug("LOGGER");
		return new ApiResponse();
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
