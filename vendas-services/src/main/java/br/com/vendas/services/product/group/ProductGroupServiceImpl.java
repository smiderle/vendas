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
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation=Propagation.SUPPORTS)
public class ProductGroupServiceImpl implements ProductGroupService{

	@Inject
	private ProductGroupDAO productDAO;

	@Override
	public ServiceResponse<List<ProductGroup>> findAllByBranch(
			Long organizationID, Long branchID, Integer offset) {		
		return ServiceResponseFactory.create(productDAO.findAllByBranche(organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT_GROUP.getLimit()));

	}

	@Override
	public ServiceResponse<List<ProductGroup>> findAllByDescription(
			String description, Long organizationID, Long branchID,
			Integer offset) {
		return ServiceResponseFactory.create(productDAO.findByDescription(description, organizationID, branchID, offset, LimitQuery.LIMIT_PRODUCT_GROUP.getLimit()));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<ProductGroup> saveOrUpdate(ProductGroup productGroup) {
		productGroup.setChangeTime(new Date());
		return ServiceResponseFactory.create(productDAO.saveOrUpdate(productGroup));
	}
}