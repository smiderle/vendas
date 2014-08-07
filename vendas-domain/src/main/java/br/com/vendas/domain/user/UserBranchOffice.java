package br.com.vendas.domain.user;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;

/**
 * Usuario Filial.
 * Esta classe representa a empresa do usuario.
 *  
 * @author LADAIR
 *
 */

@Entity
@Table(name="USUARIO_FILIAL")
@IdClass(value=UserBranchOfficePK.class)
public class UserBranchOffice extends Domain {
	
	
	public UserBranchOffice(Long organizationID, Long branchOfficeID,
			Long userID, Double maximumDiscount, boolean viewAllCustomers,
			Double minimumValueSales) {
		super();
		this.organizationID = organizationID;
		this.branchOfficeID = branchOfficeID;
		this.userID = userID;
		this.maximumDiscount = maximumDiscount;
		this.viewAllCustomers = viewAllCustomers;
		this.minimumValueSales = minimumValueSales;
	}

	private static final long serialVersionUID = 1327645454492832287L;

	@Id
	private Long organizationID;
	
	@Id
	private Long branchOfficeID;
	
	@Id
	private Long userID;

	
	/**
	 * Desconto Máximo
	 */
	@Column(name="descmax")
	private Double maximumDiscount;
	
	/**
	 * Visualiza todos os clientes
	 */
	@Column(name="todosclientes")
	private boolean viewAllCustomers;
	
	/**
	 * Valor Minimo de Venda.
	 */
	@Column(name="minvenda")
	private Double minimumValueSales;
	
	/**
	 * Data e Hora de Alteração
	 */
	@Column(name="dthralteracao")
	private Calendar changeTime;

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	public Long getBranchOfficeID() {
		return branchOfficeID;
	}

	public void setBranchOfficeID(Long branchOfficeID) {
		this.branchOfficeID = branchOfficeID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
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
}
