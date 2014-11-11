package br.com.vendas.dao.order.installment;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.order.Installment;

@Repository
public class InstallmentDAOImpl extends ResourceDAO<Installment> implements InstallmentDAO{

	@Override
	public List<Installment> findAllByBranche(Integer organizationID, Integer branchID) {
		Criterion criterion =
				Restrictions.and(
						Restrictions.eq("excluded", false),
						Restrictions.and(
									Restrictions.eq("organizationID", organizationID), 
									Restrictions.eq("branchID", branchID)));				
		return findByCriteria(Order.asc("installmentID"), criterion);
		
	}

}
