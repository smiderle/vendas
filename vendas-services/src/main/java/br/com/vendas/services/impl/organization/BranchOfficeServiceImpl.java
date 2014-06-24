package br.com.vendas.services.impl.organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.facade.organization.BranchOfficeRepositoryFacade;
import br.com.vendas.repository.support.ServiceResponse;
import br.com.vendas.repository.support.ServiceResponseFactory;
import br.com.vendas.services.facade.organization.BranchOfficeServiceFacade;

@Service
public class BranchOfficeServiceImpl implements BranchOfficeServiceFacade{
	
	@Autowired
	private BranchOfficeRepositoryFacade branchOfficeRepositoryFacade;

	@Override
	public ServiceResponse<List<BranchOffice>> findAllByOrganization(Organization organization) {		
		return ServiceResponseFactory.create(branchOfficeRepositoryFacade.findAllByOrganization(organization));
	}

	@Override
	public void save(BranchOffice branchOffice) {
		branchOfficeRepositoryFacade.save(branchOffice);
		
	}

	
}
