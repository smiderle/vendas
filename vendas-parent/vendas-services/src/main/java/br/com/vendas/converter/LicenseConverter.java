package br.com.vendas.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

			licenseDTO.setId(license.getId());
			licenseDTO.setSerial(license.getSerial());
			licenseDTO.setChangeTime(license.getChangeTime());
			licenseDTO.setExpirationDate(license.getExpirationDate());
			licenseDTO.setRegistrationDate(license.getRegistrationDate());
			licenseDTO.setVersionType(license.getVersionType().ordinal());

			Calendar current = Calendar.getInstance();
			current.set(Calendar.HOUR_OF_DAY, 0);
			current.set(Calendar.MINUTE, 0);
			current.set(Calendar.SECOND, 0);
			current.set(Calendar.MILLISECOND, 0);

			GregorianCalendar expirate = new GregorianCalendar();

			expirate.setTime( license.getExpirationDate() );

			licenseDTO.setExpired( current.compareTo( expirate ) > 0 );

			UserDTO userDTO = new UserDTO();
			userDTO.setUserID( license.getUser().getUserID() );

			licenseDTO.setUser( userDTO );

		}


	}

}