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
	
	/**
	 * Retorna todas as parcelas pendetes de determinada empresa respeitando o limit e offset  
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<OrderPayment> findAllPendingByOrganizationID(Integer organizationID, Integer branchID, Integer offset,Integer limit);
	
	/**
	 * Retorna todas as parcelas pendetes de determinado cliente ou pedido
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<OrderPayment> findPendingByOrderIDOrCustomerID(Integer organizationID, Integer branchID,String customerID, Long orderID, Integer offset, Integer limit);
	
	
	/**
	 * Retorna a parcela por id
	 * @param orderID
	 * @return
	 */
	OrderPayment findByID(Long orderID);
	
	
	
	List<OrderPayment> findByComplexFilter(Integer organizationID, Integer branchID, String customerID, Date issuanceDateGte, Date issuanceDateLte, Date expirationDateGte, Date expirationDateLte, String statusPayment,Integer formPayment, Integer offset, Integer limit);
	

}
