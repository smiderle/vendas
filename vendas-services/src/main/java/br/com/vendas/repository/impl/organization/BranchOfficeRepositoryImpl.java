package br.com.vendas.repository.impl.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.facade.organization.BranchOfficeRepositoryFacade;

@Repository
public class BranchOfficeRepositoryImpl implements BranchOfficeRepositoryFacade{

	@Autowired
	private MongoOperations mongoOperation;
	
	@Override
	public List<BranchOffice> findAllByOrganization(Organization organization) {
		Query query = new Query(Criteria.where("organization").is(organization));
		return mongoOperation.find(query, BranchOffice.class);
	}

	@Override
	public void save(BranchOffice branchOffice) {
		mongoOperation.save(branchOffice);
	}
}
