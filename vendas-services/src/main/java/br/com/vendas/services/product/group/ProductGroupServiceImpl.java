package br.com.vendas.services.product.group;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.product.group.ProductGroupDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.product.ProductGroup;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
public class ProductGroupServiceImpl implements ProductGroupService{

	@Inject
	private ProductGroupDAO productDAO;
	
	@Inject
	private UserActionService userActionService;

	@Override
	public ServiceResponse<List<ProductGroup>> findAllByBranch(
			Integer organizationID, Integer branchID, Integer offset) {		
		return ServiceResponseFactory.create(productDAO.findAllByBranche(organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT_GROUP.value()));

	}

	@Override
	public ServiceResponse<List<ProductGroup>> findAllByDescription(
			String description, Integer organizationID, Integer branchID,
			Integer offset) {
		return ServiceResponseFactory.create(productDAO.findByDescription(description, organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT_GROUP.value()));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<ProductGroup> saveOrUpdate(Integer userID, ProductGroup productGroup) {
		
		productGroup.setChangeTime(new Date());
		
		saveProductGoupAction(userID, productGroup);
		
		return ServiceResponseFactory.create(productDAO.saveOrUpdate(productGroup));
	}
	
	
	/**
	 * Salva as alções do usuário para o cadastro ou edição 
	 * @param userID
	 * @param customer
	 */
	private void saveProductGoupAction( Integer userID, ProductGroup productGroup) {
		UserAction userAction = null;
		
		if(productGroup.isExcluded()){
			userAction = UserActionHelper.createProductGroupDelete( userID, productGroup );
		} else if(productGroup.getID() == null) {
			userAction = UserActionHelper.createProductGroupSave( userID, productGroup );			
		} else {
			userAction = UserActionHelper.createProductGroupEdit( userID, productGroup );
			
		}
		
		userActionService.save( userAction );
		
	}
}