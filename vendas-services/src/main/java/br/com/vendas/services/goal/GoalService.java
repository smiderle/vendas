package br.com.vendas.services.goal;

import java.util.List;

import br.com.vendas.domain.target.Goal;
import br.com.vendas.pojo.UserTargetsDTO;
import br.com.vendas.services.support.ServiceResponse;

public interface GoalService {
	
	ServiceResponse<List<UserTargetsDTO>> saveUserTargets(Integer userID, List<UserTargetsDTO> targets );
	
	
	/**
	 * Retorna as metas cadastradas para o mes atual e os próximos 11 meses
	 * @param targets
	 * @return
	 */
	ServiceResponse<List<UserTargetsDTO>> findNextTargets( Integer organizationID, Integer branchID );
	
	/**
	 * Retorna a meta estimada de determinado usuário e mes e ano 
	 * @param userID
	 * @param yearMonth
	 * @return
	 */
	ServiceResponse<Goal> findTargetsByUserAndMonth( Integer organizationID, Integer branchID, Integer userID, Integer yearMonth );

}
