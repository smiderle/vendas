package br.com.vendas.domain.user;

import java.util.Calendar;
import java.util.Date;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.organization.BranchOffice;


public class UserGroup extends Domain{
	/**
	 * Código interno.
	 */
	private Integer userGroupID;
	

	private BranchOffice branchOffice;
	
	/**
	 * Desconto Máximo
	 */
	private Double maximumDiscount;
	
	/**
	 * Visualiza todos os clientes
	 */
	private boolean viewAllCustomers;
	
	/**
	 * Valor Minimo de Venda.
	 */
	private Double minimumValueSales;
	
	/**
	 * Data e Hora de Alteração
	 */
	private Calendar changeTime;
	
	/**
	 * Data de Cadastro
	 */
	private Date registrationDate;

	public Integer getUserGroupID() {
		return userGroupID;
	}

	public void setUserGroupID(Integer userGroupID) {
		this.userGroupID = userGroupID;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}

	public Double getMaximumDiscount() {
		return maximumDiscount;
	}

	public void setMaximumDiscount(Double maximumDiscount) {
		this.maximumDiscount = maximumDiscount;
	}

	public boolean isViewAllCustomers() {
		return viewAllCustomers;
	}

	public void setViewAllCustomers(boolean viewAllCustomers) {
		this.viewAllCustomers = viewAllCustomers;
	}

	public Double getMinimumValueSales() {
		return minimumValueSales;
	}

	public void setMinimumValueSales(Double minimumValueSales) {
		this.minimumValueSales = minimumValueSales;
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
}
