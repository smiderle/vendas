package br.com.vendas.domain.user;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.application.MenuApplication;

@Entity
@Table(name="USUARIO", uniqueConstraints={		
		@UniqueConstraint(columnNames={"EMAIL"}, name="UK_USUARIO_EMAIL")})

public class User extends Domain{

	private static final long serialVersionUID = 1L;
	
	public User() {		
	}
	
	public User(Long organizationID, String email, String password,
			String name, boolean active, boolean portalAccess) {
		super();
		this.organizationID = organizationID;
		this.email = email;
		this.password = password;
		this.name = name;
		this.active = active;
		this.portalAccess = portalAccess;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IDUSUARIO")
	private Long userID;
	
	@Column(name="IDEMPRESA")
	private Long organizationID;
	
	@NaturalId
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="SENHA")
	private String password;
	
	@Column(name="NOME", length=50)
	private String name;		
	
	@Column(name="DTHRALTERACAO")
	private Calendar changeTime;
	
	
	@Column(name="DTHRCADASTRO", insertable=false)
	private Date registrationDate;
	
	
	@Column(name="ATIVO")
	private boolean active;
	
	/**
	 * Define se o usuario tem acesso ao portal.
	 */
	@Column(name="ACESSO_PORTAL")
	private boolean portalAccess;
	
	@OrderBy("order")
	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
    @JoinTable(name="usuario_menu", joinColumns={@JoinColumn(name="idusuario")}
                                        , inverseJoinColumns={@JoinColumn(name="idmenu")})  
	private Set<MenuApplication> menusApplication = new LinkedHashSet<>(0);
	
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Calendar changeTime) {
		this.changeTime = changeTime;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isPortalAccess() {
		return portalAccess;
	}

	public void setPortalAccess(boolean portalAccess) {
		this.portalAccess = portalAccess;
	}

	
	public Set<MenuApplication> getMenusApplication() {
		return menusApplication;
	}

	public void setMenusApplication(Set<MenuApplication> menusApplication) {
		this.menusApplication = menusApplication;
	}	
	
	
}
