package br.com.vendas.persistence.repository;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import br.com.vendas.domain.user.User;
import br.com.vendas.repository.impl.user.UserRepositoryImpl;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


public class UserRepositoryTest {

	private static final String LOCALHOST = "127.0.0.1";
	private static final String DB_NAME = "testa";
	private static final int MONGO_TEST_PORT = 27017;

	private UserRepositoryImpl repoImpl;

	private static Mongo mongo;

	private MongoTemplate template;


	@BeforeClass
	public static void initializeDB() throws IOException {
		mongo = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
		mongo.getDB(DB_NAME);
	}


	@Before
	public void setUp() throws Exception {
		repoImpl = new UserRepositoryImpl();
		template = new MongoTemplate(mongo, DB_NAME);
		repoImpl.setMongoOps(template);
	}

	@Test
	public void testUserRepository(){
		repoImpl.findAll();
	}
	
	@Test
	public void save(){
		repoImpl.save(new User("Ciclano", "emildociclano"));
	}

	@Ignore
	public void testSave() {
		Assert.fail("Not yet implemented");
	}

	@Ignore
	public void testFindByKey() {
		Assert.fail("Not yet implemented");
	}

}
