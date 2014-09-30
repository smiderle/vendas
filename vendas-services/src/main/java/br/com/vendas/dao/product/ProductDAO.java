package br.com.vendas.dao.product;

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
	List<Product> findAllByBranche(Long organizationID, Long branchID, Integer offset, Integer limit);
	
	

	/**
	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
	 * @param description - 
	 * @param organizationID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Product> findByDescriptionOrProductIDOrBarcode(String description,String productID,String barcode, Long organizationID,Long branchID, Integer offset, Integer limit);

}
