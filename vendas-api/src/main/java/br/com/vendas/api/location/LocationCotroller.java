package br.com.vendas.api.location;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendas.domain.location.Address;
import br.com.vendas.domain.location.City;
import br.com.vendas.services.location.CityService;
import br.com.vendas.services.location.PostalCodeService;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;

@RequestMapping(value = "/location")
@Controller
public class LocationCotroller {

	private static final Logger LOG = Logger.getLogger(LocationCotroller.class);

	@Autowired
	private PostalCodeService postalCodeService;
	
	@Autowired
	private CityService cityService;

	@RequestMapping(value = "getAddressByPostalCode", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse getAddress(String postalCode) {
		try {
			ServiceResponse<Address> payload = postalCodeService.getStreet(postalCode);
			LOG.debug("getAddress: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}
	
	
	@RequestMapping(value = "getAllByDescription", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse getAllByDescription(String description, Integer offset) {
		try {
			ServiceResponse<List<City>> payload = cityService.findAllByDescription(description, offset);
			LOG.debug("getAllByDescription: " + payload.getRowCount());
			return ResponseBuilder.build(payload);
		} catch (Exception e) {
			LOG.error(e);
			return ResponseBuilder.build(new VendasExceptionWapper(e));
		}
	}

}
