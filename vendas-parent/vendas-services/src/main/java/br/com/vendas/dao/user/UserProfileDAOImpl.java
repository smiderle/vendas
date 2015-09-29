package br.com.vendas.dao.user;

import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserProfile;

@Repository
public class UserProfileDAOImpl extends ResourceDAO<UserProfile>  implements UserProfileDAO{

}
