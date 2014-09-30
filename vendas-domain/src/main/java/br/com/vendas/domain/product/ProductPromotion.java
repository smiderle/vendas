package br.com.vendas.domain.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PRODUTO_PROMOCAO")
public class ProductPromotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name="IDPRODUTO")
	private Long productID;
	
	@Column(name="PRECO_PROMOCAO")
	private Double promotionPrice;
	
	@Column(name="DATA_INICIAL")
	@Temporal(TemporalType.DATE)
	private Date initialDate;

	@Column(name="DATA_FINAL")
	@Temporal(TemporalType.DATE)
	private Date finalDate;
	
	@Column(name="EXCLUIDA")
	private boolean excluded;
	
	@Column(name="DTHRALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date changeTime;

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public Double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(Double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	
}
