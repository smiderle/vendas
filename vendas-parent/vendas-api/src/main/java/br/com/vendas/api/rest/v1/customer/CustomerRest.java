package br.com.vendas.api.rest.v1.customer;

import java.math.BigDecimal;
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
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.services.customer.CustomerService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

import com.fasterxml.jackson.core.type.TypeReference;

@RequestMapping(value="/private/v1/customer")
@Controller
public class CustomerRest {

	private static final Logger LOG = Logger.getLogger(CustomerRest.class);

	@Inject
	private CustomerService customerService;


	@RequestMapping(value="getCustomersByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getCustomersByOrganizationID(Integer organizationID, Integer branchID, Integer offset) {
		try {
			ServiceResponse<List<Customer>> payload =  customerService.findAllByOrganizationID(organizationID, branchID, offset);
			LOG.debug("List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Salva o cliente.
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="saveCustomer", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveCustomer(@RequestHeader(value="userID") Integer userID, @RequestBody Customer customer){
		try{
			ServiceResponse<Customer> payload =  customerService.save(userID, customer);
			LOG.debug("List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	@RequestMapping(value="getAllByFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByFilter(String filter, Integer organizationID, Integer branchID,Integer offset, Integer limit){
		try {
			ServiceResponse<List<Customer>> payload =  customerService.findByIDOrNameOrEmail(filter, organizationID, branchID, offset, limit);
			LOG.debug("getAllByFilter - List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	@RequestMapping(value="getAvaliableCreditLimit", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getInstallmentPendingTotalValue(Integer customerID) {
		try {
			ServiceResponse<BigDecimal> payload =  customerService.getAvaliableCreditLimit(customerID);
			LOG.debug("getAvaliableCreditLimit: "+payload.getValue());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	/**
	 * Tem algum pagamento vencido
	 * @param customerID
	 * @return
	 */
	@RequestMapping(value="hasExpiratePayment", method = RequestMethod.GET)
	public @ResponseBody ApiResponse hasExpiratePayment(Integer customerID) {
		try {
			ServiceResponse<Boolean> payload =  customerService.hasExpiratePayment(customerID);
			LOG.debug("hasExpiratePayment: "+payload.getValue());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	@RequestMapping(value="getAllByChangeGreaterThan", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		try {
			ServiceResponse<List<Customer>> payload =  customerService.findAllByChangeGreaterThan(date, organizationID, offset);
			LOG.debug("getAllByChangeGreaterThan - List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Salva ou atualiza um produto.
	 */
	@RequestMapping(value="saveList", method = RequestMethod.POST )
	public @ResponseBody ApiResponse saveList( @RequestBody String customers) {
		try {

			List<Customer> listCustomer = ObjectMapperHelper.readValue(customers, new TypeReference<List<Customer>>() {});
			ServiceResponse<List<Customer>> payload = customerService.save(listCustomer);
			LOG.debug("saveList - List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Atualiza um produto.
	 */
	@RequestMapping(value="updateList", method = RequestMethod.POST )
	public @ResponseBody ApiResponse updateList( @RequestBody String customers) {
		try {

			List<Customer> listCustomer = ObjectMapperHelper.readValue(customers, new TypeReference<List<Customer>>() {});
			ServiceResponse<List<Customer>> payload = customerService.update(listCustomer);
			LOG.debug("updateList - List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

}
