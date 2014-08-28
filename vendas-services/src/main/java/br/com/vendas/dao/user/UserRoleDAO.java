package br.com.vendas.dao.user;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.user.UserRole;

public interface UserRoleDAO  extends GenericDAO<UserRole> {
	
	void deleteByUserID(Long userID);

}
