package br.com.vendas.persistence.repository.geo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vendas.domain.location.State;
import br.com.vendas.repository.location.StateRepositoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-data-mongo.xml")
public class StateRepositoryMongoImplTest {

	@Autowired
	private StateRepositoryImpl repoImpl;

	@Test
	public void save(){
		List<State> states = new ArrayList<State>();	
	}

	@Test
	public void findAll(){		
		repoImpl.findAll();
		Assert.assertEquals(2, repoImpl.findAll());
	}
}
