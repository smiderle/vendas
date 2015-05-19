package br.com.vendas.dao.order.pricetable;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.PriceTable;

public interface PriceTableDAO extends GenericDAO<PriceTable>  {
	
	/**
	 * Retorna todas as tabelas de preço de determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	List<PriceTable> findAllByBranche(Integer organizationID, Integer branchID);
	

	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<PriceTable> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );

}