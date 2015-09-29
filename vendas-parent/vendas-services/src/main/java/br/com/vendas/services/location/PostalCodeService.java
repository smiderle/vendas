package br.com.vendas.services.location;

import br.com.vendas.domain.location.Address;
import br.com.vendas.exception.VendasException;
import br.com.vendas.services.support.ServiceResponse;

public interface PostalCodeService {
	
	ServiceResponse<Address> getStreet(String postalCode) throws VendasException;

}
