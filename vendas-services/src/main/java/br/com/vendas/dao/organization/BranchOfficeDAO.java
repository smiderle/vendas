package br.com.vendas.dao.organization;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;

public interface BranchOfficeDAO extends GenericDAO<BranchOffice>{
	
	/**
	 * Retorna todas as filiais por em empresa
	 * @param organizationID
	 * @return
	 */
	List<BranchOffice> findAllByOrganizationID(Long organizationID);
	
	/**
	 * Retorna a filial
	 * @param organInteger
	 * @param branchOfficeID
	 * @return
	 */
	BranchOffice findByOrganizationIDAndBranchOfficeID(Long organizationID, Long branchOfficeID);
	
	/**
	 * Retorna o maior idfilial de determinada empresa. Se não existir nenhuma, retorna 0
	 * @return
	 */
	Long findMaxBranchOfficeIDByOrganization(Organization organization);

}
