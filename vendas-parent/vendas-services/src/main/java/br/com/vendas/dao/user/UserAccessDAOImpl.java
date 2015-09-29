package br.com.vendas.dao.user;

import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.user.UserAccess;

@Repository
public class UserAccessDAOImpl  extends ResourceDAO<UserAccess> implements UserAccessDAO{

}