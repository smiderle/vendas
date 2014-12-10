package br.com.vendas.services.user;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserActionDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserActionServiceImpl implements UserActionService {
	
	@Inject
	private UserActionDAO userActionDAO;

	@Transactional(readOnly=false)
	@Override
	public void save( UserAction userAction ) {
		userActionDAO.save(userAction);
	}

	@Override
	public ServiceResponse<List<UserAction>> findByUser(Integer userID, Integer offset, Integer limit) {
		List<UserAction> userActions = userActionDAO.findByUser(userID, offset, getLimit(limit));
		return ServiceResponseFactory.create(userActions);
	}
	
	private Integer getLimit(Integer limit) {
		Integer defaultLimit = LimitQuery.LIMIT_USER_ACTIONS.value();
		if(limit != null && limit < LimitQuery.LIMIT_USER_ACTIONS.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}
}