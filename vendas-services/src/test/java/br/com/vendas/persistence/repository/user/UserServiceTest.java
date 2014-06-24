package br.com.vendas.persistence.repository.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.vendas.domain.user.User;
import br.com.vendas.services.facade.user.UserServiceFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-data-mongo.xml")
public class UserServiceTest {
	
	@Autowired
	private UserServiceFacade userService;
	
	@Test
	public void save(){
		User user = new User();
		user.setActive(true);
		user.setName("Ladair User");
		userService.save(user);
	}

}
