package br.com.vendas.repository.impl.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.Util.RepositoryUtil;
import br.com.vendas.repository.facade.organization.OrganizationRepositoryFacade;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepositoryFacade{
	
	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public void save(Organization organization) {
		mongoOperation.save(organization);
		
	}

	@Override
	public List<Organization> findAll() {
		Query query = new Query();
		query.limit(RepositoryUtil.LIMIT_QUERY_ORGANIZATION);		
		return mongoOperation.find(query,Organization.class);		
	}

}
