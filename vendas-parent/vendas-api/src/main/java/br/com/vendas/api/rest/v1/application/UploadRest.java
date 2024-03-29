package br.com.vendas.api.rest.v1.application;

import java.io.IOException;
import java.util.Iterator;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import br.com.vendas.aws.s3.picture.Bucket;
import br.com.vendas.services.image.ImageService;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping("/public/v1/upload")
@Controller
public class UploadRest {
	
	private static final Logger LOG = Logger.getLogger(UploadRest.class);
	
	@Inject
	private ImageService imageService;
	
	/**
	 * Faz upload da imagem do produto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="uploadUserPicture", method = RequestMethod.POST,  produces="application/json")
	public @ResponseBody ApiResponse  uploadUserPicture(MultipartHttpServletRequest request,
			HttpServletResponse response) {
		try {
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = null;
			while (itr.hasNext()) {
				mpf = request.getFile(itr.next());
				return ResponseBuilder.build(imageService.upload(Bucket.PICTURES_USER,  mpf.getInputStream(), mpf.getContentType(), mpf.getSize()));
			}	
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);	
			return ResponseBuilder.build(new VendasExceptionWapper(e));

		}
		return null;
	}
	
	@RequestMapping(value="uploadCustomerPicture", method = RequestMethod.POST,  produces="application/json")
	public @ResponseBody ApiResponse  uploadCustomerPicture(MultipartHttpServletRequest request,
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
	
	@RequestMapping(value="uploadProductPicture", method = RequestMethod.POST,  produces="application/json")
	public @ResponseBody ApiResponse  uploadProductPicture(MultipartHttpServletRequest request,
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
