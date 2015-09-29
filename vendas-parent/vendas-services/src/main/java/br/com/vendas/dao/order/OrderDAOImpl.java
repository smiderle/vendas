package br.com.vendas.dao.order;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
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
	
	@Override
	public List<Order> findByIDOrCustomerIDAndUserID(Integer organizationID, Integer branchID, Integer userID, Long orderId, String customerID, Integer offset, Integer limit) {
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(Order.class)
				.createAlias("customer", "c")
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.add(Restrictions.eq("userID", userID ))
				.add(Restrictions.or(
								Restrictions.eq("id", orderId),
								Restrictions.eq("c.customerID",customerID)))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(org.hibernate.criterion.Order.desc("issuanceTime"));

		return criteria.list();
	}

	@Override
	public List<Object[]> getTotalValueDailyBetweenDateAndUserID(Integer organizationID, Integer branchID, Integer userID, Date dtIntial, Date dtFinal) {
		/*SELECT 	('day', dthremissao) "day", sum(valorliquido) views
		FROM pedido
		where dthremissao > '2014-10-01'
		group by 1
		ORDER BY 1*/
				
		String hql = "select day(issuanceTime), sum(netValue) from Order where organizationID = :organizationID and branchID = :branchID and userID = :userID and issuanceTime > :dtInitial and  issuanceTime < :dtFinal group by 1 order by 1 ";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter(	"organizationID", organizationID );
		query.setParameter(	"branchID", branchID );
		query.setParameter(	"userID", userID );
		query.setParameter("dtInitial", dtIntial );
		query.setParameter("dtFinal", dtFinal );
		
		return query.list();
	}
	
	@Override
	public List<Object[]> getTotalValueDailyBetweenDateAndBranchID(Integer organizationID, Integer branchID, Date dtIntial, Date dtFinal) {					
		String hql = "select day(issuanceTime), sum(netValue) from Order where organizationID = :organizationID and branchID = :branchID and issuanceTime > :dtInitial and  issuanceTime < :dtFinal group by 1 order by 1 ";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter(	"organizationID", organizationID );
		query.setParameter(	"branchID", branchID );
		query.setParameter("dtInitial", dtIntial );
		query.setParameter("dtFinal", dtFinal );
		
		return query.list();
	}
	
	
	@Override
	public Long getCountSalesByBranchAndDate( Integer organizationID, Integer branchID, Date initialDate, Date finalDate ) {

		Session session = getSession();		

		Criteria criteria = session.createCriteria(Order.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add( Restrictions.ge("issuanceTime", initialDate))
				.add( Restrictions.le("issuanceTime", finalDate))
				.add(Restrictions.eq("branchID", branchID)).setProjection( Projections.rowCount() );
		

		return (Long) criteria.uniqueResult();
	}

}
