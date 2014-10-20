package br.com.vendas.api.rest.v1.product;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import br.com.vendas.aws.s3.picture.Bucket;
import br.com.vendas.domain.product.Product;
import br.com.vendas.services.image.ImageService;
import br.com.vendas.services.product.ProductService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping("/v1/product")
@Controller
public class ProductRest {
	
	private static final Logger LOG = Logger.getLogger(ProductRest.class);
	
	@Inject	
	private ProductService productService;
	
	@Inject
	private ImageService imageService;
	
	
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
	public @ResponseBody ApiResponse save(@RequestBody Product product){
		try {
			ServiceResponse<Product> payload =  productService.saveOrUpdate(product);
			LOG.debug("save - ProductGroup Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	/**
	 * Faz upload da imagem do produto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="uploadImage", method = RequestMethod.POST,  produces="application/json")
	public @ResponseBody ApiResponse  uploadImage(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		try {
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;
			while (itr.hasNext()) {
				mpf = request.getFile(itr.next());
				return ResponseBuilder.build(imageService.upload(Bucket.PICTURES_PRODUCT,  mpf.getInputStream(), mpf.getContentType(), mpf.getSize()));
			}	
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);	
			return ResponseBuilder.build(new VendasExceptionWapper(e));

		}
		return null;

	}
	
	/**
	 * Precisa ter pois se não da erro 500 ao enviar a imagem
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(500000000);
		return multipartResolver;
	}

}
