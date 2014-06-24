package br.com.vendas.repository.facade.user;

import java.util.List;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.user.User;

public interface UserRepositoryFacade {

	/**
	 * Retorna uma lista dos usuarios.
	 * @return List<User>
	 */	
	List<User> findByBranchOffice(BranchOffice branchOffice);
	
	/**
	 * Busca o usuario pelo email. 	
	 * @return User
	 */
	User findByEmail(String email);
	
	/**
	 * Busca o usuario pelo email e senha
	 * @return User
	 */
	User findByEmailAndPassword(String email, String password);
	
	/**
	 * Salva o usuario.
	 * @param user
	 */
	public void save(User user);
}
