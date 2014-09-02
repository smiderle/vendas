package br.com.vendas.dao.user;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.User;

@Repository
public class UserDAOImpl  extends ResourceDAO<User> implements UserDAO{

	@Override
	public List<User> findAllByOrganizationID(Long organizationID, Integer offset, Integer limit) {		
		
		Session session = getSession();		
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("organizationID", organizationID))
			.setFirstResult(offset)
			.setMaxResults(limit);
		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria
				.addOrder(Order.asc("email")).list();
	}

	@Override
	public User findUserByEmail(String email) {		
		Session session = getSession();		
		Criterion criterion = Restrictions.eq("email",email);

		return (User) session.createCriteria(User.class)
				.add(criterion).uniqueResult();
	}

	/**
	 * SELECT * FROM USER WHERE ORGANIZATIONID = ? AND  (NAME LIKE '?' OR EMAIL LIKE '?' OR USERID = ?)
	 */
	@Override
	public List<User> findUsersByUserIDOrNameOrEmail(String filter,
			Long organizationID, Long userID, Integer offset, Integer limit) {
				
		

		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class)
				.add(
						Restrictions.and(
								Restrictions.eq("organizationID", organizationID),
								Restrictions.or(Restrictions.eq("userID", userID),
												Restrictions.or(
														Restrictions.like("name", filter, MatchMode.START).ignoreCase(),
														Restrictions.like("email", filter, MatchMode.START).ignoreCase()))))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("email"));
				
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}
}
