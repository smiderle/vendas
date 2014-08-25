package br.com.vendas.services.location;

import java.util.List;

import br.com.vendas.domain.location.City;
import br.com.vendas.services.support.ServiceResponse;

public interface CityService {
	
	
	/**
	 * retorna as cidades por descrição
	 * @param description
	 * @return
	 */
	ServiceResponse<List<City>> findAllByDescription(String description, Integer offset);
	
	/**
	 * Retorna a cidade por código IBGE
	 * @param ibgeCode
	 * @return
	 */
	ServiceResponse<City> findByIBGECode(Integer ibgeCode);


}
