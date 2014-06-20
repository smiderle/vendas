package br.com.vendas.services.facade.geo;

import java.util.List;

import br.com.vendas.domain.state.State;

public interface StateServiceFacade {
	
	public List<State> findAll();

}
