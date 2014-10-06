package br.com.vendas.services.customer;

import java.util.List;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.services.support.ServiceResponse;

public interface CustomerService {
	
	/**
	 * Retorna todos os clientes de determinada empresa
	 * @param organizationID
	 * @return
	 */
	ServiceResponse<List<Customer>> findAllByOrganizationID(Integer organizationID, Integer branchID, Integer offset);
	
	/**
	 * Salva o cliente
	 * @param customer
	 * @return
	 */
	ServiceResponse<Customer> save(Customer customer);
	
 	/**
 	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
 	 * @param filter
 	 * @param organizationID
 	 * @param branchID
 	 * @param offset
 	 * @return
 	 */
 	ServiceResponse<List<Customer>> findByIDOrNameOrEmail(String filter,Integer organizationID, Integer branchID, Integer offset);
}
