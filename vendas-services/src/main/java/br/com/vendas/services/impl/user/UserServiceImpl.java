package br.com.vendas.services.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.user.User;
import br.com.vendas.repository.facade.user.UserRepositoryFacade;
import br.com.vendas.repository.support.ServiceResponse;
import br.com.vendas.repository.support.ServiceResponseFactory;
import br.com.vendas.services.facade.user.UserServiceFacade;

@Service
public class UserServiceImpl implements UserServiceFacade{

	@Autowired
	private UserRepositoryFacade userRepository;
	
	@Override
	public ServiceResponse<List<User>> findByBranchOffice(BranchOffice branchOffice) {
		List<User> users = userRepository.findByBranchOffice(branchOffice);
		return ServiceResponseFactory.create(users);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public ServiceResponse<User> findByEmail(String email) {
		return ServiceResponseFactory.create(userRepository.findByEmail(email));
	}
}
