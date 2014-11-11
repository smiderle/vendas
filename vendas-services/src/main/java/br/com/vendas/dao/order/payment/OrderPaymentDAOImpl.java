package br.com.vendas.dao.order.payment;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.order.OrderPayment;

@Repository
public class OrderPaymentDAOImpl extends ResourceDAO<OrderPayment> implements OrderPaymentDAO  {

	@Override
	public Double getTotalValuePending(Integer customerID) {
		
		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.createAlias("order", "o")
				.createAlias("o.customer", "c")
				.add(Restrictions.eq("c.id", customerID))
				.add(Restrictions.isNull("paymentDate"))
				.setProjection(Projections.sum("installmentValue"));
		
		return (Double) criteria.uniqueResult();
	}

	@Override
	public List<OrderPayment> findAllPedingByCustomerAndDate(Integer customerID, Date date) {

		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.createAlias("order", "o")
				.createAlias("o.customer", "c")
				.add(Restrictions.eq("c.id", customerID))
				.add(Restrictions.lt("expirationDate", date))
				.add(Restrictions.isNull("paymentDate"))
				.addOrder(Order.asc("paymentDate"));

		return criteria.list();


	}

}
