package br.com.vendas.api.rest.v1.order;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.dto.OrderPaymentDTO;
import br.com.vendas.services.order.payment.OrderPaymentService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/private/v1/orderPayment")
@Controller
public class OrderPaymentRest {
	
	private static final Logger LOG = Logger.getLogger(OrderPaymentRest.class);
	
	@Inject
	private OrderPaymentService orderPaymentService;
	
	@RequestMapping(value="getAllPendingByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllPendingByOrganizationID(Integer organizationID, Integer branchID, Integer offset, Integer limit) {
		try {
			ServiceResponse<List<OrderPaymentDTO>> payload =  orderPaymentService.findAllPendingByOrganizationID(organizationID, branchID, offset, limit);
			LOG.debug("getAllPendingByOrganizationID Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	
	@RequestMapping(value="getPendingByFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getPendingByFilter(Integer organizationID, Integer branchID, String filter,  Integer offset, Integer limit) {
		try {
			ServiceResponse<List<OrderPaymentDTO>> payload =  orderPaymentService.findPendingByOrderIDOrCustomerID(organizationID, branchID, filter, offset, limit);
			LOG.debug("getPendingByFilter Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="getByComplexFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByComplexFilter(Integer organizationID, Integer branchID, String customerID, Long issuanceDateGte, Long issuanceDateLte, Long expirationDateGte, Long expirationDateLte,  String statusPayment,Integer formPayment, Integer offset, Integer limit) {
		try {
			ServiceResponse<List<OrderPaymentDTO>> payload =  orderPaymentService.findByComplexFilter(organizationID, branchID, customerID, issuanceDateGte, issuanceDateLte, expirationDateGte, expirationDateLte, statusPayment, formPayment, offset, limit);
			LOG.debug("getByComplexFilter Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	@RequestMapping(value="getByID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByOrderID(Long orderID) {
		try {
			ServiceResponse<OrderPaymentDTO> payload =  orderPaymentService.findByID(orderID);
			LOG.debug("getByID Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}		
	}
	
	/**
	 * Seta a parcela como paga ou baixada
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="setPaid", method = RequestMethod.POST)
	public @ResponseBody ApiResponse setPaid(@RequestHeader(value="userID") Integer userID, @RequestBody Long orderPaymentID) {
		try {
			ServiceResponse<Boolean> payload =  orderPaymentService.setPaid(userID, orderPaymentID);
			LOG.debug("setPaid Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
}
