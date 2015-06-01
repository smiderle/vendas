package br.com.vendas.converter;

import java.util.Date;

import br.com.vendas.domain.application.License;
import br.com.vendas.dto.LicenseDTO;
import br.com.vendas.pojo.UserDTO;

public class LicenseConverter implements Converter<LicenseDTO, License>{

	@Override
	public void converteDtoParaOrm(LicenseDTO licenseDTO, License license) {

		license.setId( licenseDTO.getId() );
		license.setSerial(licenseDTO.getSerial());



	}

	@Override
	public void converteOrmParaDto(License license, LicenseDTO licenseDTO) {

		if(license != null){

			licenseDTO.setId( license.getId() );
			licenseDTO.setSerial(license.getSerial());
			licenseDTO.setChangeTime(license.getChangeTime());
			licenseDTO.setExpirationDate(license.getExpirationDate());
			licenseDTO.setRegistrationDate(license.getRegistrationDate());
			licenseDTO.setVersionType(license.getVersionType().ordinal());

			licenseDTO.setExpired( license.getExpirationDate().before(new Date()) );

			UserDTO userDTO = new UserDTO();
			userDTO.setUserID( license.getUser().getUserID() );

			licenseDTO.setUser( userDTO );

		}


	}

}