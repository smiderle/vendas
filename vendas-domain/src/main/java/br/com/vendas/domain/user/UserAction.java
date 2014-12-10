package br.com.vendas.domain.user;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="USUARIO_ACOES")
@Entity
public class UserAction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private BigInteger ID;
	
	@Column(name="IDUSUARIO")
	private Integer userID;
	
	@Column(name="DTHRCADASTRO", insertable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	
	@Column(name="DESCRICAO")
	private String description;
	
	@Column(name="TITULO")
	private String title;
	
	@Column(name="CATEGORIA")
	@Enumerated(EnumType.ORDINAL)
	private Category category;
	
	@Column(name="OPERACAO")
	@Enumerated(EnumType.ORDINAL)
	private Operation operation;
	
	public BigInteger getID() {
		return ID;
	}

	

	public Integer getUserID() {
		return userID;
	}



	public void setUserID(Integer userID) {
		this.userID = userID;
	}



	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Category getCategory() {
		return category;
	}



	public void setCategory(Category category) {
		this.category = category;
	}



	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public enum Operation {
		
		/**
		 * 0 - Cadastro
		 */
		SAVE,
		
		/**
		 * 1 - Edição
		 */
		UPDATE,
		
		/**
		 * 2 - Remoção
		 */
		DELETE
	}
	
	public enum Category {
		/**
		 * 0
		 */
		MESSAGE,	
		
		/**
		 * 1
		 */
		USER,
		
		/**
		 * 2
		 */
		PRODUCT,
		
		/**
		 * 3
		 */
		CUSTOMER,
		
		/**
		 * 4
		 */
		BRANCH,
		
		/**
		 * 5
		 */
		PRICE_TABLE,
		
		/**
		 * 6
		 */
		INSTALLMENT,
		
		/**
		 * 7
		 */
		PRODUCT_GROUP,
		
		/**
		 * 8 - Order
		 */
		ORDER,
		
		/**
		 * 9 - 
		 */
		PAYMENT,
		
		/**
		 * 10
		 */
		LOGIN,
		
		/**
		 * 11
		 */
		TARGETS
		
			
	}
	
	

}
