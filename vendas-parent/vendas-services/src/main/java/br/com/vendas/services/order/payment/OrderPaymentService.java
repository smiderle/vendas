package br.com.vendas.services.order.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.dto.OrderPaymentDTO;
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


	/**
	 * Retorna todos as parcelas em aberto
	 * @param organizationID
	 * @param branchID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<OrderPaymentDTO>> findAllPendingByOrganizationID(Integer organizationID, Integer branchID, Integer offset, Integer limit);

	/**
	 * Retorna todas as parcelas pendetes de determinado cliente ou pedido
	 * @param organizationID
	 * @param branchID
	 * @param customerID
	 * @param orderID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<OrderPaymentDTO>> findPendingByOrderIDOrCustomerID(Integer organizationID, Integer branchID, String filter, Integer offset, Integer limit);
	
	/**
	 * Retorna as parcelas de determinado pedido
	 * @param orderID
	 * @return
	 */
	ServiceResponse<OrderPaymentDTO> findByID(Long orderID);
		
	/**
	 * Atualiza a data de pagamento da parcela, setando como paga.
	 * @param orderPayment
	 * @return
	 */
	ServiceResponse<Boolean> setPaid(Integer userID, Long id);
	
	
	/**
	 * Retorna os pagamentos respeitando os filtros
	 * @param organizationID
	 * @param branchID
	 * @param customerID
	 * @param issuanceDateGte
	 * @param issuanceDateLte
	 * @param expirationDateGte
	 * @param expirationDateLte
	 * @param statusPayment
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<OrderPaymentDTO>> findByComplexFilter(Integer organizationID, Integer branchID, String customerID, Long issuanceDateGte,
			Long issuanceDateLte, Long expirationDateGte, Long expirationDateLte,String statusPayment, Integer formPayment, Integer offset, Integer limit);

}
