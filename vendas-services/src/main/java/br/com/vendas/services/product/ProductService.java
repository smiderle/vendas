package br.com.vendas.services.product;

import java.util.List;

import br.com.vendas.domain.product.Product;
import br.com.vendas.services.support.ServiceResponse;

public interface ProductService {
		
	/**
	 * Retorna todos os produtos de determinada filial
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @return
	 */
 	ServiceResponse<List<Product>> findAllByBranche(Long organizationID, Long branchID, Integer offset);
	
	
 	/**
 	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
 	 * @param filter
 	 * @param organizationID
 	 * @param branchID
 	 * @param offset
 	 * @return
 	 */
 	ServiceResponse<List<Product>> findByDescriptionOrProductIDOrBarcode(String filter, Long organizationID,Long branchID, Integer offset);
 	
 	/**
 	 * Salva ou atualiza o produto
 	 * @param product
 	 * @return
 	 */
	ServiceResponse<Product> saveOrUpdate(Product product) ;

}