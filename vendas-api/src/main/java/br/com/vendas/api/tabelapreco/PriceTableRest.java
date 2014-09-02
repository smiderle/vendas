package br.com.vendas.api.tabelapreco;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.pedido.PriceTable;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.tabelapreco.PriceTableService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;


@RequestMapping(value="/priceTable")
@Controller
public class PriceTableRest {
	
	private static final Logger LOG = Logger.getLogger(PriceTableRest.class);
	
	@Inject
	private PriceTableService priceTableService;
	
	
	@RequestMapping(value="findAllByBranche", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAll(Long organizationID, Long branchID){
		try {
			ServiceResponse<List<PriceTable>> payload =  priceTableService.findAllByBranche(organizationID, branchID);
			LOG.debug("buscaTodosPorFilial Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAll(@RequestBody PriceTable priceTable){
		try {
			ServiceResponse<PriceTable> payload =  priceTableService.saveOrUpdate(priceTable);
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}

}