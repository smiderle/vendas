package br.com.vendas.persistence.repository.organization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vendas.domain.organization.Organization;
import br.com.vendas.repository.facade.organization.OrganizationRepositoryFacade;
import br.com.vendas.services.facade.location.OrganizationServiceFacade;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-data-mongo.xml")
public class OrganizationRepositoryTest {

	@Autowired
	private OrganizationServiceFacade service;
	
	@Test
	public void save(){
		Organization org = new Organization();
		org.setOrganizationID(4L);
		service.save(org);
		System.out.println("");
	}
}
