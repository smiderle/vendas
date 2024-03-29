package br.com.vendas.services.order.installment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.order.installment.InstallmentDAO;
import br.com.vendas.domain.LimitQuery;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.domain.user.UserAction;
import br.com.vendas.helper.UserActionHelper;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;
import br.com.vendas.services.user.UserActionService;


@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class InstallmentServiceImpl implements InstallmentService{

	@Inject
	private InstallmentDAO installmentDAO;

	@Inject
	private UserActionService userActionService;


	@Override
	public ServiceResponse<List<Installment>> findAllByBranche(
			Integer organizationID, Integer branchID) {

		return ServiceResponseFactory.create(installmentDAO.findAllByBranche(organizationID, branchID));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Installment> save(Integer userID, Installment installment) {
		installment.setChangeTime(new Date());

		saveInstallmentAction(userID, installment);

		return ServiceResponseFactory.create(installmentDAO.save(installment));
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<Installment> saveOrUpdate(Integer userID, Installment installment) {
		installment.setChangeTime(new Date());

		saveInstallmentAction(userID, installment);

		return ServiceResponseFactory.create(installmentDAO.saveOrUpdate(installment));
	}

	/**
	 * Salva as alções do usuário para o cadastro ou edição de um novo cliente
	 * @param userID
	 */
	private void saveInstallmentAction( Integer userID, Installment installment ){
		UserAction userAction = null;

		if(installment.isExcluded()){
			userAction = UserActionHelper.createInstallmentDelete(userID, installment);
		} else if(installment.getID() == null) {
			userAction = UserActionHelper.createInstallmentSave(userID, installment);
		} else {
			userAction = UserActionHelper.createInstallmentEdit(userID, installment);

		}

		userActionService.save(userAction);

	}

	@Override
	public void save(List<Installment> installments) {
		installmentDAO.save( installments );
	}

	@Override
	public ServiceResponse<List<Installment>> findAllByChangeGreaterThan(Long date, Integer organizationID, Integer offset) {
		return ServiceResponseFactory.create( installmentDAO.findAllByChangeGreaterThan(new Date(date), organizationID, offset, LimitQuery.LIMIT_SYNC_INIT_LOAD.value()));
	}


	@Override
	@Transactional(readOnly=false)
	public ServiceResponse<List<Installment>> saveList(List<Installment> installments) {

		List<Installment> customersSaved = new ArrayList<>();

		for (Installment installment : installments) {

			installment.setID(null);
			installment.setChangeTime( new Date() );

			Installment customerSaved = installmentDAO.saveOrUpdate( installment );

			customersSaved.add( customerSaved );

		}

		return ServiceResponseFactory.create(customersSaved);
	}


	@Override
	@Transactional(readOnly=false)
	public ServiceResponse<List<Installment>> updateList(List<Installment> installments) {

		List<Installment> customersSaved = new ArrayList<>();

		for (Installment installment : installments) {

			installment.setChangeTime( new Date() );

			Installment customerSaved = installmentDAO.saveOrUpdate( installment );

			customersSaved.add( customerSaved );

		}

		return ServiceResponseFactory.create(customersSaved);
	}


}
