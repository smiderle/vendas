package br.com.vendas.services.order.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.core.util.StringUtils;
import br.com.vendas.dao.order.payment.OrderPaymentDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.dto.OrderDTO;
import br.com.vendas.dto.OrderPaymentDTO;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderPaymentServiceImpl implements OrderPaymentService {

	@Inject 
	private OrderPaymentDAO orderPaymentDAO; 
	
	@Inject 
	private UserActionService userActionService;
	
	@Override
	public ServiceResponse<BigDecimal> getTotalValuePending(Integer customerID) {
		Double totalValuePending = orderPaymentDAO.getTotalValuePending(customerID);
		BigDecimal total;
		if(totalValuePending == null){
			total = new BigDecimal("0");
		} else {
			total = new BigDecimal(totalValuePending.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		
		
		return ServiceResponseFactory.create(total);
	}

	@Override
	public ServiceResponse<List<OrderPayment>> findAllOverdueByCustomer(Integer customerID) {
		return ServiceResponseFactory.create(orderPaymentDAO.findAllPedingByCustomerAndDate(customerID, new Date()));
	}

	@Override
	public ServiceResponse<List<OrderPaymentDTO>> findAllPendingByOrganizationID( Integer organizationID, Integer branchID, Integer offset, Integer limit ) {
		
		List<OrderPayment> ordersPayment = orderPaymentDAO.findAllPendingByOrganizationID(organizationID, branchID, offset, getLimit(limit));
		
		List<OrderPaymentDTO> ordersPaymentDTO = new ArrayList<>(ordersPayment.size());
		
		for (OrderPayment orderPayment : ordersPayment) {
			OrderDTO orderDTO = new OrderDTO(orderPayment.getOrder(), null, null, null);
			
			ordersPaymentDTO.add(new OrderPaymentDTO(orderPayment, orderDTO));
		}
		
		return ServiceResponseFactory.create(ordersPaymentDTO);
	}


	@Override
	public ServiceResponse<List<OrderPaymentDTO>> findPendingByOrderIDOrCustomerID(Integer organizationID, Integer branchID, String filter, Integer offset, Integer limit) {

		Long filterLong = -1L;
		if(StringUtils.isInteger(filter)){
			filterLong = Long.parseLong(filter);
		}
		
		List<OrderPayment> ordersPayment = orderPaymentDAO.findPendingByOrderIDOrCustomerID(organizationID, branchID, filter, filterLong, offset,  getLimit(limit));
		
		List<OrderPaymentDTO> ordersPaymentDTO = new ArrayList<>(ordersPayment.size());
		
		for (OrderPayment orderPayment : ordersPayment) {
			OrderDTO orderDTO = new OrderDTO(orderPayment.getOrder(), null, null, null);
			
			ordersPaymentDTO.add(new OrderPaymentDTO(orderPayment, orderDTO));
		}
		
		return ServiceResponseFactory.create(ordersPaymentDTO);
	}

	@Override
	public ServiceResponse<OrderPaymentDTO> findByID(Long orderID) {
		
		OrderPayment orderPayment = orderPaymentDAO.findByID(orderID);
		
		OrderPaymentDTO orderPaymentDTO = null;
		
		if (orderPayment != null) {
			Hibernate.initialize(orderPayment.getOrder().getOrdersPayments());
			Hibernate.initialize(orderPayment.getOrder().getInstallment());
			OrderDTO orderDTO = new OrderDTO(orderPayment.getOrder(), orderPayment.getOrder().getInstallment(), null, orderPayment.getOrder().getOrdersPayments());
			
			orderPaymentDTO = new OrderPaymentDTO(orderPayment, orderDTO);
		}
		
		return ServiceResponseFactory.create(orderPaymentDTO);
	}
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Boolean> setPaid(Integer userID, Long id) {		
		OrderPayment orderPayment = orderPaymentDAO.findByID(id);
		
		saveOrderPaymentAction(userID, orderPayment);
		
		orderPayment.setPaymentDate(new Date());
		return ServiceResponseFactory.create(true);
	}

	@Override
	public ServiceResponse<List<OrderPaymentDTO>> findByComplexFilter(Integer organizationID, Integer branchID, String customerID,
			Long issuanceDateGte, Long issuanceDateLte, Long expirationDateGte, Long expirationDateLte, String statusPayment,Integer formPayment,  Integer offset,
			Integer limit) {

		Date issuanceDateGteDt = null;
		Date issuanceDateLteDt = null;
		Date expirationDateGteDt = null;
		Date expirationDateLteDt = null;
		
		if(issuanceDateGte != null){
			issuanceDateGteDt = new Date(issuanceDateGte);
		}
		
		if(issuanceDateLte != null) {
			issuanceDateLteDt =new Date(issuanceDateLte);
		}
		
		if(expirationDateGte != null) {
			expirationDateGteDt = new Date(expirationDateGte);
		}
		
		if(expirationDateLte != null) {
			expirationDateLteDt = new Date(expirationDateLte);
		}
		
		
		List<OrderPayment> ordersPayment = orderPaymentDAO.findByComplexFilter(organizationID, branchID, customerID, issuanceDateGteDt, issuanceDateLteDt, expirationDateGteDt, expirationDateLteDt, statusPayment, formPayment, offset, getLimit(limit));

		List<OrderPaymentDTO> ordersPaymentDTO = new ArrayList<>(ordersPayment.size());

		for (OrderPayment orderPayment : ordersPayment) {
			OrderDTO orderDTO = new OrderDTO(orderPayment.getOrder(), null, null, null);

			ordersPaymentDTO.add(new OrderPaymentDTO(orderPayment, orderDTO));
		}

		return ServiceResponseFactory.create(ordersPaymentDTO);
	}
	

	/**
	 * Salva as alções do usuário para o cadastro ou edição de um produto
	 * @param userID
	 * @param customer
	 */
	private void saveOrderPaymentAction( Integer userID, OrderPayment orderPayment) {
		UserAction userAction = null;
		
		
		userAction = UserActionHelper.createOrderPaymentEdit( userID, orderPayment );
				
		userActionService.save( userAction );
		
	}
	
	private Integer getLimit(Integer limit) {
		Integer defaultLimit = LimitQuery.LIMIT_ORDER_PAYMENT.value();
		if(limit != null && limit < LimitQuery.LIMIT_ORDER_PAYMENT.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}

}
