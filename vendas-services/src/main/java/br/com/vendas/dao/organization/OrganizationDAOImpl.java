package br.com.vendas.dao.organization;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.organization.Organization;

@Repository
public class OrganizationDAOImpl extends ResourceDAO<Organization>  implements OrganizationDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> findAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organization.class);
		return criteria.list();
	}

	@Override
	public Organization findByOrganizationId( Date date, Integer organizationID ) {

		Session session = getSession();
		Criteria criteria = session.createCriteria(Organization.class)
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.ge("changeTime", date));

		return (Organization) criteria.uniqueResult();
	}

}
