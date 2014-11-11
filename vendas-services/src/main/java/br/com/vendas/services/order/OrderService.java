package br.com.vendas.services.order;

import java.util.List;

import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.dto.order.OrderDTO;
import br.com.vendas.services.support.ServiceResponse;

public interface OrderService {
	
	/**
	 * Salva o pedido
	 * @param order
	 * @return
	 */
	ServiceResponse<OrderDTO> save(OrderWrapper orderWrapper);
	
	/**
	 * Retorna todos os pedidos/orçamentos por filial
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<OrderDTO>> findAllByBranch( Integer organizationID, Integer branchID,Integer offset, Integer limit);
	
	/**
	 * Retorna todos os pedidos/orçamento por vendedor.
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<OrderDTO>> findAllByUserAndBranch( Integer organizationID, Integer branchID,Integer offset, Integer limit, Integer userID );
	
	/**
	 * Retorna o pedido/orçamento por ID
	 * @param id
	 * @return
	 */
	ServiceResponse<OrderDTO> findByID(Long id);
		
	/**
	 * Retorna os pedidos pelo código ou pelo cliente
	 * @param organizationID
	 * @param branchID
	 * @param filter
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<OrderDTO>> findByIDOrCustomerID( Integer organizationID, Integer branchID, String filter, Integer offset, Integer limit);

}
