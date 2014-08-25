package br.com.vendas.dao.location;

import java.util.List;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.location.City;

public interface CityDAO extends GenericDAO<City>  {
	
	
	/**
	 * retorna as cidades por descrição
	 * @param description
	 * @return
	 */
	List<City> findAllByDescription(String description, Integer offset, Integer limit);
	
	/**
	 * Retorna uma lista de cidades por código IBGE. Devera retornar somente uma cidade, pois o código IBGE, deve ser unico, porém, 
	 * como o código é fornecido de terceiros, pode ser que exista código repedito, então é retornado uma lista. 
	 * @param ibgeCode
	 * @return
	 */
	List<City> findByIBGECode(Integer ibgeCode);

}
