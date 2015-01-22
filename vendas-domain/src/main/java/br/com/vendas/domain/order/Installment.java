package br.com.vendas.domain.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PARCELAMENTO")
public class Installment {
	
	public Installment() {
	}
	
	public Installment(Integer organizationID, Integer branchID, Integer installmentID, String description, String installmentsDays, Double tax,
			boolean active, Date changeTime, boolean excluded) {
		super();
		this.organizationID = organizationID;
		this.branchID = branchID;
		this.installmentID = installmentID;
		this.description = description;
		this.installmentsDays = installmentsDays;
		this.tax = tax;
		this.active = active;
		this.changeTime = changeTime;
		this.excluded = excluded;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;
	
	@Column(name="IDEMPRESA")
	private Integer organizationID;
	
	@Column(name="IDFILIAL")
	private Integer branchID;
	
	@Column(name="idparcelamento")
	private Integer installmentID;

	@Column(name="DESCRICAO", length=40)
	private String description;
	
	@Column(name="parcelas")
	private String installmentsDays;
	
	@Column(name="TAXA")
	private Double tax;
	
	@Column(name="ATIVO", nullable=false)
	private boolean active;
	
	@Column(name="DTHRALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changeTime;
	

	@Column(name="EXCLUIDA")
	private boolean excluded;


	public Integer getID() {
		return ID;
	}


	public void setID(Integer iD) {
		ID = iD;
	}


	public Integer getOrganizationID() {
		return organizationID;
	}


	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}


	public Integer getBranchID() {
		return branchID;
	}


	public void setBranchID(Integer branchID) {
		this.branchID = branchID;
	}


	public Integer getInstallmentID() {
		return installmentID;
	}


	public void setInstallmentID(Integer installmentID) {
		this.installmentID = installmentID;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getInstallmentsDays() {
		return installmentsDays;
	}


	public void setInstallmentsDays(String installmentsDays) {
		this.installmentsDays = installmentsDays;
	}


	public Double getTax() {
		return tax;
	}


	public void setTax(Double tax) {
		this.tax = tax;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Date getChangeTime() {
		return changeTime;
	}


	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}


	public boolean isExcluded() {
		return excluded;
	}


	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	
}
