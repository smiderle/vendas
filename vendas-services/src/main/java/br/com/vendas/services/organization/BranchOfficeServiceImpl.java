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
import br.com.vendas.services.support.ServiceResponse;
import br.com.vendas.services.support.ServiceResponseFactory;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BranchOfficeServiceImpl implements BranchOfficeService{

	@Inject
	private BranchOfficeDAO branchOfficeDAO;

	@Override
	public ServiceResponse<List<BranchOffice>> findAllByOrganizationID(
			Long organizationID) {
		List<BranchOffice> branchs = branchOfficeDAO.findAllByOrganizationID(organizationID);		
		return ServiceResponseFactory.create(branchs);
	}

	@Override
	public ServiceResponse<BranchOffice> findByOrganizationIDAndBranchOfficeID(
			Long organizationID, Long branchOfficeID) {
		BranchOffice branchOffice = branchOfficeDAO.findByOrganizationIDAndBranchOfficeID(organizationID, branchOfficeID);
		return ServiceResponseFactory.create(branchOffice);
	}

	@Transactional(readOnly=false)
	@Override
	public ServiceResponse<BranchOffice> save(BranchOffice branchOffice) {
		branchOffice.setChangeTime(new GregorianCalendar());
		return ServiceResponseFactory.create(branchOfficeDAO.save(branchOffice));
	}

	@Override
	public ServiceResponse<Long> findNextBranchOfficeIDByOrganization(Organization organization) {
		Long maxID = branchOfficeDAO.findMaxBranchOfficeIDByOrganization(organization);
		return ServiceResponseFactory.create(maxID+1);
	}

}
