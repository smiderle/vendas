package br.com.vendas.services.organization;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.organization.OrganizationDAO;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrganizationServiceImpl implements OrganizationService{
	
	@Inject
	private OrganizationDAO organizationDAO;


	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Organization> save(Organization organization) {
		return ServiceResponseFactory.create(organizationDAO.save(organization));
	}

	@Override
	public ServiceResponse<List<Organization>> findAll() {
		  return ServiceResponseFactory.create(organizationDAO.findAll());
	}
}
