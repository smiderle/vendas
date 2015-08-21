package br.com.vendas.services.user;

import java.util.List;

import br.com.vendas.domain.user.UserAction;
import br.com.vendas.services.support.ServiceResponse;

public interface UserActionService {
	
	 void save( UserAction userAction );
	 
	 ServiceResponse<List<UserAction>> findByUser(Integer userID, Integer offset, Integer limit);

}