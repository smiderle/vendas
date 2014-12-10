package br.com.vendas.dao.targets;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.target.Goal;
import br.com.vendas.pojo.UserTargetsDTO;
import br.com.vendas.services.support.ServiceResponse;

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
	Goal findGoalByUserAndMonth( Integer userID, Integer yearMonth );

}
