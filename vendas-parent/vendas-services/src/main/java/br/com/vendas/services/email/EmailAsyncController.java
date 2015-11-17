package br.com.vendas.services.email;

import br.com.vendas.domain.order.Order;
import br.com.vendas.util.file.GeraPdf;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ladairsmiderle on 17/09/2015.
 */
@Service
@EnableAsync
public class EmailAsyncController {

    @Autowired
    private EmailBean emailBean;

    @Async
    public void sendWelcome(String to, String from, String usuario, String senha) {

        try {

            String text = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("welcome.html"), "UTF-8");

            text = text.replace("{email}", usuario).replace("{password}", senha);

            emailBean.naoResponda(to, "Bem Vindo!", text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Async
    public void sendConfirmacaoCadastro(String to, String chave) {

        try {

            String text = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("confirmacao.html"), "UTF-8");

            text = text.replace("{codigo}", chave);

            emailBean.naoResponda(to, "Confirmação do Cadastro", text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Async
    public void contato(String nome, String email, String message) {


        String mensagem = "Nome do contato: " + nome + "\n";
        mensagem += "Email de contato: " + email + "\n";
        mensagem += "Mensagem: " + message;


        emailBean.contato("contato@vendasup.com.br", "Contato", mensagem);


    }

    @Async
    public void sendPedidoEmail(String to, Order order) {

        GeraPdf geraPdf = new GeraPdf(order);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        geraPdf.writePdf(outputStream);

        emailBean.sendDocumento(to, "Pedido VendasUp", "application/pdf", outputStream);


    }


}
