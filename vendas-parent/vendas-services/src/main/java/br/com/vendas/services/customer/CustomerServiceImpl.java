package br.com.vendas.services.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.core.util.PictureUtil;
import br.com.vendas.dao.customer.CustomerDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.exception.ApplicationException;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.order.payment.OrderPaymentService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerDAO customerDAO;

	@Inject
	private OrderPaymentService orderPaymentService;

	@Inject
	private UserActionService userActionService;

	@Override
	public ServiceResponse<List<Customer>> findAllByOrganizationID(Integer organizationID, Integer branchID, Integer offset) {

		List<Customer> customers = customerDAO.findAllByOrganizationID(organizationID, branchID, offset, LimitQuery.LIMIT_CUSTOMER.value());

		return ServiceResponseFactory.create(customers);
	}



	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Customer> save( Integer userID, Customer customer ) {
		customer.setChangeTime(new Date());
		saveUserAction(userID, customer);
		return  ServiceResponseFactory.create( customerDAO.saveOrUpdate( customer ) );
	}


	@Override
	public ServiceResponse<List<Customer>> findByIDOrNameOrEmail(String filter,Integer organizationID, Integer branchID, Integer offset, Integer limit) {

		List<Customer> customers = customerDAO.findByIDOrNameOrCpf(filter, filter,filter, organizationID, branchID, offset, getLimit(limit));
		return ServiceResponseFactory.create(customers);
	}


	@Override
	public ServiceResponse<BigDecimal> getAvaliableCreditLimit(Integer id) throws ApplicationException {

		BigDecimal totalValuePending = orderPaymentService.getTotalValuePending(id).getValue();

		Customer customer = findByID(id).getValue();

		if(customer != null) {
			BigDecimal creditLimitBig = null;

			if(customer.getCreditLimit() == null){
				creditLimitBig = new BigDecimal("0.0");
			} else {
				creditLimitBig = new BigDecimal(String.valueOf(customer.getCreditLimit()));
			}

			return ServiceResponseFactory.create(creditLimitBig.subtract(totalValuePending));

		} else {
			throw new ApplicationException("Não foi possivel consultar o limite de crédito do cliente. Motivo: Cliente não localizado.");
		}
	}

	@Override
	public ServiceResponse<Customer> findByID(Integer id) {
		return ServiceResponseFactory.create(customerDAO.findByID(id));
	}

	@Override
	public ServiceResponse<Boolean> hasExpiratePayment(Integer customerID) {
		Long rowCount = orderPaymentService.findAllOverdueByCustomer(customerID).getRowCount();
		return ServiceResponseFactory.create(rowCount > 0);
	}

	/**
	 * Salva as alções do usuário para o cadastro ou edição de um novo cliente
	 * @param userID
	 * @param customer
	 */
	private void saveUserAction( Integer userID, Customer customer ){
		UserAction userAction = null;

		if(customer.isExcluded()){
			userAction = UserActionHelper.createCustomerDelete(userID, customer);
		} else if(customer.getID() == null) {
			userAction = UserActionHelper.createCustomerSave(userID, customer);
		} else {
			userAction = UserActionHelper.createCustomerEdit(userID, customer);

		}

		userActionService.save( userAction );

	}

	private Integer getLimit(Integer limit) {
		Integer defaultLimit = LimitQuery.LIMIT_CUSTOMER.value();
		if(limit != null && limit < LimitQuery.LIMIT_CUSTOMER.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}



	@Override
	@Transactional(readOnly=false)
	public ServiceResponse<List<Customer>> save(List<Customer> customers) {

		List<Customer> customersSaved = new ArrayList<>();

		for (Customer customer : customers) {

			if(customer.getPictureUrl() == null){

				customer.setPictureUrl(PictureUtil.URL_USUARIO_SEM_FOTO);

			}

			customer.setID(null);

			customer.setChangeTime(new Date());

			Customer customerSaved = customerDAO.saveOrUpdate(customer );

			customersSaved.add( customerSaved );

		}


		return ServiceResponseFactory.create(customersSaved);
	}


	@Override
	@Transactional(readOnly = false)
	public ServiceResponse<List<Customer>> update(List<Customer> customers) {

		List<Customer> customersSaved = new ArrayList<>();

		for (Customer customer : customers) {

			if (customer.getPictureUrl() == null) {

				customer.setPictureUrl(PictureUtil.URL_USUARIO_SEM_FOTO);

			}

			customer.setChangeTime(new Date());

			Customer customerSaved = customerDAO.saveOrUpdate(customer);

			customersSaved.add(customerSaved);

		}


		return ServiceResponseFactory.create(customersSaved);
	}

	@Override
	public ServiceResponse<List<Customer>> findAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		return ServiceResponseFactory.create(customerDAO.findAllByChangeGreaterThan(new Date(date), organizationID, offset, LimitQuery.LIMIT_SYNC_INIT_LOAD.value()));
	}
}
