package br.com.vendas.domain.wrapper;

import br.com.vendas.domain.order.Order;

public class OrderWrapper {
	
	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}