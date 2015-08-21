package br.com.vendas.dao.application;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.application.License;

@Repository
public class LicenseDAOImpl extends ResourceDAO<License> implements LicenseDAO {

	@Override
	public License findByUserId(Integer userId) {

		Session session = getSession();

		Criteria criteria = session.createCriteria(License.class)
				.add(Restrictions.eq("user.userID", userId));

		return (License) criteria.uniqueResult();

	}

}
