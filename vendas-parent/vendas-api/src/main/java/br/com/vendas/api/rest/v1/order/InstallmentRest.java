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

import br.com.vendas.domain.order.Installment;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.services.order.installment.InstallmentService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

import com.fasterxml.jackson.core.type.TypeReference;

@RequestMapping(value="/private/v1/installment")
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
	public @ResponseBody ApiResponse save(@RequestHeader(value="userID") Integer userID, @RequestBody Installment installment){
		try {
			ServiceResponse<Installment> payload =  installmentService.saveOrUpdate(userID, installment);
			LOG.debug("save Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	@RequestMapping(value="getAllByChangeGreaterThan", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		try {
			ServiceResponse<List<Installment>> payload =  installmentService.findAllByChangeGreaterThan(date, organizationID, offset);
			LOG.debug("getAllByChangeGreaterThan - List<Installment> Size: "+ payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Salva um produto.
	 */
	@RequestMapping(value="saveList", method = RequestMethod.POST )
	public @ResponseBody ApiResponse saveList( @RequestBody String installments) {
		try {

			List<Installment> listCustomer = ObjectMapperHelper.readValue(installments, new TypeReference<List<Installment>>() {});
			ServiceResponse<List<Installment>> payload = installmentService.saveList(listCustomer);
			LOG.debug("saveList - List<Installment> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Salva ou atualiza um produto.
	 */
	@RequestMapping(value="updateList", method = RequestMethod.POST )
	public @ResponseBody ApiResponse updateList( @RequestBody String installments) {
		try {

			List<Installment> listCustomer = ObjectMapperHelper.readValue(installments, new TypeReference<List<Installment>>() {});
			ServiceResponse<List<Installment>> payload = installmentService.updateList(listCustomer);
			LOG.debug("updateList - List<Installment> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

}
