package br.com.vendas.services.product.promotion;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.product.promotion.ProductPromotionDAO;
import br.com.vendas.domain.product.ProductPromotion;
import br.com.vendas.exception.ApplicationException;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ProductPromotionServiceImpl implements ProductPromotionService {

	@Inject
	private ProductPromotionDAO productPromotionDAO;

	@Override
	public ServiceResponse<List<ProductPromotion>> findAllByProductID(Integer productID) {
		//Retorna dois meses.
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH,-2);
		
		return ServiceResponseFactory.create(productPromotionDAO.findAllByProductID(productID, date.getTime()));
	}
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<ProductPromotion> saveOrUpdate(ProductPromotion productPromotion) throws ApplicationException {
		
		productPromotion.setChangeTime(new Date());
		//Se for um novo produto, faz a validação
		/*if(productPromotion.getID() == null) {
			validatePromotionDate(productPromotion);
		}*/
		validatePromotionDate(productPromotion);
		return ServiceResponseFactory.create(productPromotionDAO.saveOrUpdate(productPromotion));
	}
	

	/**
	 * Valida se não existe uma promoção (Com excelçao da própria promoção) cadastrada para o intervalo de data. 
	 * @param productPromotion
	 * @throws RegistrationException
	 */
	private void validatePromotionDate(ProductPromotion productPromotion) throws RegistrationException {
		Integer id = productPromotion.getID() != null ? productPromotion.getID() : 0;
		
		List<ProductPromotion> products = productPromotionDAO.findByByInitalDateAndFinalDate(productPromotion.getProductID(), productPromotion.getFinalDate(),productPromotion.getInitialDate(), id);
		
		if(products != null && products.size() > 0) {
			throw new RegistrationException("Já existe uma promoção cadastrada para o período informado");
		}
		
		if(productPromotion.getFinalDate().before(productPromotion.getInitialDate())){
			throw new RegistrationException("A data do final da promoção não pode ser antes do inicio da promoção");
		}

		
		if(productPromotion.getFinalDate().before(new Date()) && !DateUtils.isSameDay(productPromotion.getFinalDate(), new Date())){
			throw new RegistrationException("A data do final da promoção deve ser maior ou igual a data de hoje");
		}
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<ProductPromotion> remove(ProductPromotion productPromotion) throws ApplicationException {
		productPromotion.setChangeTime(new Date());
		return ServiceResponseFactory.create(productPromotionDAO.saveOrUpdate(productPromotion));
	}

}
