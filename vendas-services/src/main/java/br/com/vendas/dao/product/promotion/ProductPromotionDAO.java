package br.com.vendas.dao.product.promotion;

import java.util.Date;
import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.product.ProductPromotion;

public interface ProductPromotionDAO extends GenericDAO<ProductPromotion> {

	/**
	 * Retorna todos os preços de promoções de determinado produto. Não serão
	 * apresentados os preços que ecerraram após a data passada por parametro
	 * 
	 * @param productID
	 * @return
	 */
	List<ProductPromotion> findAllByProductID(Long productID, Date afterDate);
	
	/**
	 * Retorna as promoções em um intervalo de datas. Com exceção da promoção com id passado por parametro
	 * @param productID
	 * @param finalDate
	 * @param initalDate
	 * @param productPromotionID
	 * @return
	 */
	List<ProductPromotion> findByByInitalDateAndFinalDate(Long productID, Date finalDate, Date initalDate, Long productPromotionID);

}
