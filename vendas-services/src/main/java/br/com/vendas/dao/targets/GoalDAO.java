package br.com.vendas.dao.targets;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.target.Goal;

public interface GoalDAO extends GenericDAO<Goal> {

	/**
	 * Retorna as metas cadastradas a partir do periodo informado
	 * @param organizationID
	 * @param branchID
	 * @param yearMonth
	 * @return
	 */
	List<Goal> findAllGreaterThen( Integer organizationID, Integer branchID, Integer userID , Integer yearMonth, Integer months);

	/**
	 * Retorna a meta estimada de determinado usuário e mes e ano
	 * @param userID
	 * @param yearMonth
	 * @return
	 */
	Goal findGoalByUserAndMonth(Integer organizationID, Integer branchID, Integer userID, Integer yearMonth );


	/**
	 * Retorna todos os registros com data de alteração maior que a data passada por parametro.
	 * @param date
	 * @return
	 */
	List<Goal> findAllByChangeGreaterThan( Date date,Integer organizationID, Integer offset, Integer limit );

}
