package br.com.vendas.api.rest.v1.customer;

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
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.product.Product;
import br.com.vendas.services.customer.CustomerService;
import br.com.vendas.services.image.ImageService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value="/v1/customer")
@Controller
public class CustomerRest {
	
	private static final Logger LOG = Logger.getLogger(CustomerRest.class);
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	private ImageService imageService;
	
	@RequestMapping(value="getCustomersByOrganizationID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getCustomersByOrganizationID(Integer organizationID, Integer branchID, Integer offset) {
		try {
			ServiceResponse<List<Customer>> payload =  customerService.findAllByOrganizationID(organizationID, branchID, offset);
			LOG.debug("List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	/**
	 * Salva o cliente.
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="saveCustomer", method = RequestMethod.POST)
	public @ResponseBody ApiResponse saveCustomer(@RequestBody Customer customer){
		try{			
			ServiceResponse<Customer> payload =  customerService.save(customer);			
			LOG.debug("List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	
	
	@RequestMapping(value="getAllByFilter", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getAllByFilter(String filter, Integer organizationID, Integer branchID,Integer offset){
		try {
			ServiceResponse<List<Customer>> payload =  customerService.findByIDOrNameOrEmail(filter, organizationID, branchID, offset);
			LOG.debug("getAllByFilter - List<Customer> Size: "+payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));			
		}
	}
	

	/**
	 * Faz upload da imagem do cliente
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
				return ResponseBuilder.build(imageService.upload(Bucket.PICTURES_CUSTOMER,  mpf.getInputStream(), mpf.getContentType(), mpf.getSize()));
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
