package br.com.vendas.api.rest.v1.common;

import br.com.vendas.services.email.EmailBean;
import br.com.vendas.support.ApiResponse;
import br.com.vendas.support.ResponseBuilder;
import br.com.vendas.support.VendasExceptionWapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="/public/v1/email")
@Controller
@EnableAsync
public class EmailController {


    private static final Logger LOG = Logger.getLogger( EmailController.class );


	@Autowired
	private EmailBean emailBean;


	@RequestMapping(value="sendEmail", method = RequestMethod.GET)
	public void sendEmail(String email, String password){
		emailBean.sendEmail("ladairsmiderle@gmail.com", "ladairsmiderle@gmail.com", "Titulo", "Bom dia. Estou encaminhando esse email como teste.");
		System.out.println("Concluido ");

	}

    @RequestMapping(value="contact", method = RequestMethod.GET)
	public void contact( String name, String email, String message) {

        String mensagem = "Nome do contato: " + name +"\n";
        mensagem += "Email de contato: "+ email + "\n";
        mensagem += "Mensagem: "+ message;

        emailBean.sendEmail("ladairsmiderle@gmail.com", "ladairsmiderle@gmail.com", "Contato VendasUP", mensagem);

    }


    @RequestMapping(value = "contactPost", method = RequestMethod.POST)
    public @ResponseBody ApiResponse contactPost(String name, String email, String message) {

        try {

            String mensagem = "Nome do contato: " + name + "\n";
            mensagem += "Email de contato: " + email + "\n";
            mensagem += "Mensagem: " + message;

            emailBean.sendEmail("ladairsmiderle@gmail.com", "ladairsmiderle@gmail.com", "Contato VendasUP", mensagem);

            return ResponseBuilder.build(new Integer(1));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return ResponseBuilder.build(new VendasExceptionWapper(e));
        }


    }

}
