package br.com.vendas.dao.product.group;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.product.ProductGroup;

public interface ProductGroupDAO extends GenericDAO<ProductGroup>{
	
	/**
	 * Retorna todas os grupos de determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	List<ProductGroup> findAllByBranche(Integer organizationID, Integer branchID, Integer offset, Integer limit);
	
	

	/**
	 * Retorna todos os grupos que iniciem com a descrição passada no filter.
	 * @param description - 
	 * @param organizationID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<ProductGroup> findByDescription(String description, Integer organizationID,Integer branchID, Integer offset, Integer limit);

}
