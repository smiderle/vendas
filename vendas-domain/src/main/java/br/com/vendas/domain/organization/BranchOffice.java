package br.com.vendas.domain.organization;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.vendas.domain.Domain;
import br.com.vendas.domain.location.City;

/**
 * Filial
 * @author LADAIR
 *
 */

@Entity
@Table(name="filial")
@IdClass(BranchOfficePK.class)
public class BranchOffice extends Domain{
	
	public BranchOffice() {	
	}
	
	public BranchOffice(Integer branchOfficeID, Organization organization,
			String realName, String fancyName) {
		super();
		this.branchOfficeID = branchOfficeID;
		this.organization = organization;
		this.realName = realName;
		this.fancyName = fancyName;
	}

	private static final long serialVersionUID = 1L;

	@Id
	private Integer branchOfficeID;
	
	/**
	 * Empresa
	 */
	@Id
	private Organization organization;
	
		
	/**
	 * Razão Social
	 */
	@Column(name="razaosocial")
	private String realName;
	
	/**
	 * Nome Fantasia
	 */
	@Column(name="nomefantasia")
	private String fancyName;
	
	/**
	 * Telefone
	 */
	@Column(name="fone")
	private String phoneNumber;
	
	/**
	 * Fax
	 */	
	@Column(name="fax")
	private String faxNumber;
	
	/**
	 * Rua
	 */
	@Column(name="rua")
	private String street;
	
	/**
	 * Bairro
	 */
	@Column(name="bairro")
	private String district;

	/**
	 * Numero
	 */
	@Column(name="numero")
	private String number;
	
	/**
	 * Cep
	 */
	@Column(name="cep")
	private String postalCode;
	
	/**
	 * Data e hora de alteração
	 */
	@Column(name="dthralteracao")
	private Calendar changeTime;
	
	
	@Column(name="email")
	private String email;
	
	@Column(name="website")
	private String website;
	
	/**
	 * Data de cadastro
	 */
	@Column(name="dthrcadastro", insertable=false)
	private Date registrationDate;
	
	@Column(name="gerente")
	private String manager;
	
	@Column(name="cnpj")
	private String cnpj;
	
	@Column(name="complemento")
	private String addressComplement;
	
	@ManyToOne
	@JoinColumn(name="CIDADE")
	private City city;
	
	
	@Column(name="descmax")
	private Double maximumDiscount;
	
	@Column(name="email_notificacoes")
	private String emailNotification;
	
	/**
	 * Mostrar quantidade em estoque do produto
	 */
	@Column(name="exibir_estoque")
	private boolean showStockProduct;
	
	/**
	 * Permite venda para produtos com estoque negativo
	 */
	@Column(name="estoque_negativo")
	private boolean negativeStockProduct;
	
	/**
	 * Vendedor pode cadastrar cliente.
	 */
	@Column(name="vendedor_cadastra_cliente")
	private boolean sellerRegisterCustomer;
	
	/**
	 * Enviar email para o cliente quando efetivar uma venda.
	 */
	@Column(name="enviar_email_cliente")
	private boolean sendEmailCustomer;
	
	/**
	 * Ação ao evetuar venda para clientes com titulos vencidos, inadimplentes
	 */
	@Column(name="acao_titulo_vencido")
	private String actionOverdue;
		
	/**
	 * Ação ao evetuar venda para clientes com titulos vencidos, inadimplentes
	 */
	@Column(name="acao_limite_credito")
	private String actionCreditLimit;
				
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getFancyName() {
		return fancyName;
	}

	public void setFancyName(String fancyName) {
		this.fancyName = fancyName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	

	public Integer getBranchOfficeID() {
		return branchOfficeID;
	}

	public void setBranchOfficeID(Integer branchOfficeID) {
		this.branchOfficeID = branchOfficeID;
	}


	public Calendar getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Calendar changeTime) {
		this.changeTime = changeTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Double getMaximumDiscount() {
		return maximumDiscount;
	}

	public void setMaximumDiscount(Double maximumDiscount) {
		this.maximumDiscount = maximumDiscount;
	}

	
	public boolean isShowStockProduct() {
		return showStockProduct;
	}

	public void setShowStockProduct(boolean showStockProduct) {
		this.showStockProduct = showStockProduct;
	}

	public boolean isNegativeStockProduct() {
		return negativeStockProduct;
	}

	public void setNegativeStockProduct(boolean negativeStockProduct) {
		this.negativeStockProduct = negativeStockProduct;
	}

	public boolean isSellerRegisterCustomer() {
		return sellerRegisterCustomer;
	}

	public void setSellerRegisterCustomer(boolean sellerRegisterCustomer) {
		this.sellerRegisterCustomer = sellerRegisterCustomer;
	}

	public boolean isSendEmailCustomer() {
		return sendEmailCustomer;
	}

	public void setSendEmailCustomer(boolean sendEmailCustomer) {
		this.sendEmailCustomer = sendEmailCustomer;
	}

	public String getActionOverdue() {
		return actionOverdue;
	}

	public void setActionOverdue(String actionOverdue) {
		this.actionOverdue = actionOverdue;
	}

	public String getActionCreditLimit() {
		return actionCreditLimit;
	}

	public void setActionCreditLimit(String actionCreditLimit) {
		this.actionCreditLimit = actionCreditLimit;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}
	
	
	
	
}
