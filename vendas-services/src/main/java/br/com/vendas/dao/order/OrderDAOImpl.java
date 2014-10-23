package br.com.vendas.dao.order;

import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.order.Order;

@Repository
public class OrderDAOImpl  extends ResourceDAO<Order> implements OrderDAO {

}
