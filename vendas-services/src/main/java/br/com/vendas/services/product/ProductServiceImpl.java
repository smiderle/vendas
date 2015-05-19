package br.com.vendas.services.product;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.product.ProductDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.product.Product;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductDAO productDAO;

	@Inject
	private UserActionService userActionService;

	@Override
	public ServiceResponse<List<Product>> findAllByBranche(Integer organizationID,
			Integer branchID, Integer offset) {
		return ServiceResponseFactory.create(productDAO.findAllByBranch(organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT.value()));
	}

	@Override
	public ServiceResponse<List<Product>> findByDescriptionOrProductID(
			String filter, Integer organizationID, Integer branchID, Integer offset, Integer limit) {

		List<Product> products = productDAO.findByDescriptionOrProductID(filter, filter, filter, organizationID, branchID, offset, getLimit(limit));

		return ServiceResponseFactory.create(products);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Product> saveOrUpdate(Integer userID, Product product) {
		product.setChangeTime(new Date());

		saveProductAction(userID, product);

		return ServiceResponseFactory.create(productDAO.saveOrUpdate(product));
	}

	/**
	 * Salva as alções do usuário para o cadastro ou edição de um produto
	 * @param userID
	 * @param customer
	 */
	private void saveProductAction( Integer userID, Product product) {
		UserAction userAction = null;

		if(product.isExcluded()){
			userAction = UserActionHelper.createProductDelete( userID, product );
		} else if(product.getID() == null) {
			userAction = UserActionHelper.createProductSave( userID, product );
		} else {
			userAction = UserActionHelper.createProductEdit( userID, product );

		}

		userActionService.save( userAction );

	}

	private Integer getLimit(Integer limit) {
		Integer defaultLimit = LimitQuery.LIMIT_PRODUCT.value();
		if(limit != null && limit < LimitQuery.LIMIT_PRODUCT.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}

	@Override
	public void saveOrUpdate(List<Product> products) {
		productDAO.save(products);
	}

	@Override
	public ServiceResponse<List<Product>> findAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		return ServiceResponseFactory.create(productDAO.findAllByChangeGreaterThan(new Date(date), organizationID, offset, LimitQuery.LIMIT_SYNC_INIT_LOAD.value()));
	}

	@Override
	@Transactional(readOnly=false)
	public ServiceResponse<List<Product>> save(List<Product> products) {
		for (Product product : products) {
			product.setID(null);
			product.setChangeTime(new Date());
		}

		List<Product> productsSaved = productDAO.save(products);

		return ServiceResponseFactory.create( productsSaved );

	}
}
