package br.com.vendas.services.user;

import java.util.List;

import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserRole;
import br.com.vendas.services.support.ServiceResponse;

public interface UserRoleService {
	
	ServiceResponse<List<UserRole>> save(List<UserRole> usersRole);
	ServiceResponse<UserRole> save(UserRole userRole);
	/**
	 * Seta as permissões default do usuario
	 * ADMIN e USER
	 * @param user
	 * @return
	 */
	ServiceResponse<List<UserRole>> saveDefaultRoles(User user);
	
	void deleteByUser(User user);

}
