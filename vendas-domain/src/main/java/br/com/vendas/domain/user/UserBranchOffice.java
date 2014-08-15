package br.com.vendas.domain.user;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.organization.BranchOffice;

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
	
	public UserBranchOffice() {
	
	}
	
	
	public UserBranchOffice(BranchOffice branchOffice,
			Long userID, Double maximumDiscount, boolean viewAllCustomers,
			Double minimumValueSales, boolean active) {
		super();		
		this.userID = userID;
		this.maximumDiscount = maximumDiscount;
		this.viewAllCustomers = viewAllCustomers;
		this.minimumValueSales = minimumValueSales;
		this.active = active; 
		this.branchOffice = branchOffice;
	}

	private static final long serialVersionUID = 1327645454492832287L;

	@Id
	private BranchOffice branchOffice;	
		
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
	

	@Column(name="ATIVO")
	private boolean active;
	

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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BranchOffice getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(BranchOffice branchOffice) {
		this.branchOffice = branchOffice;
	}
	
	
}
