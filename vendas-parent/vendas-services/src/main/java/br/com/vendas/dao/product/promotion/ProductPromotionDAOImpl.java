package br.com.vendas.dao.product.promotion;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.product.ProductPromotion;

@Repository
public class ProductPromotionDAOImpl  extends ResourceDAO<ProductPromotion> implements ProductPromotionDAO{

	@Override
	public List<ProductPromotion> findAllByProductID(Integer productID, Date afterDate) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(ProductPromotion.class)
				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("productID", productID))
				.add(Restrictions.gt("finalDate", afterDate))
				.addOrder(Order.asc("initialDate"));
		return criteria.list();
	}

	@Override
	public List<ProductPromotion> findByByInitalDateAndFinalDate(Integer productID, Date finalDate, Date initialDate, Integer productPromotionID) {


		Session session = getSession();

		Criteria criteria = session.createCriteria(ProductPromotion.class)
				.add(
						Restrictions.or(
								Restrictions.or(
										Restrictions.between("initialDate", initialDate, finalDate),
										Restrictions.between("finalDate", initialDate, finalDate)),
										Restrictions.and(
												Restrictions.le("initialDate", initialDate),
												Restrictions.ge("finalDate", finalDate))))

												.add(Restrictions.eq("excluded", false))
												.add(Restrictions.ne("ID", productPromotionID))
												.add(Restrictions.eq("productID", productID));

		return criteria.list();
	}


	@Override
	public List<ProductPromotion> findAllByChangeGreaterThan( Date date, Integer organizationID, Integer offset, Integer limit ) {

		Session session = getSession();


		Criteria criteria = session.createCriteria(ProductPromotion.class)
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.ge("changeTime", date))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("changeTime"));

		return criteria.list();
	}

}
