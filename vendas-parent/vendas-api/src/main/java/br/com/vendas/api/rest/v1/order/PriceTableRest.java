package br.com.vendas.api.rest.v1.order;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.PriceTable;
import br.com.vendas.services.order.pricetable.PriceTableService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;


@RequestMapping(value="/private/v1/priceTable")
@Controller
public class PriceTableRest {
	
	private static final Logger LOG = Logger.getLogger(PriceTableRest.class);
	
	@Inject
	private PriceTableService priceTableService;
	
	
	@RequestMapping(value="findAllByBranche", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAll(Integer organizationID, Integer branchID){
		try {
			ServiceResponse<List<PriceTable>> payload =  priceTableService.findAllByBranche(organizationID, branchID);
			LOG.debug("findAllByBranche Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse save(@RequestHeader(value="userID") Integer userID, @RequestBody PriceTable priceTable){
		try {
			ServiceResponse<PriceTable> payload =  priceTableService.saveOrUpdate( userID, priceTable );
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="getAllByChangeGreaterThan", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		try {
			ServiceResponse<List<PriceTable>> payload =  priceTableService.findAllByChangeGreaterThan(date, organizationID, offset);
			LOG.debug("getAllByChangeGreaterThan - List<PriceTable> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}

}