package br.com.vendas.api.rest.v1.application;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.application.License;
import br.com.vendas.dto.LicenseDTO;
import br.com.vendas.services.application.LicenseService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping("/private/v1/license")
@Controller
public class LicenseRest {

	private static final Logger LOG = Logger.getLogger(LicenseRest.class);

	@Inject
	private LicenseService deviceService;

	@RequestMapping(value="getByID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByID(Integer id){

		try {

			ServiceResponse<License> payload =  deviceService.findByID( id );

			LOG.debug("License Size: "+payload.getRowCount());

			return ResponseBuilder.build(payload);

		} catch (Exception e) {

			LOG.error(e.getMessage(), e);

			return ResponseBuilder.build(new VendasExceptionWapper(e));

		}
	}

	@RequestMapping(value="getByUserID", method = RequestMethod.GET)
	public @ResponseBody ApiResponse getByUserID(Integer userId){

		try {

			LicenseDTO license =  deviceService.findByUserId( userId );

			return ResponseBuilder.build( license );

		} catch (Exception e) {

			LOG.error(e.getMessage(), e);

			return ResponseBuilder.build(new VendasExceptionWapper(e));

		}
	}



}
