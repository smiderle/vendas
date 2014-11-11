package br.com.vendas.dao.order;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.order.Order;

@Repository
public class OrderDAOImpl  extends ResourceDAO<Order> implements OrderDAO {
	
	@Override
	public List<Order> findAllByBranch( Integer organizationID, Integer branchID,Integer offset, Integer limit ) {

		Session session = getSession();		

		Criteria criteria = session.createCriteria(Order.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(org.hibernate.criterion.Order.desc("issuanceTime"));

		return criteria.list();
	}

	@Override
	public List<Order> findAllByUserAndBranch(Integer organizationID, Integer branchID, Integer offset, Integer limit, Integer userID) {
		Session session = getSession();		

		Criteria criteria = session.createCriteria(Order.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("userID", userID))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(org.hibernate.criterion.Order.desc("issuanceTime"));

		return criteria.list();
	}

	@Override
	public Order findByID(Long id) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(Order.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("id", id));

		return (Order) criteria.uniqueResult();
	}

	@Override
	public List<Order> findByIDOrCustomerID(Integer organizationID, Integer branchID, Long orderId, String customerID, Integer offset, Integer limit) {
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(Order.class)
				.createAlias("customer", "c")
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.add(Restrictions.or(
								Restrictions.eq("id", orderId),
								Restrictions.eq("c.customerID",customerID)))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(org.hibernate.criterion.Order.desc("issuanceTime"));

		return criteria.list();
	}

}
