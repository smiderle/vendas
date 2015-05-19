package br.com.vendas.dao.organization;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.organization.Organization;

public interface OrganizationDAO  extends GenericDAO<Organization> {

	List<Organization> findAll();

	Organization findByOrganizationId( Date date, Integer organizationID ) ;


}
