package br.com.vendas.services.order.pricetable;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.pricetable.PriceTableDAO;
import br.com.vendas.domain.order.PriceTable;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PriceTableServiceImpl implements PriceTableService {

	@Inject
	private PriceTableDAO priceTableDAO;
	
	@Override
	public ServiceResponse<List<PriceTable>> findAllByBranche(Long organizationID,
			Long branchID) {
		return ServiceResponseFactory.create(priceTableDAO.findAllByBranche(organizationID, branchID));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<PriceTable> save(PriceTable priceTable) {
		priceTable.setChangeTime(new Date());
		return ServiceResponseFactory.create(priceTableDAO.save(priceTable));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<PriceTable> saveOrUpdate(PriceTable priceTable) {
		priceTable.setChangeTime(new Date());
		return ServiceResponseFactory.create(priceTableDAO.saveOrUpdate(priceTable));
	}

}
