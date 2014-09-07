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
	ServiceResponse<List<Installment>> findAllByBranche(Long organizationID, Long branchID);
	
	/**
	 * Salva o parcelamento
	 * @param priceTable
	 * @return
	 */
	ServiceResponse<Installment> save(Installment installment);
	
	/**
	 * Atualiza o parcelamento
	 * @param priceTable
	 * @return
	 * @throws RegistrationException
	 */
	ServiceResponse<Installment> saveOrUpdate(Installment installment) ;
}