package br.com.vendas.services.order.installment;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.installment.InstallmentDAO;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class InstallmentServiceImpl implements InstallmentService{
		
	@Inject
	private InstallmentDAO installmentDAO;

	
	@Override
	public ServiceResponse<List<Installment>> findAllByBranche(
			Integer organizationID, Integer branchID) {
		
		return ServiceResponseFactory.create(installmentDAO.findAllByBranche(organizationID, branchID));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Installment> save(Installment installment) {
		installment.setChangeTime(new Date());
		return ServiceResponseFactory.create(installmentDAO.save(installment));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Installment> saveOrUpdate(Installment installment) {
		installment.setChangeTime(new Date());
		return ServiceResponseFactory.create(installmentDAO.saveOrUpdate(installment));
	}

}
