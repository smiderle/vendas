package br.com.vendas.dao.order.payment;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.order.PaymentStatus;

@Repository
public class OrderPaymentDAOImpl extends ResourceDAO<OrderPayment> implements OrderPaymentDAO  {

	@Override
	public Double getTotalValuePending(Integer customerID) {
		
		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.createAlias("order", "o")
				.createAlias("o.customer", "c")
				.add(Restrictions.eq("c.id", customerID))
				.add(Restrictions.isNull("paymentDate"))
				.setProjection(Projections.sum("installmentValue"));
		
		return (Double) criteria.uniqueResult();
	}

	@Override
	public List<OrderPayment> findAllPedingByCustomerAndDate(Integer customerID, Date date) {

		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.createAlias("order", "o")
				.createAlias("o.customer", "c")
				.add(Restrictions.eq("c.id", customerID))
				.add(Restrictions.lt("expirationDate", date))
				.add(Restrictions.isNull("paymentDate"))
				.addOrder(Order.asc("paymentDate"));

		return criteria.list();


	}

	@Override
	public List<OrderPayment> findAllPendingByOrganizationID(Integer organizationID, Integer branchID, Integer offset, Integer limit) {
		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				.add(Restrictions.isNull("paymentDate"))
				.setFirstResult(offset)
				.setMaxResults(limit)
				.addOrder(Order.asc("expirationDate"));
		return criteria.list();
	}

	@Override
	public List<OrderPayment> findPendingByOrderIDOrCustomerID(Integer organizationID, Integer branchID,String customerID, Long orderID, Integer offset, Integer limit) {
		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.createAlias("order", "o")
				.createAlias("o.customer", "c")
				.add(Restrictions.eq("organizationID", organizationID))
				.add(Restrictions.eq("branchID", branchID))
				//.add(Restrictions.isNull("paymentDate"))
				.add(Restrictions.or(
								Restrictions.eq("c.customerID", customerID), 
								Restrictions.eq("o.id", orderID)))
				
				.setFirstResult(offset)
				.setMaxResults(limit)				
				.addOrder(Order.desc("paymentDate"))
				.addOrder(Order.asc("expirationDate"));
		return criteria.list();
	}

	@Override
	public OrderPayment findByID(Long id) {
		Session session = getSession();		

		Criteria criteria = session.createCriteria(OrderPayment.class)
				.add(Restrictions.eq("id", id));
		
		return (OrderPayment) criteria.uniqueResult();
	}

	@Override
	public List<OrderPayment> findByComplexFilter(Integer organizationID, Integer branchID, String customerID, Date issuanceDateGte,
			Date issuanceDateLte, Date expirationDateGte, Date expirationDateLte,String statusPayment, Integer formPayment, Integer offset, Integer limit) {

		Session session = getSession();
		
		Criteria criteria = session.createCriteria(OrderPayment.class);
		criteria.createAlias("order", "o");
		criteria.createAlias("o.customer", "c");
		criteria.add(Restrictions.eq("organizationID", organizationID));
		criteria.add(Restrictions.eq("branchID", branchID));
		
		if( PaymentStatus.PAID.name().equals( statusPayment ) ) {
			criteria.add(Restrictions.isNotNull("paymentDate"));
		} else if( PaymentStatus.OVERDUE.name().equals( statusPayment ) ) {
			criteria.add(Restrictions.isNull("paymentDate"));
			
			/*
			 * Se for selecionado para trazer somente as parcelas vencidas, então é validade se data de vencimento informado no filtro é anterior a de hoje, 
			 * é setado a data atual, para não trazer parcelas que não venceram ainda
			 */
			if(expirationDateLte == null || expirationDateLte.after( new Date() )){
				expirationDateLte = new Date();
			}
		} else if( PaymentStatus.PENDING.name().equals( statusPayment ) ){
			criteria.add(Restrictions.isNull("paymentDate"));
			
			/*
			 * Se for selecionado para trazer somente parcelas pendetens, então é validado se a data de vencimento final, informada no filtro é postreior a de hoje, 
			 * é setado a data atual, para não trazer parcelas vencidas. 
			 */
			if(expirationDateGte == null || expirationDateGte.before(new Date())){
				expirationDateLte = new Date();
			}
			
		}
		
		if( customerID != null ) {
			criteria.add(Restrictions.eq("c.customerID", customerID)); 
		}
		
		
		if(issuanceDateGte != null && issuanceDateLte != null){
			criteria.add(Restrictions.and(Restrictions.ge("registrationDate", issuanceDateGte), Restrictions.le("registrationDate", issuanceDateLte)));
		} else if(issuanceDateGte != null) {
			criteria.add(Restrictions.ge("registrationDate", issuanceDateGte));
		} else if(issuanceDateLte != null) {
			criteria.add(Restrictions.le("registrationDate", issuanceDateLte));
		}
		
		if(expirationDateGte != null && expirationDateLte != null) {
			criteria.add(Restrictions.and(Restrictions.ge("expirationDate", expirationDateGte), Restrictions.le("expirationDate", expirationDateLte)));
		} else if(expirationDateGte != null) {
			criteria.add(Restrictions.ge("expirationDate", expirationDateGte));
		}  else if(expirationDateLte != null) {
			criteria.add(Restrictions.le("expirationDate", expirationDateLte));
		}
		
		
		
		if(formPayment != null) {
			criteria.add(Restrictions.eq("o.formPayment", formPayment)); 
		}
		
		
		criteria.setFirstResult(offset);
		criteria.setMaxResults(limit);
		criteria.addOrder(Order.asc("expirationDate"));
		
				//.add(Restrictions.isNull("paymentDate"))
/*				.add(Restrictions.or(
						Restrictions.eq("c.customerID", customerID), 
						Restrictions.eq("o.id", customerID)))

						.setFirstResult(offset)
						.setMaxResults(limit)				
						.addOrder(Order.desc("paymentDate"))
						.addOrder(Order.asc("expirationDate"));*/
		
		return criteria.list();
	}	
}
