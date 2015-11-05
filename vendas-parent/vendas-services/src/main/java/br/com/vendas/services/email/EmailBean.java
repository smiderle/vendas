package br.com.vendas.services.email;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


//http://bhtecnonerd.blogspot.com.br/2014/12/configuracao-de-servico-de-email-no.html

@Service
public class EmailBean {

    private static final Logger LOG = Logger.getLogger(EmailBean.class);

    //Objecto que vai reprensentar uma sessão de email
    @Resource(mappedName = "java:jboss/mail/VendasUp")
    private Session mailSession;

    //@Resource(mappedName = "java:jboss/mail/NaoResponda")
    //private Session mailNaoResponda;

    @Resource(mappedName = "java:jboss/mail/Contato")
    private Session mailContato;


    public EmailBean() {
    }


    @Async
    public void naoResponda(String to, String titulo, String conteudo) {

        try {
            //Criação de uma mensagem simples
            Message message = new MimeMessage(mailContato);
            //Cabeçalho do Email
            message.setFrom(new InternetAddress(to, "VendasUp"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(titulo);

            javax.mail.Address[] addresses = {
                    new InternetAddress("contato@vendasup.com.br")
            };

            message.setReplyTo(addresses);

            message.setContent(conteudo, "text/html");


            //Envio da mensagem
            Transport.send(message);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }


    @Async
    public void contato(String to, String titulo, String conteudo) {

        try {
            //Criação de uma mensagem simples
            Message message = new MimeMessage(mailContato);
            //Cabeçalho do Email
            message.setFrom(new InternetAddress(to, "Contato"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(titulo);

            message.setContent(conteudo, "text/html");


            //Envio da mensagem
            Transport.send(message);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
