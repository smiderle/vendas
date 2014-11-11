package br.com.vendas.dao.order.payment;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.order.OrderPayment;

public interface OrderPaymentDAO extends GenericDAO<OrderPayment>{
	
	/**
	 * Retorna o valor total das parcelas pendentes
	 * @return
	 */
	Double getTotalValuePending(Integer customerID);
	
	
	/**
	 * Retorna todos os pagamentos pendetes a partir de determinada data.
	 * @param customerID
	 * @param date
	 * @return
	 */
	List<OrderPayment> findAllPedingByCustomerAndDate(Integer customerID, Date date);
}
