package br.com.vendas.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.order.OrderItem;
import br.com.vendas.domain.order.OrderPayment;

public class OrderDTO {
	
	
	public OrderDTO(Order order, Installment installment, Set<OrderItem> ordersItens, Set<OrderPayment> ordersPayments) {
		if(order != null){
			setID(order.getID());
			setOrganizationID(order.getOrganizationID());
			setBranchID(order.getBranchID());
			setUserID(order.getUserID());
			setInstallment(installment);
			setGrossValue(order.getGrossValue());
			setNetValue(order.getNetValue());
			setTotalDiscount(order.getTotalDiscount());
			setChangeTime(order.getChangeTime());
			setObservation(order.getObservation());
			setIssuanceTime(order.getIssuanceTime());
			setOrdersItens(ordersItens);
			setOrdersPayments(ordersPayments);
			setCustomer(order.getCustomer());
			setType(order.getType());
			setFormPayment(order.getFormPayment());
		}		
	}
	
	
	private Long ID;	
	
	private Integer organizationID;	
	
	private Integer branchID;
	
	private Customer customer;
		
	private Integer userID;
		
	private Installment installment;
		
	private Double grossValue;
	
	private Double netValue;
	
	private Double totalDiscount;
	
	private Date changeTime;
		
	private String observation;
	
	private Date issuanceTime;
	
	private Integer type;
	
	private Integer formPayment;

	private Set<OrderItem> ordersItens = new HashSet<>(0);	
	
	private Set<OrderPayment> ordersPayments = new HashSet<>(0);

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

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
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

	public Set<OrderPayment> getOrdersPayments() {
		return ordersPayments;
	}

	public void setOrdersPayments(Set<OrderPayment> ordersPayments) {
		this.ordersPayments = ordersPayments;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFormPayment() {
		return formPayment;
	}

	public void setFormPayment(Integer formPayment) {
		this.formPayment = formPayment;
	}

	
	

}
