package br.com.vendas.domain.user;

import java.io.Serializable;

import javax.persistence.Column;

public class UserBranchOfficePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -550870406575279282L;

	@Column(name="IDEMPRESA")
	private Long organizationID;
	
	@Column(name="IDFILIAL")
	private Long branchOfficeID;
	
	@Column(name="IDUSUARIO")
	private Long userID;

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	public Long getBranchOfficeID() {
		return branchOfficeID;
	}

	public void setBranchOfficeID(Long branchOfficeID) {
		this.branchOfficeID = branchOfficeID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}
}
