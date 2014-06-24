package br.com.vendas.services.facade.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.facade.organization.OrganizationRepositoryFacade;
import br.com.vendas.repository.support.ServiceResponse;
import br.com.vendas.repository.support.ServiceResponseFactory;
import br.com.vendas.services.facade.location.OrganizationServiceFacade;

@Service
public class OrganizationServiceImpl implements OrganizationServiceFacade{

	@Autowired
	private OrganizationRepositoryFacade organizationRepository;
	
	@Override
	public ServiceResponse<Organization> save(Organization organization) {
		organizationRepository.save(organization);
		return ServiceResponseFactory.create(organization);
	}

}
