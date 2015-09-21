package br.com.vendas.domain.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.vendas.domain.location.City;
import br.com.vendas.domain.order.Installment;

@Entity
@Table(name="CLIENTE")
public class Customer {

	public Customer() {
	}

	public Customer(Integer organizationID, Integer branchID,String name, String nickName, Integer personType, String cpfCnpj,
			String incricao, String commercialPhone, String homePhone, String cellPhone, String postalCode, String addressComplement,
			String observation, Date registrationDate, Date changeTime, City city, String faxNumber, String street, String district, String number,
			String email, Double defaultDiscount, Double creditLimit, Integer defaultSeller, boolean active, Date birth, boolean excluded,
			String pictureUrl, Integer priceTable, Installment installment, Integer formPayment) {

		super();
		this.organizationID = organizationID;
		this.branchID = branchID;
		this.name = name;
		this.nickName = nickName;
		this.personType = personType;
		this.cpfCnpj = cpfCnpj;
		this.incricao = incricao;
		this.commercialPhone = commercialPhone;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.postalCode = postalCode;
		this.addressComplement = addressComplement;
		this.observation = observation;
		this.registrationDate = registrationDate;
		this.changeTime = changeTime;
		this.city = city;
		this.faxNumber = faxNumber;
		this.street = street;
		this.district = district;
		this.number = number;
		this.email = email;
		this.defaultDiscount = defaultDiscount;
		this.creditLimit = creditLimit;
		this.defaultSeller = defaultSeller;
		this.active = active;
		this.birth = birth;
		this.excluded = excluded;
		this.pictureUrl = pictureUrl;
		this.priceTable = priceTable;
		this.installment = installment;
		this.formPayment = formPayment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer ID;

	@Column(name="IDEMPRESA")
	private Integer organizationID;

	@Column(name="IDFILIAL")
	private Integer branchID;


	@Column(name="NOME")
	private String name;

	/**
	 * Apelido para pessoa fisica, nome fantasia para pessoa juridica
	 */
	@Column(name="APELIDO_FANTASIA")
	private String nickName;

	@Column	(name="TIPO_PESSOA")
	private Integer personType;

	@Column(name="CPFCNPJ")
	private String cpfCnpj;

	/**
	 * RG ou Inscrição Estadual
	 */
	@Column(name="inscriestad")
	private String incricao;

	@Column(name="FONE_COMERCIAL")
	private String commercialPhone;

	@Column(name="FONE_RESIDENCIAL")
	private String homePhone;

	@Column(name="celular")
	private String cellPhone;

	@Column(name="CEP")
	private String postalCode;

	@Column(name="complemento")
	private String addressComplement;

	@Column(name="observacao")
	private String observation;

	/**
	 * Data de casdastro
	 */
	@Column(name="dthrcadastro", insertable=false, updatable= false)
	private Date registrationDate;

	/**
	 * Data e hora de alteração
	 */
	@Column(name="dthralteracao")
	private Date changeTime;

	@ManyToOne
	@JoinColumn(name="CIDADE")
	private City city;

	/**
	 * Fax
	 */
	@Column(name="FAX")
	private String faxNumber;

	/**
	 * Rua
	 */
	@Column(name="RUA")
	private String street;

	/**
	 * Bairro
	 */
	@Column(name="BAIRRO")
	private String district;

	/**
	 * Numero
	 */
	@Column(name="NUMERO")
	private String number;

	/**
	 * Email
	 */
	@Column(name="EMAIL")
	private String email;

	/**
	 * Desconto padrão do cliente
	 */
	@Column(name="DESCONTO_PADRAO")
	private Double defaultDiscount;

	@Column(name="LIMITE_CREDITO")
	private Double creditLimit;

	@Column(name="VENDEDOR_PADRAO")
	private Integer defaultSeller;

	@Column(name="ATIVO")
	private boolean active;

	@Column(name="dtnascimento")
	private Date birth;

	@Column(name="EXCLUIDO")
	private boolean excluded;

	@Column(name="url_imagem")
	private String pictureUrl;

	@Column(name="TABPRECO")
	private Integer priceTable;

	@ManyToOne
	@JoinColumn(name="IDPARCELAMENTO")
	private Installment installment;


	@Column(name="FORMA_PAGAMENTO")
	private Integer formPayment;

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


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getIncricao() {
		return incricao;
	}

	public void setIncricao(String incricao) {
		this.incricao = incricao;
	}


	public String getCommercialPhone() {
		return commercialPhone;
	}

	public void setCommercialPhone(String commercialPhone) {
		this.commercialPhone = commercialPhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getDefaultDiscount() {
		return defaultDiscount;
	}

	public void setDefaultDiscount(Double defaultDiscount) {
		this.defaultDiscount = defaultDiscount;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getDefaultSeller() {
		return defaultSeller;
	}

	public void setDefaultSeller(Integer defaultSeller) {
		this.defaultSeller = defaultSeller;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
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

	public Integer getPriceTable() {
		return priceTable;
	}

	public void setPriceTable(Integer priceTable) {
		this.priceTable = priceTable;
	}


	public Integer getFormPayment() {
		return formPayment;
	}

	public void setFormPayment(Integer formPayment) {
		this.formPayment = formPayment;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public Integer getIdMobile() {
		return idMobile;
	}

	public void setIdMobile(Integer idMobile) {
		this.idMobile = idMobile;
	}

}