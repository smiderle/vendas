package br.com.vendas.services.user;

import br.com.vendas.domain.user.UserProfile;
import br.com.vendas.services.support.ServiceResponse;

public interface UserProfileService {
	
	ServiceResponse<UserProfile> save(UserProfile userProfile);

}
