package br.com.vendas.api.rest.v1.product;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.product.ProductPromotion;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.product.promotion.ProductPromotionService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping("/private/v1/productPromotion")
@Controller
public class ProductPromotionRest {

	private static final Logger LOG = Logger.getLogger(ProductPromotionRest.class);

	@Inject
	private ProductPromotionService productPromotionService;

	/**
	 * Retorna os preços de promoção do produto
	 * @param productID
	 * @return
	 */
	@RequestMapping(value = "getAllByProductID", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse getAllByProductID(Integer productID) {
		try {
			ServiceResponse<List<ProductPromotion>> payload = productPromotionService.findAllByProductID(productID);
			LOG.debug("getAllByProductID - List<ProductPromotion> Size: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	/**
	 * Salva ou atualiza o preço de promoção
	 * @param productPromotion
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse save(@RequestBody ProductPromotion productPromotion) {
		try {
			ServiceResponse<ProductPromotion> payload =  productPromotionService.saveOrUpdate(productPromotion);			
			return ResponseBuilder.build(payload);
		} catch (RegistrationException e) {
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	
	/**
	 * Salva ou atualiza o preço de promoção
	 * @param productPromotion
	 * @return
	 */
	@RequestMapping(value="remove", method = RequestMethod.POST)
	public @ResponseBody ApiResponse remove(@RequestBody ProductPromotion productPromotion) {
		try {
			ServiceResponse<ProductPromotion> payload =  productPromotionService.remove(productPromotion);			
			return ResponseBuilder.build(payload);
		} catch (RegistrationException e) {
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
}