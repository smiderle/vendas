package br.com.vendas.dao.organization;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.domain.product.Product;

public interface BranchOfficeDAO extends GenericDAO<BranchOffice>{
	
	/**
	 * Retorna todas as filiais por em empresa
	 * @param organizationID
	 * @return
	 */
	List<BranchOffice> findAllByOrganizationID(Integer organizationID);
	
	/**
	 * Retorna a filial
	 * @param organInteger
	 * @param branchOfficeID
	 * @return
	 */
	BranchOffice findByOrganizationIDAndBranchOfficeID(Integer organizationID, Integer branchOfficeID);
	
	/**
	 * Retorna o maior idfilial de determinada empresa. Se não existir nenhuma, retorna 0
	 * @return
	 */
	Long findMaxBranchOfficeIDByOrganization(Organization organization);
	
	

	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<BranchOffice> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );

}
