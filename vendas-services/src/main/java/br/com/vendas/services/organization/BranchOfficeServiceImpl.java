package br.com.vendas.services.organization;

import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendas.dao.organization.BranchOfficeDAO;
import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.exception.RegistrationException;
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BranchOfficeServiceImpl implements BranchOfficeService{

	@Inject
	private BranchOfficeDAO branchOfficeDAO;

	@Override
	public ServiceResponse<List<BranchOffice>> findAllByOrganizationID(
			Integer organizationID) {
		List<BranchOffice> branchs = branchOfficeDAO.findAllByOrganizationID(organizationID);		
		return ServiceResponseFactory.create(branchs);
	}

	@Override
	public ServiceResponse<BranchOffice> findByOrganizationIDAndBranchOfficeID(
			Integer organizationID, Integer branchOfficeID) {
		BranchOffice branchOffice = branchOfficeDAO.findByOrganizationIDAndBranchOfficeID(organizationID, branchOfficeID);
		return ServiceResponseFactory.create(branchOffice);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<BranchOffice> save(BranchOffice branchOffice) throws RegistrationException {
		ServiceResponse<BranchOffice> branchOfficeSaved = findByOrganizationIDAndBranchOfficeID(branchOffice.getOrganization().getOrganizationID(), branchOffice.getBranchOfficeID());
		if(branchOfficeSaved.getRowCount() > 0){
			throw new RegistrationException("81","O código "+ branchOfficeSaved.getValue().getBranchOfficeID() +" já esta cadastrado para a empresa " + branchOfficeSaved.getValue().getFancyName());
		}
		
		branchOffice.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(branchOfficeDAO.save(branchOffice));
	}
	
	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<BranchOffice> saveOrUpdate(BranchOffice branchOffice) {
		branchOffice.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(branchOfficeDAO.saveOrUpdate(branchOffice));
	}

	@Override
	public ServiceResponse<Long> findNextBranchOfficeIDByOrganization(Organization organization) {
		Long maxID = branchOfficeDAO.findMaxBranchOfficeIDByOrganization(organization);
		return ServiceResponseFactory.create(maxID+1);
	}

}
