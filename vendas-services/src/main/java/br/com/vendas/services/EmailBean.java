package br.com.vendas.services;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


//http://bhtecnonerd.blogspot.com.br/2014/12/configuracao-de-servico-de-email-no.html

@Service
public class EmailBean  {

	private static final Logger LOG = Logger.getLogger(EmailBean.class);

	@Resource(mappedName = "java:jboss/mail/VendasUp") //Nome do Recurso que criamos no Wildfly
	private Session mailSession; //Objecto que vai reprensentar uma sessão de email


	public EmailBean() {
	}

	//@Asynchronous //Metodo Assíncrono para que a aplicação continue normalmente sem ficar bloqueada até que o email seja enviado
    @Async
	public void sendEmail(String to, String from, String subject, String content) {

		try {
			//Criação de uma mensagem simples
			Message message = new MimeMessage(mailSession);
			//Cabeçalho do Email
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			message.setSubject(subject);
			//Corpo do email
			message.setText(content);

			//Envio da mensagem
			Transport.send(message);
            System.out.println("Email enviado");
		} catch (MessagingException e) {
			LOG.error( e.getMessage() );
			e.printStackTrace();
		}
	}
}
