package br.com.vendas.domain.product;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRODUTO")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer ID;
	
	@Column(name="IDEMPRESA")
	private Integer organizationID;
	
	@Column(name="IDFILIAL")
	private Integer branchID;
	
	@Column(name="CODIGO_PRODUTO")
	private String productID;
		
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDGRUPO")
	private ProductGroup group;
	
	@Column(name="DESCRICAO")
	private String description;
	
	@Column(name="REFERENCIA")
	private String reference;
	
	@Column(name="embalagem")
	private String packaging;
	
	@Column(name="CODBAR")
	private String barcode;
	
	@Column(name="QUANTIDADE_ESTOQUE")
	private Double stockAmount;
	
	@Column(name="DESCMAX")
	private Double maximumDiscount;
	
	@Column(name="PRECO_VENDA")
	private Double salesPrice;
	
	@Column(name="ATIVO")
	private Boolean active;
	
	@Column(name="DTHRALTERACAO")
	private Date changeTime;
	
	
	@Column(name="DTHRCADASTRO", insertable=false)
	private Date registrationDate;
	
	@Column(name="EXCLUIDO")
	private boolean excluded;
	
	@Column(name="url_imagem")
	private String pictureUrl;

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

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public ProductGroup getGroup() {
		return group;
	}

	public void setGroup(ProductGroup group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Double getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}

	public Double getMaximumDiscount() {
		return maximumDiscount;
	}

	public void setMaximumDiscount(Double maximumDiscount) {
		this.maximumDiscount = maximumDiscount;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
		

}
