package br.com.vendas.dao.application;

import br.com.vendas.dao.GenericDAO;
import br.com.vendas.domain.application.License;

public interface LicenseDAO extends GenericDAO<License> {

	License findByUserId(Integer userId);

}
