package br.com.vendas.dao.product;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.product.Product;

@Repository
public class ProductDAOImpl extends ResourceDAO<Product> implements ProductDAO {

	@Override
	public List<Product> findAllByBranche(Integer organizationID, Integer branchID,
			Integer offset, Integer limit) {


		Session session = getSession();		

		Criteria criteria = session.createCriteria(Product.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(Order.asc("productID"));

		return criteria.list();


	}

	@Override
	public List<Product> findByDescriptionOrProductID(
			String description,String productID,String barcode, Integer organizationID, Integer branchID, Integer offset,
			Integer limit) {

		Session session = getSession();

		Criteria criteria = session.createCriteria(Product.class)
				.add(Restrictions.and(
						Restrictions.eq("organizationID", organizationID), 
						Restrictions.and(
								Restrictions.eq("branchID", branchID), 
								Restrictions.or(
										Restrictions.like("description", description, MatchMode.START).ignoreCase(),
										Restrictions.eq("productID", productID)))))
				.add(Restrictions.eq("excluded", false))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(Order.asc("productID"));
		return criteria.list();
	}

}
