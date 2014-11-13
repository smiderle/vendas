package br.com.vendas.dto;

import java.util.Date;

import br.com.vendas.domain.order.OrderPayment;

public class OrderPaymentDTO {

	public OrderPaymentDTO(OrderPayment orderPayment, OrderDTO orderDTO) {
		setID(orderPayment.getID());
		setOrganizationID(orderPayment.getOrganizationID());
		setBranchID(orderPayment.getBranchID());
		setSequence(orderPayment.getSequence());
		setExpirationDate(orderPayment.getExpirationDate());
		setPaymentDate(orderPayment.getPaymentDate());
		setChangeTime(orderPayment.getChangeTime());
		setInstallmentValue(orderPayment.getInstallmentValue());
		setObservation(orderPayment.getObservation());
		setDocumentNumber(orderPayment.getDocumentNumber());
		setOrder(orderDTO);
		setRegistrationDate(orderPayment.getRegistrationDate());
	}

	private Long ID;
	
	private Integer organizationID;
	
	private Integer branchID;
	
	//private Order order;	

	private Integer sequence;
	
	private Date expirationDate;
	
	private Date paymentDate;
	
	private Date changeTime;
	
	private Double installmentValue;
	
	private String observation;
	
	private String documentNumber;
	
	private OrderDTO order;
	
	private Date registrationDate;

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

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Double getInstallmentValue() {
		return installmentValue;
	}

	public void setInstallmentValue(Double installmentValue) {
		this.installmentValue = installmentValue;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	
	
}
