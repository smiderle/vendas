package br.com.vendas.services.user;

import java.util.List;

import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.services.support.ServiceResponse;

public interface UserBranchOfficeService {
	
	
	/**
	 * Busca todos os usuarios de determinada filial
	 * @return List<UserBranchOffice>
	 */
	ServiceResponse<List<UserBranchOffice>> findAllByBranchOffice(Integer organizationID, Integer branchOfficeID);
	
	
	/**
	 * Salva o usuario-filial
	 * @return
	 */
	ServiceResponse<UserBranchOffice> save(UserBranchOffice userBranchOffice);
}
