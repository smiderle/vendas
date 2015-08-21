package br.com;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/META-INF/spring/services-bootstrap-context.xml")
public class Testando {

	
	@Test
	public void teste(){
		Assert.assertEquals(true,true);
	}
}
