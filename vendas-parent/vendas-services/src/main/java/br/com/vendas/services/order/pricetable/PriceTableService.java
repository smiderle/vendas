package br.com.vendas.services.order.pricetable;

import java.util.List;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.PriceTable;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;

public interface PriceTableService {
	
	/**
	 * Retorna todas as tabelas de preço de determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	ServiceResponse<List<PriceTable>> findAllByBranche(Integer organizationID, Integer branchID);
	
	/**
	 * Salva a tabela de preço
	 * @param priceTable
	 * @return
	 */
	ServiceResponse<PriceTable> save( Integer userID, PriceTable priceTable );
	
	/**
	 * Atualiza a tabela de preço
	 * @param priceTable
	 * @return
	 * @throws RegistrationException
	 */
	ServiceResponse<PriceTable> saveOrUpdate( Integer userID, PriceTable priceTable ) ;
	
	
	void save(List<PriceTable> priceTables);
	

	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	ServiceResponse<List<PriceTable>> findAllByChangeGreaterThan( Long date, Integer organizationID, Integer offset );

}
