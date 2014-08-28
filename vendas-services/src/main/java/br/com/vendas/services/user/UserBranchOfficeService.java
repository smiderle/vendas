package br.com.vendas.services.user;

import java.util.List;

import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.services.support.ServiceResponse;

public interface UserBranchOfficeService {
	
	
	/**
	 * Busca todos os usuarios de determinada filial
	 * @return List<UserBranchOffice>
	 */
	ServiceResponse<List<UserBranchOffice>> findAllByBranch(Integer organizationID, Integer branchOfficeID);
	
	
	/**
	 * Salva o usuario-filial
	 * @return
	 */
	ServiceResponse<UserBranchOffice> save(UserBranchOffice userBranchOffice);
	
	/**
	 * Retorna todas as empresas do usuario.
	 * @param organizationID
	 * @param branchOfficeID
	 * @param userID
	 * @return
	 */
	ServiceResponse<List<UserBranchOffice>> findAllByUserID(Long userID);
	
	/**
	 * Salva, ou atualiza uma lista de usuarios filiais
	 * @param usersBranch
	 * @return
	 */
	ServiceResponse<List<UserBranchOffice>> saveOrUpdate(List<UserBranchOffice> usersBranch);
}
