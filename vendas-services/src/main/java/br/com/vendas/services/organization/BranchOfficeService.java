package br.com.vendas.services.organization;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.services.support.ServiceResponse;

public interface BranchOfficeService {
	
	/**
	 * Retorna todas as filiais por em empresa
	 * @param organizationID
	 * @return
	 */
	ServiceResponse<List<BranchOffice>> findAllByOrganizationID(Long organizationID);
	
	/**
	 * Retorna a filial
	 * @param organInteger
	 * @param branchOfficeID
	 * @return
	 */
	ServiceResponse<BranchOffice> findByOrganizationIDAndBranchOfficeID(Long organizationID, Long branchOfficeID);
	
	
	ServiceResponse<BranchOffice> save (BranchOffice branchOffice);
	
	/**
	 * Retorna o proximo idfilial de determinada empresa.
	 * @return
	 */
	ServiceResponse<Long> findNextBranchOfficeIDByOrganization(Organization organization);


}
