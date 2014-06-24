package br.com.vendas.repository.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.location.State;
import br.com.vendas.repository.support.ServiceResponse;
import br.com.vendas.repository.support.ServiceResponseFactory;

@Repository
public class StateRepositoryImpl implements StateRepositoryFacade{
	
	@Autowired
	private MongoOperations mongoOperation;

	public ServiceResponse<List<State>> findAll() {
		return ServiceResponseFactory.create(mongoOperation.findAll(State.class));
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
