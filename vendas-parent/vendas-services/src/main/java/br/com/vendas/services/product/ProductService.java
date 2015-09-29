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
	ServiceResponse<List<Product>> findAllByBranche(Integer organizationID, Integer branchID, Integer offset);


	/**
	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
	 * @param filter
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @return
	 */
	ServiceResponse<List<Product>> findByDescriptionOrProductID(String filter, Integer organizationID,Integer branchID, Integer offset, Integer limit);

	/**
	 * Salva ou atualiza o produto
	 * @param product
	 * @return
	 */
	ServiceResponse<Product> saveOrUpdate(Integer userID, Product product) ;

	/**
	 * Salva uma lista de produtos
	 */
	void saveOrUpdate(List<Product> products) ;




	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	ServiceResponse<List<Product>> findAllByChangeGreaterThan( Long date,Integer organizationID, Integer offset );


	/**
	 * Salva uma lista de produtos
	 */
	ServiceResponse<List<Product>> save(List<Product> products) ;

	ServiceResponse<List<Product>> update(List<Product> products) ;

}