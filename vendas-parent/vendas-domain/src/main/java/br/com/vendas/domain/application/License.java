package br.com.vendas.domain.application;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.user.User;
import br.com.vendas.enumeration.VersionType;

@Entity
@Table(name= License.TABLE_NAME )
public class License extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME = "LICENCA";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;

	@Column(name="SERIAL")
	private String serial;

	@ManyToOne
	@JoinColumn(name="idusuario")
	private User user;

	@Column(name="tipoversao")
	private VersionType versionType;

	@Column(name="dtexpiracao")
	private Date expirationDate;

	@Column(name="DTHRALTERACAO")
	private Date changeTime;

	@Column(name="DTHRCADASTRO")
	private Date registrationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VersionType getVersionType() {
		return versionType;
	}

	public void setVersionType(VersionType versionType) {
		this.versionType = versionType;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}