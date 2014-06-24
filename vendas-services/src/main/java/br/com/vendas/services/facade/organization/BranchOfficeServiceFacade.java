package br.com.vendas.services.facade.organization;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.support.ServiceResponse;

public interface BranchOfficeServiceFacade {

	ServiceResponse<List<BranchOffice>> findAllByOrganization(Organization organization);
	
	void save(BranchOffice branchOffice);

}
