package br.com.vendas.services.location;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.location.CityDAO;
import br.com.vendas.domain.location.City;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CityServiceImpl implements CityService{

	private static final Integer LIMIT_CITIES = 15;
	
	@Inject
	private CityDAO cityDAO;
	
	
	@Override
	public ServiceResponse<List<City>> findAllByDescription(String description,Integer offset) {
		List<City> cities = cityDAO.findAllByDescription(description, offset, LIMIT_CITIES);
		return ServiceResponseFactory.create(cities);		
	}

	@Override
	public ServiceResponse<City> findByIBGECode(Integer ibgeCode) {
		List<City> cities = cityDAO.findByIBGECode(ibgeCode);
		City city = null;
		if(cities.size() > 0){
			city = cities.get(0);
		}
		return ServiceResponseFactory.create(city);
	}

}
