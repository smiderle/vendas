package br.com.vendas.domain.organization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class BranchOfficePK implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="idempresa")
	private Organization organization;
	
	@Column(name="idfilial")
	private Long branchOfficeID;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Long getBranchOfficeID() {
		return branchOfficeID;
	}

	public void setBranchOfficeID(Long branchOfficeID) {
		this.branchOfficeID = branchOfficeID;
	}

	
}
