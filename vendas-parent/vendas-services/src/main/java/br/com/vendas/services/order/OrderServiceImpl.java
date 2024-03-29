package br.com.vendas.services.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.core.util.DateUtil;
import br.com.vendas.core.util.StringUtils;
import br.com.vendas.dao.order.OrderDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.order.OrderItem;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.dto.OrderDTO;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderServiceImpl implements OrderService {

	@Inject
	private OrderDAO orderDAO;

	@Inject
	private UserActionService userActionService;

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<List<OrderDTO>> save(Integer userID, List<Order> orders) {

		List<OrderDTO> listOrderDTO = new ArrayList<>(orders.size());

		for (Order order : orders) {

			order.setID( null );
			OrderDTO orderDTO = save( userID, order ).getValue();
			listOrderDTO.add(orderDTO);

		}

		return ServiceResponseFactory.create(  listOrderDTO );
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<OrderDTO> save(Integer userID , Order order) {
		order.setChangeTime(new Date());
		order.setIssuanceTime(new Date());
		//É feito isso para atualizar o id do pedido ser reconhecido no orderItemID
		/**
		 * Se não fizer isso, vai tentar ser inserido um orderItem sem o id do order
		 */
		Set<OrderItem> ordersItens = order.getOrdersItens();
		for (OrderItem orderItem : ordersItens) {
			orderItem.setID(null);
			orderItem.setChangeTime(new Date());
			orderItem.setOrder(order);
		}

		Set<OrderPayment> ordersPayments = order.getOrdersPayments();
		for (OrderPayment orderPayment : ordersPayments) {
			orderPayment.setID(null);
			orderPayment.setChangeTime(new Date());
			orderPayment.setOrder(order);
		}

		orderDAO.save(order);

		saveOrderAction(userID, order);

		OrderDTO orderDTO = new OrderDTO(order, null, null, null);

		return ServiceResponseFactory.create(orderDTO);
	}

	@Override
	public ServiceResponse<List<OrderDTO>> findAllByBranch(Integer organizationID, Integer branchID, Integer offset, Integer limit) {

		List<Order> lsOrder = orderDAO.findAllByBranch(organizationID, branchID, offset, getLimit(limit));

		List<OrderDTO> lsOrderTDO = new ArrayList<>(lsOrder.size());

		for (Order order : lsOrder) {
			lsOrderTDO.add(new OrderDTO(order, null, null, null));
		}

		return ServiceResponseFactory.create(lsOrderTDO);
	}

	@Override
	public ServiceResponse<List<OrderDTO>> findAllByUserAndBranch(Integer organizationID, Integer branchID, Integer offset, Integer limit, Integer userID) {

		List<Order> lsOrder = orderDAO.findAllByUserAndBranch(organizationID, branchID, offset, getLimit(limit), userID);

		List<OrderDTO> lsOrderTDO = new ArrayList<>(lsOrder.size());

		for (Order order : lsOrder) {
			lsOrderTDO.add(new OrderDTO(order, null, null, null));
		}

		return ServiceResponseFactory.create(lsOrderTDO);
	}



	@Override
	public ServiceResponse<OrderDTO> findByID(Long id) {
		Order order = orderDAO.findByID(id);
		Hibernate.initialize(order.getOrdersItens());
		Hibernate.initialize(order.getOrdersPayments());

		OrderDTO orderDTO = new OrderDTO(order, order.getInstallment(), order.getOrdersItens(), order.getOrdersPayments());
		return ServiceResponseFactory.create(orderDTO);
	}

	@Override
	public ServiceResponse<List<OrderDTO>> findByIDOrCustomerID(Integer organizationID, Integer branchID, String filter, Integer offset, Integer limit) {
		Long orderID = 0L;

		if(StringUtils.isInteger(filter)) {
			orderID = Long.parseLong(filter);
		}

		List<Order> lsOrder = orderDAO.findByIDOrCustomerID(organizationID, branchID, orderID, filter, offset,  getLimit(limit));

		List<OrderDTO> lsOrderTDO = new ArrayList<>(lsOrder.size());

		for (Order order : lsOrder) {
			lsOrderTDO.add(new OrderDTO(order, null, null, null));
		}

		return ServiceResponseFactory.create(lsOrderTDO);
	}

	@Override
	public ServiceResponse<List<OrderDTO>> findByIDOrCustomerIDAndUserID(Integer organizationID, Integer branchID, Integer userID, String filter, Integer offset, Integer limit) {
		Long orderID = 0L;

		if(StringUtils.isInteger(filter)) {
			orderID = Long.parseLong(filter);
		}

		List<Order> lsOrder = orderDAO.findByIDOrCustomerIDAndUserID(organizationID, branchID, userID, orderID, filter, offset,  getLimit(limit));

		List<OrderDTO> lsOrderTDO = new ArrayList<>(lsOrder.size());

		for (Order order : lsOrder) {
			lsOrderTDO.add(new OrderDTO(order, null, null, null));
		}

		return ServiceResponseFactory.create(lsOrderTDO);
	}


	/**
	 * Salva as alções do usuário para o cadastro ou edição de um produto
	 * @param userID
	 * @param customer
	 */
	private void saveOrderAction( Integer userID, Order order) {
		UserAction userAction = null;

		userAction = UserActionHelper.createOrderSave( userID, order );

		/*if(order.isExcluded()){
			userAction = UserActionHelper.createOrderDelete( userID, order );
		} else if(order.getID() == null) {
			userAction = UserActionHelper.createOrderSave( userID, order );
		} else {
			userAction = UserActionHelper.createOrderEdit( userID, order );
		}*/

		userActionService.save( userAction );
	}

	@Override
	public ServiceResponse<Long> getCurrentCountSalesByBranch(Integer organizationID, Integer branchID) {

		Calendar initMonth = DateUtil.initMonth( new Date() );
		Calendar finalMonth = DateUtil.finalMonth( new Date() );

		Long totalValueSalesByBranch = orderDAO.getCountSalesByBranchAndDate( organizationID, branchID, initMonth.getTime(), finalMonth.getTime() );

		return ServiceResponseFactory.create(totalValueSalesByBranch);
	}

	private Integer getLimit( Integer limit ) {
		Integer defaultLimit = LimitQuery.LIMIT_ORDER.value();
		if(limit != null && limit < LimitQuery.LIMIT_ORDER.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}


}
