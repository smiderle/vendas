package br.com.vendas.services.impl.geo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.state.State;
import br.com.vendas.repository.facade.geo.StateRepositoryFacade;
import br.com.vendas.services.facade.geo.StateServiceFacade;

@Service
public class StateServiceImpl implements StateServiceFacade{

	@Autowired
	private StateRepositoryFacade stateRepository;
	
	@Override
	public List<State> findAll() {
		return stateRepository.findAll();
	}
}
