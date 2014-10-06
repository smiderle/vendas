package br.com.vendas.dao.user;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserBranchOffice;

@Repository
public class UserBranchOfficeDAOImpl  extends ResourceDAO<UserBranchOffice> implements UserBranchOfficeDAO{

	@Override
	public List<UserBranchOffice> findAllByBranchOffice(Integer organizationID,
			Integer branchOfficeID) {
		Criterion criterion = Restrictions.and(
				Restrictions.eq("organizationID", organizationID),
				Restrictions.eq("branchOfficeID", branchOfficeID));
		return findByCriteria(criterion);
	}

	@Override
	public List<UserBranchOffice> findAllByUserID(Integer userID) {

		Criterion criterion = Restrictions.eq("userID", userID);
				
		return findByCriteria(criterion);
	}
}
