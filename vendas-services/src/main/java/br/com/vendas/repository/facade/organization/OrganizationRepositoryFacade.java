package br.com.vendas.repository.facade.organization;

import java.util.List;

import br.com.vendas.domain.organization.Organization;

public interface OrganizationRepositoryFacade {
	
	void save(final Organization organization);
	
	List<Organization> findAll();

	
}
