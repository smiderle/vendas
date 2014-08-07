package br.com.vendas.dao.user;

import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserRole;

@Repository
public class UserRoleDAOImpl extends ResourceDAO<UserRole> implements UserRoleDAO{

}
