package br.com.vendas.dao.product.group;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.product.ProductGroup;

@Repository
public class ProductGroupDAOImpl extends ResourceDAO<ProductGroup> implements ProductGroupDAO{

	@Override
	public List<ProductGroup> findAllByBranche(Integer organizationID,
			Integer branchID, Integer offset, Integer limit) {
		Session session = getSession();		

		Criteria criteria = session.createCriteria(ProductGroup.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(Order.asc("groupID"));

		return criteria.list();
	}

	@Override
	public List<ProductGroup> findByDescription(String description,
			Integer organizationID, Integer branchID, Integer offset, Integer limit) {


		Session session = getSession();
		Criteria criteria = session.createCriteria(ProductGroup.class)

				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq( "branchID", branchID))
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.like("description", description, MatchMode.START).ignoreCase())
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("groupID"));		
		return criteria.list();	






		/*

		Session session = getSession();
		Criteria criteria = session.createCriteria(ProductGroup.class);

		criteria.add(Restrictions.and(
									Restrictions.and(
											Restrictions.eq("organizationID", organizationID),
											Restrictions.eq( "branchID", branchID)), 
											Restrictions.and(
													Restrictions.eq("excluded", false), 
													Restrictions.like("description", description, MatchMode.START).ignoreCase())))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("groupID"));
		return criteria.list();	

		 */}

}
