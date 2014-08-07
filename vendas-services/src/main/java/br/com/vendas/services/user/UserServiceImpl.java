package br.com.vendas.services.user;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserDAO;
import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.user.User;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserServiceImpl implements UserService{
	
	private static final Integer LIMIT_USER = 100;

	@Inject
	private UserDAO userDAO;


	@Override
	public ServiceResponse<List<User>> findAllByOrganizationID(Long organizationID, Integer offset) {
		List<User> users = userDAO.findAllByOrganizationID(organizationID, offset, LIMIT_USER);
		return ServiceResponseFactory.create(users);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<User> save(User user) {
		user.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(userDAO.save(user));
	}

	@Override
	public ServiceResponse<User> saveOrUpdate(User user) {
		user.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(userDAO.saveOrUpdate(user));		
	}

	@Override
	public ServiceResponse<User> findUserByEmail(String email) {
		User user = userDAO.findUserByEmail(email);
		return ServiceResponseFactory.create(user);
	}

	@Override
	public ServiceResponse<Boolean> isAvailableEmail(String email) {
		return ServiceResponseFactory.create(userDAO.findUserByEmail(email) == null);
	}

	/**
	 * Converte o filtro para um long para setar no usuarioid, pois pode ser que no filtro tenha sido digitado o código do usuario.
	 */
	@Override
	public ServiceResponse<List<User>> findUsersByUserIDOrNameOrEmail(
			String filter, Long organizationID, Integer offset) {
		Long userID = 0L;
		try{
			userID = Long.parseLong(filter);
		} catch(NumberFormatException  e){}

		List<User> users = userDAO.findUsersByUserIDOrNameOrEmail(filter, organizationID,userID, offset, LIMIT_USER);
		return ServiceResponseFactory.create(users);
	}

}
