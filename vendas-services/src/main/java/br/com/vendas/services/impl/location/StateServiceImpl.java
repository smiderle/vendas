package br.com.vendas.services.impl.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.location.State;
import br.com.vendas.repository.location.StateRepositoryFacade;
import br.com.vendas.repository.support.ServiceResponse;
import br.com.vendas.services.facade.location.StateServiceFacade;

@Service
public class StateServiceImpl implements StateServiceFacade{

	@Autowired
	private StateRepositoryFacade stateRepository;
	
	@Override
	public ServiceResponse<List<State>> findAll() {
		return stateRepository.findAll();
	}
}
