package br.com.vendas.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import br.com.vendas.domain.organization.BranchOffice;

public class UserBranchOfficePK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -550870406575279282L;

	
	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumns(
			{
				@JoinColumn(name="IDEMPRESA",referencedColumnName="IDEMPRESA"),
				@JoinColumn(name="IDFILIAL", referencedColumnName="IDFILIAL")
			}
			)
	private BranchOffice branchOffice;
	
	
	@Column(name="IDUSUARIO")
	private Integer userID;

	
	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}
	
	
}
