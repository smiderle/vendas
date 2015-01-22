package br.com.vendas.services.customer;

import java.math.BigDecimal;
import java.util.List;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.exception.ApplicationException;
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
	ServiceResponse<Customer> save(Integer userID, Customer customer);
	
 	/**
 	 * Retorna todos os produtos que iniciem com a descrição, ou código do produto, ou código de barras passada no filter.
 	 * @param filter
 	 * @param organizationID
 	 * @param branchID
 	 * @param offset
 	 * @return
 	 */
 	ServiceResponse<List<Customer>> findByIDOrNameOrEmail(String filter,Integer organizationID, Integer branchID, Integer offset, Integer limit);
 	
 	/**
 	 * Retorna o limite de crédito do cliente
 	 * @param customerID
 	 * @return
 	 * @throws ApplicationException 
 	 */
 	ServiceResponse<BigDecimal> getAvaliableCreditLimit(Integer customerID) throws ApplicationException;
 	
 	/**
 	 * Retorna o cliente por id
 	 * @param id
 	 * @return
 	 */
 	ServiceResponse<Customer> findByID(Integer id);
 	
	/**
	 * Tem pagamento vencido ?
	 * @param customerID
	 * @return
	 */
	ServiceResponse<Boolean> hasExpiratePayment(Integer customerID);
	
	
	
	void save( List<Customer> customers );
}
