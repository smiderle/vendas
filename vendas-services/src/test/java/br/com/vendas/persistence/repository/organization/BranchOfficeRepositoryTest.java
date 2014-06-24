package br.com.vendas.persistence.repository.organization;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.facade.organization.OrganizationRepositoryFacade;
import br.com.vendas.services.impl.organization.BranchOfficeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-data-mongo.xml")
public class BranchOfficeRepositoryTest {
	
	

	@Autowired
	private BranchOfficeServiceImpl serviceImpl;

	@Autowired
	private OrganizationRepositoryFacade orgRepo;
	
	
	
	@Test
	public void save(){
		List<Organization> orgs = orgRepo.findAll();
		BranchOffice branchOffice = new BranchOffice();
		branchOffice.setRealName("Empresa Ladair");
		branchOffice.setOrganization(orgs.get(2));
		serviceImpl.save(branchOffice);		
	}
}
