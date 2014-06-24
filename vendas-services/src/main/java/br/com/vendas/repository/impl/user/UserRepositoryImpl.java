package br.com.vendas.repository.impl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.vendas.domain.organization.BranchOffice;
import br.com.vendas.domain.user.User;
import br.com.vendas.repository.facade.user.UserRepositoryFacade;

@Repository
public class UserRepositoryImpl implements UserRepositoryFacade {
	
	@Autowired
	MongoOperations mongoOperation;
	
	public List<User> findByBranchOffice(BranchOffice branchOffice){
		Query query = new Query().addCriteria(Criteria.where("branchOffice").is(branchOffice));
		return mongoOperation.find(query ,User.class);
		
	}
		
	public void save(User user) {
		this.mongoOperation.save(user);
	}

	@Override
	public User findByEmail(String email) {
		Query query = new Query()
			.addCriteria(Criteria.where("email").is(email));
		return mongoOperation.findOne(query, User.class);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		Query query = new Query()
			.addCriteria(Criteria.where("email").is(email).and("password").is(password));
		return mongoOperation.findOne(query, User.class);
	}

}
