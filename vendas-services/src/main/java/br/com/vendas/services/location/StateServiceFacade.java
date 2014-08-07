package br.com.vendas.services.location;

import java.util.List;

import br.com.vendas.domain.location.State;
import br.com.vendas.services.support.ServiceResponse;

public interface StateServiceFacade {
	
	public ServiceResponse<List<State>> findAll();

}
