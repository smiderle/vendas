package br.com.vendas.domain.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;
/**
 * Empresa
 * @author LADAIR
 *
 */

@Entity
@Table(name="empresa")
public class Organization extends Domain {
		
	private static final long serialVersionUID = -1759848649572554649L;
	
	
	
	public Organization(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idempresa")
	private Long organizationID;
	
	@Column(name="nome")
	private String name;

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
