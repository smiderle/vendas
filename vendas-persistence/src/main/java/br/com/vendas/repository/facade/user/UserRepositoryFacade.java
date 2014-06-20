package br.com.vendas.repository.facade.user;

import java.util.List;

import br.com.vendas.domain.user.User;

public interface UserRepositoryFacade {

	/**
	 * Retorna uma lista dos usuarios.
	 * @return
	 */	
	public List<User> findAll();
	
	/**
	 * Salva o usuario.
	 * @param user
	 */
	public void save(User user);
}
