package br.com.vendas.services.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserRoleDAO;
import br.com.vendas.domain.user.Role;
import br.com.vendas.domain.user.User;
import br.com.vendas.domain.user.UserRole;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserRoleServiceImpl implements UserRoleService{

	@Inject
	private UserRoleDAO dao;
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<List<UserRole>> save(List<UserRole> usersRole) {
		return ServiceResponseFactory.create(dao.save(usersRole));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<UserRole> save(UserRole userRole) {
		return ServiceResponseFactory.create(dao.save(userRole));
	}
	
	
	@Transactional(readOnly=false)
	@Override
	public  ServiceResponse<List<UserRole>> saveDefaultRoles(User user) {
		List<UserRole> userRoles= new ArrayList<>();
		userRoles.add(new UserRole(user.getUserID(), Role.ROLE_USER));
		//userRoles.add(new UserRole(user.getUserID(), Role.ROLE_ADMIN));
		return save(userRoles);		
	}

	@Override
	public void deleteByUser(User user) {
		if(user != null && user.getUserID() != null)		{
			dao.deleteByUserID(user.getUserID());
		}
	}

}
