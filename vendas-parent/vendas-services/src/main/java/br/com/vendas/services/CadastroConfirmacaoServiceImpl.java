package br.com.vendas.services;

import br.com.vendas.dao.CadastroConfirmacaoDAO;
import br.com.vendas.domain.CadastroConfirmacao;
import br.com.vendas.exception.VendasException;
import br.com.vendas.services.email.EmailAsyncController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by ladairsmiderle on 21/09/2015.
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CadastroConfirmacaoServiceImpl implements CadastroConfirmacaoService {

    @Inject
    private CadastroConfirmacaoDAO cadastroConfirmacaoDAO;


    @Autowired
    private EmailAsyncController emailAsyncController;

    @Override
    public void save(CadastroConfirmacao cadastroConfirmacao) {

        cadastroConfirmacaoDAO.saveOrUpdate(cadastroConfirmacao);

        emailAsyncController.sendConfirmacaoCadastro(cadastroConfirmacao.getEmail(), cadastroConfirmacao.getChave());

    }

    @Override
    public CadastroConfirmacao cadastroConfirmacao(String email, String codigo) throws VendasException {

        CadastroConfirmacao cadastroConfirmacao = cadastroConfirmacaoDAO.find(email, codigo);


        if(cadastroConfirmacao == null){
            throw  new VendasException("Código inválido.");
        }

        return cadastroConfirmacao;

    }
}
