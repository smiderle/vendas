package br.com.vendas.dao.user;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.User;

@Repository
public class UserDAOImpl  extends ResourceDAO<User> implements UserDAO{

	@Override
	public List<User> findAllByOrganizationID(Integer organizationID, Integer offset, Integer limit) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("organizationID", organizationID))
		.setFirstResult(offset)
		.setMaxResults(limit);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria
				.addOrder(Order.asc("name")).list();
	}

	@Override
	public List<User> findOtherUsersByOrganizationID(Integer organizationID,Integer userID, Integer offset, Integer limit) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("organizationID", organizationID))
		.add(Restrictions.ne("userID", userID))
		.setFirstResult(offset)
		.setMaxResults(limit);

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria
				.addOrder(Order.asc("name")).list();
	}

	@Override
	public User findUserByEmail( String email ) {
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
			Integer organizationID, Integer userID, Integer offset, Integer limit) {

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

	@Override
	public User findUserByID( Integer userID ) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class)
				.add(Restrictions.eq("userID", userID));

		return (User) criteria.uniqueResult();
	}



	@Override
	public List<User> findAllByChangeGreaterThan(Date date, Integer organizationID, Integer offset, Integer limit) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class)
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.ge("changeTime", date))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("changeTime"));

		return criteria.list();

	}

	@Override
	public User findUserByEmailAndPassword(String email, String password) {
		Session session = getSession();

		Criteria criteria = session.createCriteria(User.class);

		criteria.add(Restrictions.eq("email",email) );
		criteria.add(Restrictions.eq("password",password) );

		return (User) criteria.uniqueResult();
	}
}
