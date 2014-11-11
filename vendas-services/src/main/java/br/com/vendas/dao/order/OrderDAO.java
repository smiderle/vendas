package br.com.vendas.dao.order;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.order.Order;

public interface OrderDAO extends GenericDAO<Order> {
	
	/**
	 * Retorna todos os pedidos/orçamentos por filial
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Order> findAllByBranch( Integer organizationID, Integer branchID,Integer offset, Integer limit );
	
	
	/**
	 * Retorna todos os pedidos/orçamento por vendedor.
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Order> findAllByUserAndBranch( Integer organizationID, Integer branchID,Integer offset, Integer limit, Integer userID );
	
	/**
	 * Retorna os pedidos pelo id ou código do cliente
	 * @param id
	 * @param customerID
	 * @param customerCnpj
	 * @return
	 */
	List<Order> findByIDOrCustomerID( Integer organizationID, Integer branchID, Long id, String customerID, Integer offset, Integer limit);
	
	/**
	 * Retorna o pedido/orçamento por ID
	 * @param id
	 * @return
	 */
	Order findByID(Long id);
}