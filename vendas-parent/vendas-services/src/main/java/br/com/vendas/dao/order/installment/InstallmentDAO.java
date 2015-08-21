package br.com.vendas.dao.order.installment;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.domain.order.PriceTable;

public interface InstallmentDAO extends GenericDAO<Installment> {
	
	/**
	 * Retorna todas os parcelamentos de determinada filial
	 * @param organizationID
	 * @param branchID
	 * @return
	 */
	List<Installment> findAllByBranche(Integer organizationID, Integer branchID);
	

	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<Installment> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );

}
