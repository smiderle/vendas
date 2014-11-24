package br.com.vendas.dao.user;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.user.User;

public interface UserDAO extends GenericDAO<User> {
	
	/**
	 * Retorna todos os usuarios por empresa.
	 * @param organizationID
	 * @return
	 */
	List<User> findAllByOrganizationID(Integer organizationID, Integer offset, Integer limit);
	
	/**
	 * Retorna o usuario por email.
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
	
	
	/**
	 * Retorna todos os que iniciem com o nmoe, email ou pelo código passado no filter.
	 * @param filter
	 * @param organizationID
	 * @return
	 */
	List<User> findUsersByUserIDOrNameOrEmail(String filter, Integer organizationID, Integer userID, Integer offset, Integer limit);
	
	/**
	 * Retorna todos os usuários, com exceção do usuário passado por parametro
	 * @param organizationID
	 * @param userID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<User> findOtherUsersByOrganizationID(Integer organizationID,Integer userID, Integer offset, Integer limit);		

	
}
