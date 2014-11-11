package br.com.vendas.services.order.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.payment.OrderPaymentDAO;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderPaymentServiceImpl implements OrderPaymentService {

	@Inject 
	private OrderPaymentDAO orderPaymentDAO; 
	
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

	
}
