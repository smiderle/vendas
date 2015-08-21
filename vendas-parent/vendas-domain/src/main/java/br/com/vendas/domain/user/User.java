package br.com.vendas.domain.user;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.location.City;

@Entity
@Table(name="USUARIO", uniqueConstraints={
		@UniqueConstraint(columnNames={"EMAIL"}, name="UK_USUARIO_EMAIL")})

public class User extends Domain{

	private static final long serialVersionUID = 1L;

	public User() {
	}

	public User(Integer organizationID, String email, String password,
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
	private Integer userID;

	@Column(name="IDEMPRESA")
	private Integer organizationID;

	@NaturalId
	@Column(name="EMAIL")
	private String email;

	@Column(name="SENHA")
	private String password;

	@Column(name="NOME", length=50)
	private String name;

	/**
	 * Data e hora de alteração
	 */
	@Column(name="DTHRALTERACAO")
	private Date changeTime;


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
	@JoinTable(name="usuario_menu", joinColumns={@JoinColumn(name="idusuario")}	, inverseJoinColumns={@JoinColumn(name="idmenu")})
	private Set<MenuApplication> menusApplication = new LinkedHashSet<>(0);

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<UserBranchOffice> userBranches;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private Set<UserRole> userRoles;

	@Column(name="dtnascimento")
	private Date birthDate;

	@Column(name="fone")
	private String phoneNumber;

	@Column(name="celular")
	private String cellPhone;

	@Column(name="urlfoto")
	private String pictureUrl;

	@Column(name="facebook")
	private String linkFacebook;

	@Column(name="googleplus")
	private String linkGooglePlus;

	@Column(name="skype")
	private String skype;

	@Column(name="rua")
	private String street;

	@Column(name="bairro")
	private String district;

	/**
	 * Numero
	 */
	@Column(name="NUMERO")
	private String number;


	@Column(name="cep")
	private String postalCode;

	@ManyToOne
	@JoinColumn(name="CIDADE")
	private City city;

	@Column(name="CPFCNPJ")
	private String cpfCnpj;

	/**
	 * RG ou Inscrição Estadual
	 */
	@Column(name="inscriestad")
	private String inscricao;

	@Column(name="contato")
	private String contactName;

	@Column(name="observacao")
	private String observation;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
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

	public List<UserBranchOffice> getUserBranches() {
		return userBranches;
	}

	public void setUserBranches(List<UserBranchOffice> userBranches) {
		this.userBranches = userBranches;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getLinkFacebook() {
		return linkFacebook;
	}

	public void setLinkFacebook(String linkFacebook) {
		this.linkFacebook = linkFacebook;
	}

	public String getLinkGooglePlus() {
		return linkGooglePlus;
	}

	public void setLinkGooglePlus(String linkGooglePlus) {
		this.linkGooglePlus = linkGooglePlus;
	}



	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}




	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (userID == null) {
			if (other.userID != null) {
				return false;
			}
		} else if (!userID.equals(other.userID)) {
			return false;
		}
		return true;
	}





}
