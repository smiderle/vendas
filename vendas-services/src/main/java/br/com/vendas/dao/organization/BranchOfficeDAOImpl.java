package br.com.vendas.dao.organization;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.domain.product.Product;

@Repository
public class BranchOfficeDAOImpl extends ResourceDAO<BranchOffice> implements BranchOfficeDAO{

	@Override
	public List<BranchOffice> findAllByOrganizationID(Integer organizationID) {
		Criterion criterion = Restrictions.eq("organization.organizationID", organizationID);
		return findByCriteria(Order.asc("fancyName"), criterion);
	}

	@Override
	public BranchOffice findByOrganizationIDAndBranchOfficeID(
			Integer organizationID, Integer branchOfficeID) {
		Session session = getSession();

		Criterion criterion = Restrictions.and(
				Restrictions.eq("organization.organizationID", organizationID), 
				Restrictions.eq("branchOfficeID", branchOfficeID));

		return (BranchOffice) session.createCriteria(BranchOffice.class)
				.add(criterion).uniqueResult();

	}

	@Override
	public Long findMaxBranchOfficeIDByOrganization(Organization organization) {
		Session session = getSession();
		Criterion criterion = Restrictions.eq("organization", organization);
		AggregateProjection projection = Projections.max("branchOfficeID"); 
		Long maxID = (Long) session.createCriteria(BranchOffice.class)
				.add(criterion)
				.setProjection(projection)
				.uniqueResult();
		return maxID != null ? maxID : 0;
	}
	

	@Override
	public List<BranchOffice> findAllByChangeGreaterThan( Date date, Integer organizationID, Integer offset, Integer limit ) {
		
		Session session = getSession();
		Criteria criteria = session.createCriteria(BranchOffice.class)				
				.add(Restrictions.eq("organization.organizationID", organizationID))
				.add(Restrictions.ge("changeTime", date))
				.setFirstResult(offset)
				.setMaxResults(limit)		
				.addOrder(Order.asc("changeTime"));
		
		return criteria.list();
	}

}
