package br.com.vendas.services.location;

import java.rmi.RemoteException;

import javax.inject.Inject;

import org.apache.axis.AxisFault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import CEPService_pkg.CEPServiceBindingStub;
import br.com.vendas.domain.location.Address;
import br.com.vendas.domain.location.City;
import br.com.vendas.exception.VendasRuntimeException;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PostalCodeServiceImpl implements PostalCodeService{
	
	@Inject
	private CityService cityService;

	
	@Override
	public ServiceResponse<Address> getStreet(String zipCode) {
		Address address = new Address();
		try {
			CEPServiceBindingStub stub = new CEPServiceBindingStub();
			String retorno = stub.obterLogradouroAuth(zipCode, "ladair", "N76tJL4");
			if(!"".equals(retorno.trim())){
				String[] split = retorno.split(",");
				
				if(split.length == 1){
					throw new VendasRuntimeException(split[0]);
				} else if(split.length == 5){					
					String street = split[0];
					String ibgeCode = split[4];
					
					//Se o retorno do nome da rua iniciar com CEP, é porque foi retornado "CEP Município", pois é um cep de municipio
					if(!street.startsWith("CEP")){						
						if(street.indexOf("-") > -1){
							street = street.substring(0, street.indexOf("-"));
						}
						address.setStreet(street);
					}
					
					address.setDistrict(split[1]);
					
					if(ibgeCode.length() > 6){
						//Descarta o ultimo digito do IBGE retornado pelo serviço
						ibgeCode = ibgeCode.substring(0, 7);
					}
					ServiceResponse<City> city = cityService.findByIBGECode(Integer.parseInt(ibgeCode.trim()));
					address.setCity(city.getValue());	
					address.setZipCode(zipCode);
				}			
			}
			
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ServiceResponseFactory.create(address);
	}
	
}
