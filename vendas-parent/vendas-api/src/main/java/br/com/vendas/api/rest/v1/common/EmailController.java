package br.com.vendas.api.rest.v1.common;

import br.com.vendas.services.email.EmailAsyncController;
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
	private EmailAsyncController emailBean;


    @RequestMapping(value="contact", method = RequestMethod.GET)
	public void contact( String name, String email, String message) {

        emailBean.contato("ladairsmiderle@gmail.com", name, email, message);

    }


    @RequestMapping(value = "contactPost", method = RequestMethod.POST)
    public void contactPost(String name, String email, String message) {

        emailBean.contato("ladairsmiderle@gmail.com", name, email, message);


    }

}
