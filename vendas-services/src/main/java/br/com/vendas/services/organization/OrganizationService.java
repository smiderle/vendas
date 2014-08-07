package br.com.vendas.services.organization;

import java.util.List;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.services.support.ServiceResponse;

public interface OrganizationService {
	
	/**
	 * Salva a empresa.
	 */
	ServiceResponse<Organization> save(Organization organization);
	
	ServiceResponse<List<Organization>> findAll();
	
	
}
