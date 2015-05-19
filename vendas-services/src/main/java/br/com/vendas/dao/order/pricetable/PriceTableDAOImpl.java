package br.com.vendas.dao.order.pricetable;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.PriceTable;

@Repository
public class PriceTableDAOImpl  extends ResourceDAO<PriceTable> implements PriceTableDAO{

	@Override
	public List<PriceTable> findAllByBranche(Integer organizationID, Integer branchID) {
		Criterion criterion =
				Restrictions.and(
						Restrictions.eq("excluded", false),
						Restrictions.and(
									Restrictions.eq("organizationID", organizationID), 
									Restrictions.eq("branchID", branchID)));				
		return findByCriteria(Order.asc("priceTableID"), criterion);
		
	}

	@Override
	public List<PriceTable> findAllByChangeGreaterThan(Date date, Integer organizationID, Integer offset, Integer limit) {
				
		Session session = getSession();
		Criteria criteria = session.createCriteria(PriceTable.class)				
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.ge("changeTime", date))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(Order.asc("changeTime"));
		
		return criteria.list();
	
	}

}
