package br.com.vendas.services.order.installment;

import java.util.List;

import br.com.vendas.domain.order.Installment;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;

public interface InstallmentService {

	/**
	 * Retorna todas os parcelamentos determinada filial
	 * @param empresaID
	 * @param filialID
	 * @return
	 */
	ServiceResponse<List<Installment>> findAllByBranche(Integer organizationID, Integer branchID);

	/**
	 * Salva o parcelamento
	 * @param priceTable
	 * @return
	 */
	ServiceResponse<Installment> save(Integer userID, Installment installment);

	/**
	 * Atualiza o parcelamento
	 * @param priceTable
	 * @return
	 * @throws RegistrationException
	 */
	ServiceResponse<Installment> saveOrUpdate(Integer userID, Installment installment) ;


	void save(List<Installment> installments);

	ServiceResponse<List<Installment>> saveList(List<Installment> customers);

	ServiceResponse<List<Installment>> updateList(List<Installment> customers);


	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	ServiceResponse<List<Installment>> findAllByChangeGreaterThan( Long date, Integer organizationID, Integer offset );
}
