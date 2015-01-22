package br.com.vendas.services.user;

import java.util.List;

import br.com.vendas.domain.user.User;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.pojo.UserDTO;
import br.com.vendas.services.support.ServiceResponse;

public interface UserService {
	
	/**
	 * Retorna todos os usuario de determinada empresa
	 * @param organizationID
	 * @return
	 */
	ServiceResponse<List<UserDTO>> findAllByOrganizationID(Integer organizationID, Integer offset);
	
	
	/**
	 * Retorna todos os usuários, com exceção do passado por parametro
	 * @param organizationID
	 * @param userID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<UserDTO>> findOtherUsersByOrganizationID(Integer organizationID,Integer userID, Integer offset);
	
	/**
	 * Salva o usuario
	 * @param user
	 * @return
	 */
	ServiceResponse<User> save(User user);
	
	/**
	 * Salva ou Atualiza o usuario
	 * @param user
	 * @return
	 * @throws RegistrationException 
	 */
	ServiceResponse<User> saveOrUpdate(Integer fromUserID, User user) throws RegistrationException;
	
	

	/**
	 * Retorna o usuario por email.
	 * @param email
	 * @return
	 */
	ServiceResponse<UserDTO> findUserByEmail(String email);
	
	
	/**
	 * Retorna true se o email estiver disponivel.
	 * @param email
	 * @return
	 */
	ServiceResponse<Boolean> isAvailableEmail(String email);
	
	

	/**
	 * Retorna todos os que iniciem com o nmoe, email ou pelo código passado no filter.
	 * @param filter
	 * @param organizationID
	 * @param offset
	 * @param limit
	 * @return
	 */
	ServiceResponse<List<UserDTO>> findUsersByUserIDOrNameOrEmail(String filter,Integer organizationID, Integer offset);

	/**
	 * Retorna o usuário com determinado ID
	 * @return
	 */
	ServiceResponse<UserDTO> findUserByID( Integer userID );

}
