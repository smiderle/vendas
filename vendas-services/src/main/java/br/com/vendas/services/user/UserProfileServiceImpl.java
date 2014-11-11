package br.com.vendas.services.user;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserProfileDAO;
import br.com.vendas.domain.user.UserProfile;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserProfileServiceImpl implements UserProfileService{
	
	@Inject
	private UserProfileDAO dao;

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<UserProfile> save(UserProfile userProfile) {
		return ServiceResponseFactory.create(dao.save(userProfile));
	}
}
