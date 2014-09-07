package br.com.vendas.services.order.pricetable;

import java.util.List;

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
	ServiceResponse<List<PriceTable>> findAllByBranche(Long organizationID, Long branchID);
	
	/**
	 * Salva a tabela de preço
	 * @param priceTable
	 * @return
	 */
	ServiceResponse<PriceTable> save(PriceTable priceTable);
	
	/**
	 * Atualiza a tabela de preço
	 * @param priceTable
	 * @return
	 * @throws RegistrationException
	 */
	ServiceResponse<PriceTable> saveOrUpdate(PriceTable priceTable) ;

}