package br.com.vendas.domain.location;

import br.com.vendas.domain.Domain;

public class State extends Domain{
	
	/**
	 * Código interno do estado.
	 */
	private Long stateID;	
	/**
	 * UF do estado.
	 */
	private String uf;	
	/**
	 * Nome do estado.
	 */
	private String name;
		
	public Long getStateID() {
		return stateID;
	}
	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	
}
