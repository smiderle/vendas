package br.com.vendas.persistence.repository.geo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vendas.domain.state.State;
import br.com.vendas.repository.impl.geo.StateRepositoryImpl;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-data-mongo.xml")*/
public class StateRepositoryMongoImplTest {

	@Autowired
	private StateRepositoryImpl repoImpl;

	@Ignore
	public void save(){
		List<State> states = new ArrayList<State>();
		states.add(new State("PR", "Parana"));
		states.add(new State("AL", "Alagoas"));
		states.add(new State("SP", "São Paulo"));
		repoImpl.save(new State("SP", "São Paulo"));
		repoImpl.save(new State("AL", "Alagoas"));
	}

	@Ignore
	public void findAll(){		
		Assert.assertEquals(2, repoImpl.findAll().size());
	}
}
