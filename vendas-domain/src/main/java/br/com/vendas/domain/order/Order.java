package br.com.vendas.domain.order;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vendas.domain.customer.Customer;


@Entity
@Table(name="PEDIDO")
public class Order implements Serializable {
	
	public static final int PEDIDO = 1;
	public static final int ORCAMENTO = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8873651768509661696L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long ID;
	
	@Column(name="IDEMPRESA")
	private Integer organizationID;
	
	@Column(name="IDFILIAL")
	private Integer branchID;
	
	@ManyToOne
	@JoinColumn(name="IDCLIENTE")
	private Customer customer;
	
	/*@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="IDVENDEDOR")
	private User user;*/
	
	@Column(name="IDVENDEDOR")
	private Integer userID;
	
	
	@ManyToOne
	@JoinColumn(name="IDPARCELAMENTO")
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
		
	@Column(name="OBSERVACAO")	
	private String observation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DTHREMISSAO")
	private Date issuanceTime;

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="IDPEDIDO")*/
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order", fetch = FetchType.LAZY)
	private Set<OrderItem> ordersItens = new HashSet<>(0);	
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "order", fetch = FetchType.LAZY)
	@OrderBy("sequence")
	private Set<OrderPayment> ordersPayments = new HashSet<>(0);
	
	@Column(name="FORMAPAGAMENTO")
	private Integer formPayment;
	
	@Column(name="EXCLUIDO")
	private boolean excluded;
	
	/**
	 * 1 - Pedido
	 * 2 - Orçamento
	 */
	@Column(name="TIPO")
	private Integer type;
	
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getIssuanceTime() {
		return issuanceTime;
	}

	public void setIssuanceTime(Date issuanceTime) {
		this.issuanceTime = issuanceTime;
	}
	
	
	public Set<OrderItem> getOrdersItens() {
		return ordersItens;
	}

	public void setOrdersItens(Set<OrderItem> ordersItens) {
		this.ordersItens = ordersItens;
	}

	public Integer getFormPayment() {
		return formPayment;
	}

	public void setFormPayment(Integer formPayment) {
		this.formPayment = formPayment;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Set<OrderPayment> getOrdersPayments() {
		return ordersPayments;
	}

	public void setOrdersPayments(Set<OrderPayment> ordersPayments) {
		this.ordersPayments = ordersPayments;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}