package br.com.vendas.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserAction;

@Repository
public class UserActionDAOImpl  extends ResourceDAO<UserAction> implements UserActionDAO {

	@Override
	public List<UserAction> findByUser(Integer userID, Integer offset, Integer limit) {
		
		Session session = getSession();		
		Criteria criteria = session.createCriteria(UserAction.class);
		criteria.add(Restrictions.eq("userID", userID))
			.setFirstResult(offset)
			.setMaxResults(limit);
		
		
		return criteria
				.addOrder(Order.desc("id")).list();
	}

}
