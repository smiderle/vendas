package br.com.vendas.dao.product;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.product.Product;

public interface ProductDAO extends GenericDAO<Product> {
	
	
	/**
	 * Retorna todos os produtos de determinada filial
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Product> findAllByBranch(Integer organizationID, Integer branchID, Integer offset, Integer limit);
	
	

	/**
	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
	 * @param description - 
	 * @param organizationID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Product> findByDescriptionOrProductID(String description,String productID,String barcode, Integer organizationID,Integer branchID, Integer offset, Integer limit);

	
	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<Product> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );
}
