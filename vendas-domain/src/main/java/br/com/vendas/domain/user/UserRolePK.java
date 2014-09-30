package br.com.vendas.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserRolePK implements Serializable{
	
	
	@ManyToOne
	@JoinColumn(name="IDUSUARIO", insertable=false, updatable=false)
	@JsonIgnore
	private User user;
	
		
	@Enumerated(EnumType.STRING)
	@Column(name="PERMISSAO")
	private Role role;


	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
