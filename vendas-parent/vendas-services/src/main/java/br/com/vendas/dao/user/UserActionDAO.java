package br.com.vendas.dao.user;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.user.UserAction;

public interface UserActionDAO extends GenericDAO<UserAction>{

	/**
	 * Retorna uma lista de ações do usuário
	 * @param userID
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<UserAction> findByUser(Integer userID, Integer offset, Integer limit );
}
