package br.com.vendas.services.email;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ladairsmiderle on 17/09/2015.
 */
@Service
@EnableAsync
public class EmailAsyncController {

    @Autowired
    private EmailBean emailBean;

    @Async
    public void sendWelcome( String to, String from,  String usuario, String senha ){

        try {

            String text = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("welcome.html"));

            text = text.replace( "{email}", usuario ).replace("{password}",senha);

            emailBean.bemVindo(to, from, text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
