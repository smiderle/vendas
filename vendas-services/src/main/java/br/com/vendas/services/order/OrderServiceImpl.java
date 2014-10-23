package br.com.vendas.services.order;

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.OrderDAO;
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.order.OrderItem;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.wrapper.OrderWrapper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderServiceImpl implements OrderService {
	
	@Inject
	private OrderDAO orderDAO;
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Order> save(OrderWrapper orderWrapper) {
		Order order = orderWrapper.getOrder();
		order.setChangeTime(new Date());
		order.setIssuanceTime(new Date());
		//É feito isso para atualizar o id do pedido ser reconhecido no orderItemID
		/**
		 * Se não fizer isso, vai tentar ser inserido um orderItem sem o id do order
		 */
		Set<OrderItem> ordersItens = order.getOrdersItens();		
		for (OrderItem orderItem : ordersItens) {			
			orderItem.setOrder(order);
		}
		
		Set<OrderPayment> ordersPayments = order.getOrdersPayments();
		for (OrderPayment orderPayment : ordersPayments) {
			orderPayment.setOrder(order);
		}
		
		return ServiceResponseFactory.create(orderDAO.save(order));
	}

}
