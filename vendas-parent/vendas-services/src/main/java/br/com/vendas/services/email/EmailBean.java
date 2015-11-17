package br.com.vendas.services.email;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.vendas.core.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



import java.io.ByteArrayOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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


    @Async
    public void sendDocumento(String to, String titulo, String conteudo) {

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

    public void sendDocumento(String to, String titulo,String mimeType, ByteArrayOutputStream outputStream) {
        LOG.info("Inciando envio do email para " + to);

        String content = "Segue em anexo o pedido gerado pelo aplicativo VendasUp."; //this will be the text of the email


        try {
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);

            //now write the PDF content to the output stream
            byte[] bytes = outputStream.toByteArray();

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, mimeType);
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName( "vendasup_pedido_"+ DateUtil.getCurrentDateUUID() +".pdf");

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(mailContato);
            mimeMessage.setFrom(new InternetAddress(to, "Pedido"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            mimeMessage.setSubject(titulo);

            mimeMessage.setContent(mimeMultipart);

            //send off the email
            Transport.send(mimeMessage);

            LOG.info("Finalizando o  envio do email para " + to);

        } catch(Exception ex) {
            LOG.error(ex);
        }
    }


}
