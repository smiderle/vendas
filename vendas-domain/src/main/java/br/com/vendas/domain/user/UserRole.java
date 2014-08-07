package br.com.vendas.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USUARIO_PERMISSAO")
public class UserRole implements Serializable{
	
	
	private static final long serialVersionUID = 1L;


	public UserRole() {}
	
	public UserRole(Long userID, Role role) {
		super();
		this.userID = userID;
		this.role = role;
	}
	@Id
	@Column(name="IDUSUARIO")
	private Long userID;
	
	
	@Id
	@Enumerated(EnumType.STRING)
	@Column(name="PERMISSAO")
	private Role role;


	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}	
}
