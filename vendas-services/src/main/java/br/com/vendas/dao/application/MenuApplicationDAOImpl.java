package br.com.vendas.dao.application;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import br.com.vendas.dao.ResourceDAO;
import br.com.vendas.domain.application.MenuApplication;

@Repository
public class MenuApplicationDAOImpl extends ResourceDAO<MenuApplication> implements MenuApplicationDAO{

	@Override
	public List<MenuApplication> findAll() {
		Session session = getSession();
		return session.createCriteria(MenuApplication.class).list();
	}

}
