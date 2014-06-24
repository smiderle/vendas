package br.com.vendas.services.facade.location;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.support.ServiceResponse;

public interface OrganizationServiceFacade {
	
	/**
	 * Salva a empresa.
	 */
	ServiceResponse<Organization> save(Organization organization);
}
