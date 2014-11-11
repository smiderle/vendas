package br.com.vendas.services.order.payment;

import java.math.BigDecimal;
import java.util.List;

import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.services.support.ServiceResponse;

public interface OrderPaymentService {
	
	/**
	 * Retorna o valor total de parcelas pendentes
	 * @param customerID
	 * @return
	 */
	ServiceResponse<BigDecimal> getTotalValuePending(Integer customerID);
	
	
	/**
	 * Retorna todos os pagamentos vencidos de determinado cliente
	 * @param customerID
	 * @param date
	 * @return
	 */
	ServiceResponse<List<OrderPayment>> findAllOverdueByCustomer(Integer customerID); 


}
