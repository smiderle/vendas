package br.com.vendas.dao.targets;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.target.Goal;

@Repository
public class GoalDAOImpl  extends ResourceDAO<Goal> implements GoalDAO {

	@Override
	public List<Goal> findAllGreaterThen(Integer organizationID, Integer branchID, Integer userID, Integer yearMonth, Integer months ) {
		
		Session session = getSession();		
		
		Criteria criteria = session.createCriteria(Goal.class)
				.add(Restrictions.eq( "organizationID", organizationID ))
				.add(Restrictions.eq( "branchID", branchID ))
				.add(Restrictions.eq( "userID", userID ))
				.add(Restrictions.ge( "yearMonth", yearMonth ))
				.setMaxResults(months)
				.addOrder(Order.asc("userID"))
				.addOrder(Order.asc("yearMonth"));
		return criteria.list();	
	}

	@Override
	public Goal findGoalByUserAndMonth(Integer organizationID, Integer branchID, Integer userID, Integer monthYear ) {

		Session session = getSession();		

		Criteria criteria = session.createCriteria( Goal.class )
				.add(Restrictions.eq( "organizationID", organizationID ))
				.add(Restrictions.eq( "branchID", branchID ))
				.add(Restrictions.eq( "userID", userID ))
				.add(Restrictions.eq( "yearMonth", monthYear ));
		return (Goal) criteria.uniqueResult();		
	}	
}