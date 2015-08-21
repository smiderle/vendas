package br.com.vendas.api.rest.v1.product;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.product.Product;
import br.com.vendas.helper.ObjectMapperHelper;
import br.com.vendas.services.product.ProductService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

import com.fasterxml.jackson.core.type.TypeReference;

@RequestMapping("/private/v1/product")
@Controller
public class ProductRest {

	private static final Logger LOG = Logger.getLogger(ProductRest.class);

	@Inject
	private ProductService productService;

	/**
	 * Retorna todos os 100 proximos produtos de determinada filial.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getAllByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByBranch(Integer organizationID, Integer branchID,Integer offset){
		try {
			ServiceResponse<List<Product>> payload =  productService.findAllByBranche(organizationID, branchID, offset);
			LOG.debug("getAllByBranch - List<Product> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	/**
	 * Retorna todos os 100 proximos produtos de determinada filial que iniciam com a descrição, ou código.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getAllByFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByFilter(String filter, Integer organizationID, Integer branchID,Integer offset, Integer limit){
		try {
			ServiceResponse<List<Product>> payload =  productService.findByDescriptionOrProductID(filter, organizationID, branchID, offset, limit);
			LOG.debug("getAllByFilter - List<Product> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

	/**
	 * Salva ou atualiza um produto.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse save(@RequestHeader(value="userID") Integer userID, @RequestBody Product product){
		try {
			ServiceResponse<Product> payload =  productService.saveOrUpdate(userID, product);
			LOG.debug("save - ProductGroup Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	@RequestMapping(value="getAllByChangeGreaterThan", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		try {
			ServiceResponse<List<Product>> payload =  productService.findAllByChangeGreaterThan(date, organizationID, offset);
			LOG.debug("getAllByChangeGreaterThan - List<Product> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}


	/**
	 * Salva ou atualiza um produto.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="saveList", method = RequestMethod.POST )
	public @ResponseBody ApiResponse saveList( @RequestBody String products) {
		try {

			List<Product> listProduct = ObjectMapperHelper.readValue(products, new TypeReference<List<Product>>() {});
			ServiceResponse<List<Product>> payload = productService.save(listProduct);
			LOG.debug("saveList - List<Product> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
}
