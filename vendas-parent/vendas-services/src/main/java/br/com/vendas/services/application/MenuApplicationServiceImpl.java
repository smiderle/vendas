package br.com.vendas.services.application;

import java.util.List;

import javax.inject.Inject;

import br.com.vendas.dao.application.MenuApplicationDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class MenuApplicationServiceImpl implements MenuApplicationService {

	@Inject
	private MenuApplicationDAO menuApplicationDAO;
	@Transactional(readOnly=false)	
	@Override
	public ServiceResponse<MenuApplication> save(MenuApplication menuApplication) {
		return ServiceResponseFactory.create(menuApplicationDAO.save(menuApplication));
		
	}
	@Override
	public ServiceResponse<List<MenuApplication>> findAll() {
		return ServiceResponseFactory.create(menuApplicationDAO.findAll());
	}

}
