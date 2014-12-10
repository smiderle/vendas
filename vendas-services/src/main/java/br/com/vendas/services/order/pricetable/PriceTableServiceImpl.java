package br.com.vendas.services.order.pricetable;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.pricetable.PriceTableDAO;
import br.com.vendas.domain.order.PriceTable;
import br.com.vendas.domain.product.Product;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PriceTableServiceImpl implements PriceTableService {

	@Inject
	private PriceTableDAO priceTableDAO;
	
	@Inject
	private UserActionService userActionService;
	
	@Override
	public ServiceResponse<List<PriceTable>> findAllByBranche(Integer organizationID,
			Integer branchID) {
		return ServiceResponseFactory.create(priceTableDAO.findAllByBranche(organizationID, branchID));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<PriceTable> save( Integer userID, PriceTable priceTable ) {
		priceTable.setChangeTime(new Date());
		
		savePriceTableAction(userID, priceTable);
		
		return ServiceResponseFactory.create(priceTableDAO.save(priceTable));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<PriceTable> saveOrUpdate( Integer userID, PriceTable priceTable ) {
		priceTable.setChangeTime(new Date());
		
		savePriceTableAction(userID, priceTable);
		
		return ServiceResponseFactory.create(priceTableDAO.saveOrUpdate(priceTable));
	}
	
	/**
	 * Salva as ações do usuário para o cadastro ou edição de um produto
	 * @param userID
	 * @param customer
	 */
	private void savePriceTableAction( Integer userID, PriceTable priceTable) {
		UserAction userAction = null;
		
		if(priceTable.isExcluded()){
			userAction = UserActionHelper.createPriceTableDelete( userID, priceTable );
		} else if(priceTable.getID() == null) {
			userAction = UserActionHelper.createPriceTableSave( userID, priceTable );			
		} else {
			userAction = UserActionHelper.createPriceTableEdit( userID, priceTable );
			
		}
		
		userActionService.save( userAction );
		
	}

}
