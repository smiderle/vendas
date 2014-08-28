package br.com.vendas.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

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
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IDUSUARIO", insertable=false, updatable=false)
	@JsonIgnore
	private User user;


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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	
}
