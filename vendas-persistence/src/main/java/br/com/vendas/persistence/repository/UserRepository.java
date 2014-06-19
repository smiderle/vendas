package br.com.vendas.persistence.repository;

import java.util.List;

import br.com.vendas.domain.user.User;

public interface UserRepository {

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
