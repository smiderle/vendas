package br.com.vendas.services.application;

import java.util.List;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.services.support.ServiceResponse;

public interface MenuApplicationService {

	

	ServiceResponse<MenuApplication> save (MenuApplication menuApplication);
	
	ServiceResponse<List<MenuApplication>> findAll();
}
