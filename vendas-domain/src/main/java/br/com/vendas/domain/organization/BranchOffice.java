package br.com.vendas.domain.organization;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;

/**
 * Filial
 * @author LADAIR
 *
 */

@Entity
@Table(name="filial")
@IdClass(BranchOfficePK.class)
public class BranchOffice extends Domain{
	
	public BranchOffice() {	
	}
	
	public BranchOffice(Long branchOfficeID, Organization organization,
			String realName, String fancyName) {
		super();
		this.branchOfficeID = branchOfficeID;
		this.organization = organization;
		this.realName = realName;
		this.fancyName = fancyName;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private Long branchOfficeID;
	
	/**
	 * Empresa
	 */
	@Id
	private Organization organization;
	
		
	/**
	 * Razão Social
	 */
	@Column(name="razaosocial")
	private String realName;
	
	/**
	 * Nome Fantasia
	 */
	@Column(name="nomefantasia")
	private String fancyName;
	
	/**
	 * Telefone
	 */
	@Column(name="fone")
	private String phoneNumber;
	
	/**
	 * Fax
	 */	
	@Column(name="fax")
	private String faxNumber;
	
	/**
	 * Rua
	 */
	@Column(name="rua")
	private String street;
	
	/**
	 * Bairro
	 */
	@Column(name="bairro")
	private String district;

	/**
	 * Numero
	 */
	@Column(name="numero")
	private String number;
	
	/**
	 * Cep
	 */
	@Column(name="cep")
	private String postalCode;
	
	/**
	 * Data e hora de alteração
	 */
	@Column(name="dthralteracao")
	private Calendar changeTime;
	
	/**
	 * Data de cadastro
	 */
	@Column(name="dthrcadastro", insertable=false)
	private Date registrationDate;
			
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getFancyName() {
		return fancyName;
	}

	public void setFancyName(String fancyName) {
		this.fancyName = fancyName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	

	public Long getBranchOfficeID() {
		return branchOfficeID;
	}

	public void setBranchOfficeID(Long branchOfficeID) {
		this.branchOfficeID = branchOfficeID;
	}

	/*public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
*/
	public Calendar getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Calendar changeTime) {
		this.changeTime = changeTime;
	}
}
