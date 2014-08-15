package br.com.vendas.dao.user;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.user.UserBranchOffice;

public interface UserBranchOfficeDAO  extends GenericDAO<UserBranchOffice>{
	
	/**
	 * Busca todos os usuarios de determinada filial
	 * @return List<UserBranchOffice>
	 */
	List<UserBranchOffice> findAllByBranchOffice(Integer organizationID, Integer branchOfficeID);
	
	/**
	 * Retorna as filiais do usuário	
	 * @param userBranchOffice
	 * @return
	 */
	List<UserBranchOffice> findAllByUserID(Long userID);
	

}
