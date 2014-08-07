package br.com.vendas.domain.location;

import br.com.vendas.domain.Domain;

public class City extends Domain{
	

	/**
	 * Código interno da cidade.
	 */
	private Long cityID;
	
	/**
	 * Nome da cidade.
	 */
	private String name;
	
	/**
	 * Estado que a cidade pertence.
	 */
	
	private State state;

	
	public Long getCityID() {
		return cityID;
	}

	public void setCityID(Long cityID) {
		this.cityID = cityID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}	
}
