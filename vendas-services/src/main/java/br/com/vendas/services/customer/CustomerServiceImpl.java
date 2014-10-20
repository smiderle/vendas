package br.com.vendas.services.customer;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.customer.CustomerDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerDAO customerDAO;
	
	@Override
	public ServiceResponse<List<Customer>> findAllByOrganizationID(Integer organizationID, Integer branchID, Integer offset) {
		
		List<Customer> customers = customerDAO.findAllByOrganizationID(organizationID, branchID, offset, LimitQuery.LIMIT_CUSTOMER.value());
		
		return ServiceResponseFactory.create(customers);
	}
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Customer> save(Customer customer) {
		customer.setChangeTime(new Date());
		return  ServiceResponseFactory.create(customerDAO.saveOrUpdate(customer));
	}

	@Override
	public ServiceResponse<List<Customer>> findByIDOrNameOrEmail(String filter,Integer organizationID, Integer branchID, Integer offset, Integer limit) {
				
		List<Customer> customers = customerDAO.findByIDOrNameOrCpf(filter, filter,filter, organizationID, branchID, offset, getLimit(limit));
		return ServiceResponseFactory.create(customers);		
	}
	
	private Integer getLimit(Integer limit){
		Integer defaultLimit = LimitQuery.LIMIT_CUSTOMER.value();
		if(limit != null && limit < LimitQuery.LIMIT_CUSTOMER.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}

}
