package br.com.vendas.api.rest.v1.productgroup;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.product.ProductGroup;
import br.com.vendas.services.product.group.ProductGroupService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;


@RequestMapping(value="/v1/productGroup")
@Controller
public class ProductGroupRest {
	
	private static final Logger LOG = Logger.getLogger(ProductGroupRest.class);

	@Inject
	private ProductGroupService productGroupService;
	
	/**
	 * Retorna todos os grupos de determinada filial.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getAllByBranch", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByBranch(Integer organizationID, Integer branchID,Integer offset){
		try {
			ServiceResponse<List<ProductGroup>> payload =  productGroupService.findAllByBranch(organizationID, branchID, offset);
			LOG.debug("getAllByBranch - List<ProductGroup> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	/**
	 * Retorna todos os grupos de determinada filial que comecem os a descrição passada por parametro.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="getAllByDescription", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByBranch(String description, Integer organizationID, Integer branchID,Integer offset){
		try {
			ServiceResponse<List<ProductGroup>> payload =  productGroupService.findAllByDescription(description, organizationID, branchID, offset);
			LOG.debug("findByDescription - List<ProductGroup> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	
	/**
	 * Salva ou atualiza um grupo de produtos.
	 * @param organizationID
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	public @ResponseBody ApiResponse getAllByBranch(@RequestBody ProductGroup productGroup){
		try {
			ServiceResponse<ProductGroup> payload =  productGroupService.saveOrUpdate(productGroup);
			LOG.debug("save - ProductGroup Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}	
}
