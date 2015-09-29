package br.com.vendas.dao.location;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.vendas.domain.location.State;
import br.com.vendas.services.support.ServiceResponse;

@Repository
public class StateDAOImpl implements StateDAO{

	@Override
	public ServiceResponse<List<State>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(State state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(List<State> states) {
		// TODO Auto-generated method stub
		
	}
	
	

}
