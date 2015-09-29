package br.com.vendas.domain.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;

@Table(name="CIDADE")
@Entity
public class City extends Domain{
	

	/**
	 * Código interno da cidade.
	 */
	@Id
	@Column(name="IDCIDADE")
	private Integer cityID;
	
	/**
	 * Nome da cidade.
	 */
	@Column(name="NOME")
	private String name;
	
	/**
	 * Estado que a cidade pertence.
	 */
	@ManyToOne
	@JoinColumn(name="idestado")
	private State state;
	
	@Column(name="codigoibge")
	private Integer ibgeCode;

	
	public Integer getCityID() {
		return cityID;
	}

	public void setCityID(Integer cityID) {
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
