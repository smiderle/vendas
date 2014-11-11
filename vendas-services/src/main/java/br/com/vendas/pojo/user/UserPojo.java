package br.com.vendas.pojo.user;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.vendas.domain.application.MenuApplication;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.domain.user.UserRole;

public class UserPojo {
	
	public UserPojo(br.com.vendas.domain.user.User userDomain, Set<MenuApplication> menusApplication, List<UserBranchOffice> userBranches, Set<UserRole> userRoles) {
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
	private Calendar changeTime;	
	private Date registrationDate;	
	private boolean active;	
	private boolean portalAccess;
	private Set<MenuApplication> menusApplication;	
	private List<UserBranchOffice> userBranches;	
	private Set<UserRole> userRoles;
	
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
}
