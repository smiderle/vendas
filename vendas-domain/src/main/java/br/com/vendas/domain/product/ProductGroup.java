package br.com.vendas.domain.product;

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
@Table(name="PRODUTO_GRUPO")
public class ProductGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="IDEMPRESA")
	private Long organizationID;
	
	@Column(name="IDFILIAL")
	private Long branchID;
	
	@Column(name="IDGRUPO")
	private Integer groupID;
	
	@Column(name="DESCRICAO")
	private String description;
	
	@Column(name="DTHRALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changeTime;

	@Column(name="EXCLUIDO")
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

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
