package br.com.vendas.services.user;


public interface UserAccessService {
	
	/**
	 * Salva a hora de acesso do usuario no sistema
	 * @param userAcess
	 */
	public void save(Long userID);

}
