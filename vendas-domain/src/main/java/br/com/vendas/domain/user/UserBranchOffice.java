package br.com.vendas.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.organization.BranchOffice;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Usuario Filial.
 * Esta classe representa a empresa do usuario.
 *  
 * @author LADAIR
 *
 */

@Entity
@Table(name="USUARIO_FILIAL")
@IdClass(value=UserBranchOfficePK.class)
public class UserBranchOffice extends Domain {
	
	public UserBranchOffice() {
	
	}
	
	
	public UserBranchOffice(BranchOffice branchOffice,
			Integer userID, boolean enable) {
		super();		
		this.userID = userID;		
		this.enable = enable; 
		this.branchOffice = branchOffice;
	}

	private static final long serialVersionUID = 1327645454492832287L;

	@Id
	private BranchOffice branchOffice;	
		
	@Id
	private Integer userID;
	

	@Column(name="ABILITADO")
	private boolean enable;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IDUSUARIO", insertable=false, updatable=false)
	@JsonIgnore
	private User user;

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}


	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}


	public Integer getUserID() {
		return userID;
	}


	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}
