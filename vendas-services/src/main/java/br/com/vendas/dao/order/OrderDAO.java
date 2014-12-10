package br.com.vendas.dao.order;

import java.util.Date;
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
	
	/**
	 * Retorna os pedidos pelo id ou código do cliente, de determinado usuário
	 * @param organizationID
	 * @param branchID
	 * @param userID
	 * @param orderId
	 * @param customerID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Order> findByIDOrCustomerIDAndUserID(Integer organizationID, Integer branchID, Integer userID, Long orderId, String customerID, Integer offset, Integer limit);
	
	/**
	 * Retorna o valor total de vendas diario  entre determinado periodo.
	 * Retornara uma lista de array de objetos sendo que o objeto contém no primeiro indice o dia, e no segundo o valor
	 * @param userID
	 * @param dtIntial
	 * @param dtFinal
	 * @return
	 */
	List<Object[]>  getTotalValueDailyBetweenDateAndUserID(Integer userID, Date dtIntial, Date dtFinal);
	
	/**
	 * Retorna o valor total de vendas diario entre determinado periodo e filial
	 * @param branchID
	 * @param dtIntial
	 * @param dtFinal
	 * @return
	 */
	List<Object[]> getTotalValueDailyBetweenDateAndBranchID(Integer branchID, Date dtIntial, Date dtFinal);
	
	/**
	 * Retorna a quantidade de Pedidos/Orçamentos realizado
	 * @param organizationID
	 * @param branchID
	 * @return
	 */
	Long getCountSalesByBranchAndDate( Integer organizationID, Integer branchID, Date initialDate, Date finalDate ) ;
}