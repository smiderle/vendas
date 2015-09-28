package br.com.vendas.services;

import br.com.vendas.domain.CadastroConfirmacao;
import br.com.vendas.exception.VendasException;

/**
 * Created by ladairsmiderle on 21/09/2015.
 */
public interface CadastroConfirmacaoService {

    void save(CadastroConfirmacao cadastroConfirmacao);

    CadastroConfirmacao cadastroConfirmacao(String email, String codigo) throws VendasException;
}
