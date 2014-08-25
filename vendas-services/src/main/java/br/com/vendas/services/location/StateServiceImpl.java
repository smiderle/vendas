package br.com.vendas.services.location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import br.com.vendas.domain.location.State;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
public class StateServiceImpl implements StateServiceFacade{

	@Secured("ROLE_ADMIN")
	@Override
	public ServiceResponse<List<State>> findAll() {		
		State s = new State();
		s.setName("Parana");
		s.setStateID(1L);
		s.setUf("PR");
		List<State> states = new ArrayList<>();
		states.add(s);
		return ServiceResponseFactory.create(states);
	}


}
