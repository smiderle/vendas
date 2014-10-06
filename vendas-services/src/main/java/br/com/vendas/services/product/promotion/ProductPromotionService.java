package br.com.vendas.services.product.promotion;

import java.util.List;

import br.com.vendas.domain.product.ProductPromotion;
import br.com.vendas.exception.ApplicationException;
import br.com.vendas.services.support.ServiceResponse;

public interface ProductPromotionService {
	
	/**
	 * Retorna todas os preços de promoções de determinado produto
	 * @param productID
	 * @return
	 */
	ServiceResponse<List<ProductPromotion>> findAllByProductID(Integer productID);
	
 	/**
 	 * Salva ou atualiza o preço de promoção do produto
 	 * @param product
 	 * @return
 	 * @throws ApplicationException 
 	 */
	ServiceResponse<ProductPromotion> saveOrUpdate(ProductPromotion productPromotion) throws ApplicationException ;
	
 	/**
 	 * Remove o preço de promoção "logicamente"
 	 * @param product
 	 * @return
 	 * @throws ApplicationException 
 	 */
	ServiceResponse<ProductPromotion> remove(ProductPromotion productPromotion) throws ApplicationException ;

}
