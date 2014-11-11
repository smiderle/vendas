package br.com.vendas.dao.customer;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.product.Product;

@Repository
public class CustomerDAOImpl extends ResourceDAO<Customer> implements CustomerDAO {

	@Override
	public List<Customer> findAllByOrganizationID(Integer organizationID,Integer branchID, Integer offset, Integer limit) {

		Session session = getSession();		
		Criteria criteria = session.createCriteria(Customer.class)

				.add(Restrictions.eq("excluded", false))
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))

				.setFirstResult(offset)
				.setMaxResults(limit);

		return criteria
				.addOrder(Order.asc("ID")).list();
	}

	@Override
	public List<Customer> findByIDOrNameOrCpf(String name,String cpfCnpj, String customerID,Integer organizationID, Integer branchID, Integer offset,Integer limit) {

		Session session = getSession();

		Criteria criteria = session.createCriteria(Customer.class)
				.add(Restrictions.and(
						Restrictions.eq("organizationID", organizationID), 
						Restrictions.and(
								Restrictions.eq("branchID", branchID), 
								Restrictions.or(
										Restrictions.like("name", name, MatchMode.START).ignoreCase(),
										Restrictions.or(
												Restrictions.eq("customerID", customerID), 
												Restrictions.eq("cpfCnpj", cpfCnpj))))))
				.add(Restrictions.eq("excluded", false))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("ID"));
		return criteria.list();
	}

	@Override
	public Customer findByID(Integer id) {
		
		Session session = getSession();
		
		Criteria criteria = session.createCriteria(Customer.class)

				.add(Restrictions.eq("id", id));

		return (Customer) criteria.uniqueResult();
	}

}
