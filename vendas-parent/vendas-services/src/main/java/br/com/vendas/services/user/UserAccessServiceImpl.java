package br.com.vendas.services.user;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.domain.user.UserAction;
import br.com.vendas.helper.UserActionHelper;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserAccessServiceImpl implements UserAccessService{
		
	@Inject
	private UserActionService userActionService;

	@Transactional(readOnly=false)
	@Override
	public void save(Integer userID) {
		UserAction userAction = UserActionHelper.createUserAccess(userID);
		userActionService.save(userAction);
	}
}
