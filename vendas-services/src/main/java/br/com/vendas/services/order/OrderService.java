package br.com.vendas.services.order;

import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.services.support.ServiceResponse;

public interface OrderService {
	
	/**
	 * Salva o pedido
	 * @param order
	 * @return
	 */
	public ServiceResponse<Order> save(OrderWrapper orderWrapper);

}
