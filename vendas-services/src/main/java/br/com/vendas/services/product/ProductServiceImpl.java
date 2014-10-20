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
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductDAO productDAO;

	@Override
	public ServiceResponse<List<Product>> findAllByBranche(Integer organizationID,
			Integer branchID, Integer offset) {
		return ServiceResponseFactory.create(productDAO.findAllByBranche(organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT.value()));
	}

	@Override
	public ServiceResponse<List<Product>> findByDescriptionOrProductID(
			String filter, Integer organizationID, Integer branchID, Integer offset, Integer limit) {
		
		List<Product> products = productDAO.findByDescriptionOrProductID(filter, filter, filter, organizationID, branchID, offset, getLimit(limit));
		
		return ServiceResponseFactory.create(products);		
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Product> saveOrUpdate(Product product) {
		product.setChangeTime(new Date());
		return ServiceResponseFactory.create(productDAO.saveOrUpdate(product));
	}
	
	private Integer getLimit(Integer limit) {
		Integer defaultLimit = LimitQuery.LIMIT_PRODUCT.value();
		if(limit != null && limit < LimitQuery.LIMIT_PRODUCT.value() && limit > 0) {
			defaultLimit = limit;
		}

		return defaultLimit;
	}
}
