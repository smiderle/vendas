package br.com.vendas.services.organization;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.domain.product.Product;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;

public interface BranchOfficeService {
	
	/**
	 * Retorna todas as filiais por em empresa.
	 * @param organizationID
	 * @return
	 */
	ServiceResponse<List<BranchOffice>> findAllByOrganizationID(Integer organizationID);
	
	/**
	 * Retorna a filial.
	 * @param organInteger
	 * @param branchOfficeID
	 * @return
	 */
	ServiceResponse<BranchOffice> findByOrganizationIDAndBranchOfficeID(Integer organizationID, Integer branchOfficeID);
	
	/**
	 * Salva a filial, caso exista uma filial com o mesmo id, é lançado uma exception.
	 * @param branchOffice
	 * @return
	 * @throws RegistrationException
	 */
	ServiceResponse<BranchOffice> save (Integer userID, BranchOffice branchOffice) throws RegistrationException;
	
	/**
	 * Retorna o proximo idfilial de determinada empresa.
	 * @return
	 */
	ServiceResponse<Long> findNextBranchOfficeIDByOrganization(Organization organization);
	
	/**
	 * Salva ou atualiza a filial.
	 * @param branchOffice
	 * @return
	 */
	ServiceResponse<BranchOffice> saveOrUpdate(Integer userID, BranchOffice branchOffice);

	
	

	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	ServiceResponse<List<BranchOffice>> findAllByChangeGreaterThan( Long date,Integer organizationID, Integer offset );

}
