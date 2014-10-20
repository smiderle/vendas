package br.com.vendas.domain.order;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.user.User;


@Entity
@Table(name="PEDIDO")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer ID;
	
	@Column(name="IDEMPRESA")
	private Integer organizationID;
	
	@Column(name="IDFILIAL")
	private Integer branchID;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDCLIENTE")
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDVENDEDOR")
	private User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TABELAPRECO")
	private PriceTable priceTable;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PARCELAMENTO")
	private Installment installment;
	
	@Column(name="VALORBRUTO")
	private Double grossValue;
	
	@Column(name="VALORLIQUIDO")
	private Double netValue;
	
	@Column(name="DESCONTOTOTAL")
	private Double totalDiscount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTHRALTERACAO")
	private Date changeTime;
		
	@Column(name="OBSERVATION")	
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTHREMISSAO")
	private Calendar issuanceTime;

	@OneToMany
	@JoinColumn(name="IDPEDIDO")
	private List<OrderItem> lsOrderIten;	
	
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PriceTable getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(PriceTable priceTable) {
		this.priceTable = priceTable;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public Double getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(Double grossValue) {
		this.grossValue = grossValue;
	}

	public Double getNetValue() {
		return netValue;
	}

	public void setNetValue(Double netValue) {
		this.netValue = netValue;
	}

	public Double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Calendar getIssuanceTime() {
		return issuanceTime;
	}

	public void setIssuanceTime(Calendar issuanceTime) {
		this.issuanceTime = issuanceTime;
	}

	public List<OrderItem> getLsOrderIten() {
		return lsOrderIten;
	}

	public void setLsOrderIten(List<OrderItem> lsOrderIten) {
		this.lsOrderIten = lsOrderIten;
	}
	
}