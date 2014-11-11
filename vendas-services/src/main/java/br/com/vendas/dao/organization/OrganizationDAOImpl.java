package br.com.vendas.dao.organization;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.organization.Organization;

@Repository
public class OrganizationDAOImpl extends ResourceDAO<Organization>  implements OrganizationDAO{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> findAll() {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Organization.class);
		return criteria.list();
	}

}
