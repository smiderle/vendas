package br.com.vendas.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;

@Table(name="ESTADO")
@Entity
public class State extends Domain{
	
	/**
	 * Código interno do estado.
	 */
	@Id
	@Column(name="IDESTADO")
	private Long stateID;	
	/**
	 * UF do estado.
	 */
	@Column
	private String uf;	
	/**
	 * Nome do estado.
	 */
	@Column(name="NOME")
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
