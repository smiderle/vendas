package br.com.vendas.domain.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PRODUTO")
public class Product {

	public Product() {
	}

	public Product(Integer organizationID, Integer branchID, String productID, ProductGroup group, String description, String reference,
			String packaging, String barcode, Double salePrice, Double stockAmount,  Boolean active, Date changeTime, Date registrationDate,
			boolean excluded, String pictureUrl) {
		super();
		this.organizationID = organizationID;
		this.branchID = branchID;
		this.productID = productID;
		this.group = group;
		this.description = description;
		this.reference = reference;
		this.packaging = packaging;
		this.barcode = barcode;
		this.stockAmount = stockAmount;
		this.salePrice = salePrice;
		this.active = active;
		this.changeTime = changeTime;
		this.registrationDate = registrationDate;
		this.excluded = excluded;
		this.pictureUrl = pictureUrl;
	}

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

	@Column(name="PRECO_VENDA")
	private Double salePrice;

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

	@Transient
	private Integer idMobile;

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

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getIdMobile() {
		return idMobile;
	}

	public void setIdMobile(Integer idMobile) {
		this.idMobile = idMobile;
	}


}
