package br.com.vendas.repository.facade.organization;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.support.ServiceResponse;

public interface BranchOfficeRepositoryFacade {

	/**
	 * Retorna uma lista das filiais.
	 * @return
	 */	
	public List<BranchOffice> findAllByOrganization(Organization organizatio);
	
	/**
	 * Salva a filial.
	 */
	public void save(BranchOffice branchOffice);
	

}
