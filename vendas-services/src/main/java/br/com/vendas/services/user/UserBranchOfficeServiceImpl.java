package br.com.vendas.services.user;

import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.user.UserBranchOfficeDAO;
import br.com.vendas.domain.user.UserBranchOffice;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class UserBranchOfficeServiceImpl implements UserBranchOfficeService{

	@Inject
	private UserBranchOfficeDAO dao;
	
	@Override
	public ServiceResponse<List<UserBranchOffice>> findAllByBranchOffice(
			Integer organizationID, Integer branchOfficeID) {
		List<UserBranchOffice> users = dao.findAllByBranchOffice(organizationID, branchOfficeID);
		return ServiceResponseFactory.create(users);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<UserBranchOffice> save(
			UserBranchOffice userBranchOffice) {
		userBranchOffice.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(dao.save(userBranchOffice));
	}

}