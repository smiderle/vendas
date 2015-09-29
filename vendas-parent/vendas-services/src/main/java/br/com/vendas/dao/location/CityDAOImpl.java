package br.com.vendas.dao.location;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.location.City;
import br.com.vendas.domain.user.User;

@Repository
public class CityDAOImpl extends ResourceDAO<City>  implements CityDAO{

	@Override
	public List<City> findAllByDescription(String description, Integer offset, Integer limit) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(City.class)
				.add(Restrictions.like("name", description, MatchMode.START).ignoreCase())						
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("name"));
		return criteria.list();
	}

	@Override
	public List<City> findByIBGECode(Integer ibgeCode) {		
		Criterion criterion = Restrictions.eq("ibgeCode", ibgeCode);
		return findByCriteria(criterion);		
	}

}
