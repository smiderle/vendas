package br.com.vendas.dao.customer;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.product.Product;

public interface CustomerDAO extends GenericDAO<Customer> {
	
	/**
	 * Retorna todos os clientes por empresa.
	 * @param organizationID
	 * @return
	 */
	List<Customer> findAllByOrganizationID(Integer organizationID,Integer branchID, Integer offset, Integer limit);

	/**
	 * Retorna todos os que iniciem com o nmoe, email ou pelo código passado no filter.
	 * @param filter
	 * @param organizationID
	 * @param userID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Customer> findByIDOrNameOrCpf(String name,String cpfCnpj,String customerID,Integer organizationID, Integer branchID, Integer offset,Integer limit);
	
	/**
	 * Retorna o cliente pelo id
	 * @param id
	 * @return
	 */
	Customer findByID(Integer id);


	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<Customer> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );
}
