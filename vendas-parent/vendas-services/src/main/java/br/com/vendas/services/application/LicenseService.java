package br.com.vendas.services.application;

import br.com.vendas.domain.application.License;
import br.com.vendas.dto.LicenseDTO;
import br.com.vendas.services.support.ServiceResponse;

public interface LicenseService {

	ServiceResponse<License> save (License device);

	ServiceResponse<License> findByID(Integer id);

	LicenseDTO findByUserId(Integer userId);

}
