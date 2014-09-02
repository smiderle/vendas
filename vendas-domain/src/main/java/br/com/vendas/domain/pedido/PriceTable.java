package br.com.vendas.domain.pedido;

import java.util.Calendar;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="IDEMPRESA")
	private Long organizationID;
	
	@Column(name="IDFILIAL")
	private Long branchID;
	
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
					
	
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Long organizationID) {
		this.organizationID = organizationID;
	}

	
	public Long getBranchID() {
		return branchID;
	}

	public void setBranchID(Long branchID) {
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
