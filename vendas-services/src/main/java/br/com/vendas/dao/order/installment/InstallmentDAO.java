package br.com.vendas.dao.order.installment;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.order.Installment;

public interface InstallmentDAO extends GenericDAO<Installment> {
	
	/**
	 * Retorna todas os parcelamentos de determinada filial
	 * @param organizationID
	 * @param branchID
	 * @return
	 */
	List<Installment> findAllByBranche(Integer organizationID, Integer branchID);

}
