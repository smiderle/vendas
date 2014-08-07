package br.com.vendas.dao.location;

import java.util.List;

import br.com.vendas.domain.location.State;
import br.com.vendas.services.support.ServiceResponse;

public interface StateDAO {

	/**
	 * Retorna uma lista dos estados.
	 * @return
	 */	
	public ServiceResponse<List<State>> findAll();
	
	/**
	 * Salva o estado.
	 * @param state
	 */
	public void save(State state);
	
	/**
	 * Salva uma lista de estados;
	 * @param states
	 */
	public void save(List<State> states);
}
