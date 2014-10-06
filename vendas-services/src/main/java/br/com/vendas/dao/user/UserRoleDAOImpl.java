package br.com.vendas.dao.user;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserRole;

@Repository
public class UserRoleDAOImpl extends ResourceDAO<UserRole> implements UserRoleDAO{

	@Override
	public void deleteByUserID(Integer userID) {
		Session session = getSession();		
		String hql = "delete from UserRole where userID= :userID";
		session.createQuery(hql).setInteger("userID", userID).executeUpdate();	
		
	}

}
