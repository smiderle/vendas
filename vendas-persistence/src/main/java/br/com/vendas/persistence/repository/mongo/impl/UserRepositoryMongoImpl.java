package br.com.vendas.persistence.repository.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.user.User;
import br.com.vendas.persistence.repository.UserRepository;

@Repository
public class UserRepositoryMongoImpl implements UserRepository {
	
	@Autowired
	MongoOperations mongoOperation;
	
	public List<User> findAll(){
		return mongoOperation.findAll(User.class);
	}
	

	 /**
	  * Salva o usuario.
	  */
	public void save(User user) {
		this.mongoOperation.save(user);
	}
	
	/**
	 * Seta o mongoOperations para teste.
	 * @param mongoOps
	 */
	 public void setMongoOps(MongoOperations mongoOps) {
	        this.mongoOperation = mongoOps;
	 }

}
