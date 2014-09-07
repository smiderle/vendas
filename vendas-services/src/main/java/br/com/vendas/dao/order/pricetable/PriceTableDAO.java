package br.com.vendas.dao.order.pricetable;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.order.PriceTable;

public interface PriceTableDAO extends GenericDAO<PriceTable>  {
	
	/**
	 * Retorna todas as tabelas de preço de determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	List<PriceTable> findAllByBranche(Long organizationID, Long branchID);

}