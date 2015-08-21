package br.com.vendas.services.application;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.converter.LicenseConverter;
import br.com.vendas.dao.application.LicenseDAO;
import br.com.vendas.domain.application.License;
import br.com.vendas.dto.LicenseDTO;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class LicenseServiceImpl implements LicenseService {

	@Inject
	private LicenseDAO licenseDAO;

	private final LicenseConverter licenseConverter;

	public LicenseServiceImpl() {

		licenseConverter = new LicenseConverter();

	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<License> save( License device ) {

		License deviceSalvo = licenseDAO.save(device);

		return ServiceResponseFactory.create( deviceSalvo );

	}

	@Override
	public ServiceResponse<License> findByID(Integer id) {

		License device = licenseDAO.load(id);

		return ServiceResponseFactory.create( device );
	}

	@Override
	public LicenseDTO findByUserId(Integer userId) {

		LicenseDTO licenseDTO = new LicenseDTO();

		License license = licenseDAO.findByUserId(userId);

		licenseConverter.converteOrmParaDto(license, licenseDTO);

		return  licenseDTO;

	}

}