package br.com.vendas.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.location.City;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.domain.user.UserRole;

public class UserDTO {
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UserDTO(br.com.vendas.domain.user.User userDomain) {
		setUserID(userDomain.getUserID());
		setActive(userDomain.isActive());
		setChangeTime(userDomain.getChangeTime());
		setEmail(userDomain.getEmail());
		setName(userDomain.getName());
		setOrganizationID(userDomain.getOrganizationID());
		setPassword(userDomain.getPassword());
		setPortalAccess(userDomain.isPortalAccess());
		setRegistrationDate(userDomain.getRegistrationDate());
		setBirthDate(userDomain.getBirthDate());
		setPhoneNumber(userDomain.getPhoneNumber());
		setPictureUrl(userDomain.getPictureUrl());
		setLinkFacebook(userDomain.getLinkFacebook());
		setLinkGooglePlus(userDomain.getLinkGooglePlus());
		setSkype(userDomain.getSkype());
		setStreet(userDomain.getStreet());
		setDistrict(userDomain.getDistrict());
		setNumber(userDomain.getNumber());
		setPostalCode(userDomain.getPostalCode());
		setCity(userDomain.getCity());
		setCellPhone(userDomain.getCellPhone());
		setCpfCnpj(userDomain.getCpfCnpj());
		setInscricao(userDomain.getInscricao());
		setContactName(userDomain.getContactName());
		setObservation(userDomain.getObservation());
	}
	
	public UserDTO(br.com.vendas.domain.user.User userDomain, Set<MenuApplication> menusApplication, List<UserBranchOffice> userBranches, Set<UserRole> userRoles) {
		if(userDomain != null){
			setUserID(userDomain.getUserID());
			setActive(userDomain.isActive());
			setChangeTime(userDomain.getChangeTime());
			setEmail(userDomain.getEmail());
			setName(userDomain.getName());
			setOrganizationID(userDomain.getOrganizationID());
			setPassword(userDomain.getPassword());
			setPortalAccess(userDomain.isPortalAccess());
			setRegistrationDate(userDomain.getRegistrationDate());
			setBirthDate(userDomain.getBirthDate());
			setPhoneNumber(userDomain.getPhoneNumber());
			setPictureUrl(userDomain.getPictureUrl());
			setLinkFacebook(userDomain.getLinkFacebook());
			setLinkGooglePlus(userDomain.getLinkGooglePlus());
			setSkype(userDomain.getSkype());
			setStreet(userDomain.getStreet());
			setDistrict(userDomain.getDistrict());
			setNumber(userDomain.getNumber());
			setPostalCode(userDomain.getPostalCode());
			setCity(userDomain.getCity());
			setCellPhone(userDomain.getCellPhone());
			setCpfCnpj(userDomain.getCpfCnpj());
			setInscricao(userDomain.getInscricao());
			setContactName(userDomain.getContactName());
			setObservation(userDomain.getObservation());
		}
		
		setMenusApplication(menusApplication);
		setUserBranches(userBranches);
		setUserRoles(userRoles);
		
	}
	
	
	private Integer userID;	
	private Integer organizationID;
	private String email;	
	private String password;	
	private String name;	
	private Date changeTime;	
	private Date registrationDate;	
	private boolean active;	
	private boolean portalAccess;
	private Set<MenuApplication> menusApplication;	
	private List<UserBranchOffice> userBranches;	
	private Set<UserRole> userRoles;	
	private Date birthDate;	
	private String phoneNumber;
	private String cellPhone;
	private String pictureUrl;
	private String linkFacebook;
	private String linkGooglePlus;
	private String skype;
	private String street;	
	private String district;
	private String number;	
	private String postalCode;
	private City city;
	private String cpfCnpj;
	private String inscricao;
	private String contactName;
	private String observation;
	
	
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
	
	
	
	
	
}
