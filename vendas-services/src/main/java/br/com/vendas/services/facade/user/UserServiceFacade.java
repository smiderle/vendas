package br.com.vendas.services.facade.user;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.user.User;
import br.com.vendas.repository.support.ServiceResponse;

public interface UserServiceFacade {
	
	/**
	 * retorna todos os usuario por Filial;
	 * @return
	 */
	ServiceResponse<List<User>> findByBranchOffice(BranchOffice branchOffice);
	
	/**
	 * Salva o usuario
	 */
	void save(User user);
	
	/**
	 * Busca o usuario pelo email
	 * @param email
	 * @return
	 */
	ServiceResponse<User> findByEmail(String email);
}
