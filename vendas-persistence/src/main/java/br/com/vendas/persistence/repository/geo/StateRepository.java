package br.com.vendas.persistence.repository.geo;

import java.util.List;

import br.com.vendas.domain.state.State;

public interface StateRepository {

	/**
	 * Retorna uma lista dos estados.
	 * @return
	 */	
	public List<State> findAll();
	
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
