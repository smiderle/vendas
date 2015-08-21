package br.com.vendas.dao.application;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.application.MenuApplication;

public interface MenuApplicationDAO extends GenericDAO<MenuApplication>{
	
	
	List<MenuApplication> findAll();

}
