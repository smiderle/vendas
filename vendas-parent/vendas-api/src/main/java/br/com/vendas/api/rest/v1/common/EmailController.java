package br.com.vendas.api.rest.v1.common;

import javax.inject.Inject;

import br.com.vendas.services.EmailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/public/v1/email")
@Controller
@EnableAsync
public class EmailController {


	@Autowired
	private EmailBean emailBean;


	@RequestMapping(value="sendEmail", method = RequestMethod.GET)
	public void sendEmail(String email, String password){
		emailBean.sendEmail("ladairsmiderle@gmail.com", "ladairsmiderle@gmail.com", "Titulo", "Bom dia. Estou encaminhando esse email como teste.");
		System.out.println("Concluido ");

	}

}
