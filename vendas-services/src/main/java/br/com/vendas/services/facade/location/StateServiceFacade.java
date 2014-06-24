package br.com.vendas.services.facade.location;

import java.util.List;

import br.com.vendas.domain.location.State;
import br.com.vendas.repository.support.ServiceResponse;

public interface StateServiceFacade {
	
	public ServiceResponse<List<State>> findAll();

}
