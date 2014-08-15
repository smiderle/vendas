package br.com.vendas.domain.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="USUARIO_ACESSO")
@Entity
public class UserAccess implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3701354748069249056L;

	@Id	
	@Column(name="IDUSUARIO")	
	private Long userID;
	
	@Id
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTHRACESSO")
	private Date accessTime;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
}
