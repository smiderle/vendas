package br.com.vendas.services.product.group;

import java.util.List;

import br.com.vendas.domain.product.ProductGroup;
import br.com.vendas.services.support.ServiceResponse;

public interface ProductGroupService {
	
	/**
	 * Retorna todas os grupos de determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	ServiceResponse<List<ProductGroup>> findAllByBranch(Long organizationID, Long branchID, Integer offset);
	
	

	/**
	 * Retorna todos os grupos que iniciem com a descrição passada no filter.
	 * @param description - 
	 * @param organizationID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<ProductGroup>> findAllByDescription(String description, Long organizationID,Long branchID, Integer offset);


	/**
	 * Salva ou atualiza o grupo de produtos
	 * @param productGroup
	 * @return
	 */
	ServiceResponse<ProductGroup> saveOrUpdate(ProductGroup productGroup) ;



}
