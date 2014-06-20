package br.com.vendas.repository.impl.geo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.state.State;
import br.com.vendas.repository.facade.geo.StateRepositoryFacade;

@Repository
public class StateRepositoryImpl implements StateRepositoryFacade{
	
	@Autowired
	private MongoOperations mongoOperation;

	public List<State> findAll() {
		return mongoOperation.findAll(State.class);
	}

	public void save(State state) {
		this.mongoOperation.save(state);
	}

	public void save(List<State> states) {		
		this.mongoOperation.save(states);
	}

	/**
	 * Seta o mongoOperations para teste.
	 * @param mongoOps
	 */
	 public void setMongoOps(MongoOperations mongoOps) {
	        this.mongoOperation = mongoOps;
	 }

	


}
