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
@Table(name="TABELA_PRECO")
public class PriceTable {
	
	public PriceTable() {
	}
	
	public PriceTable(Integer organizationID, Integer branchID, Integer priceTableID, String description, Double percentage, boolean increase,
			boolean active, Date changeTime, boolean excluded) {
		super();
		this.organizationID = organizationID;
		this.branchID = branchID;
		this.priceTableID = priceTableID;
		this.description = description;
		this.percentage = percentage;
		this.increase = increase;
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
	
	@Column(name="IDTABELA")
	private Integer priceTableID;
		
	@Column(name="DESCRICAO", length=40)
	private String description;
	
	@Column(name="PERCENTUAL")
	private Double percentage;
	
	@Column(name="ACRESCIMO")
	private boolean increase;
	
	@Column(name="ATIVO")	
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

	public Integer getPriceTableID() {
		return priceTableID;
	}

	public void setPriceTableID(Integer priceTableID) {
		this.priceTableID = priceTableID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public boolean isIncrease() {
		return increase;
	}

	public void setIncrease(boolean increase) {
		this.increase = increase;
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
