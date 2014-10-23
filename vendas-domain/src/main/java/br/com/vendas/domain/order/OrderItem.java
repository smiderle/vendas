package br.com.vendas.domain.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vendas.domain.product.Product;

@Entity
@Table(name="PEDIDOITEM")
public class OrderItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795675300876421269L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long ID;
	
	@Column(name="IDEMPRESA")
	private Integer organizationID;
	
	@Column(name="IDFILIAL")
	private Integer branchID;

	
	@Column(name="SEQUENCIA")	
	private Integer sequence;
	
	/*@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="IDPEDIDO")*/
	@ManyToOne
    @JoinColumn(name = "IDPEDIDO")
	private Order order;	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDPRODUTO")
	private Product product;
	
	@Column(name="QUANTIDADE")
	private Double quantity;
	
	@Column(name="PRECOVENDA")
	private Double salePrice;
	
	@Column(name="DESCONTO")
	private Double discount;
	
	@Column(name="OBSERVACAO")
	private String observation;
	
	@ManyToOne
	@JoinColumn(name="TABPRECO")
	private PriceTable priceTable;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
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

	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PriceTable getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(PriceTable priceTable) {
		this.priceTable = priceTable;
	}	
}