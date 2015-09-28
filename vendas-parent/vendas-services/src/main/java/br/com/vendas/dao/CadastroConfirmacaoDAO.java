package br.com.vendas.dao;

import br.com.vendas.domain.CadastroConfirmacao;

/**
 * Created by ladairsmiderle on 21/09/2015.
 */
public interface CadastroConfirmacaoDAO extends GenericDAO<CadastroConfirmacao>{

    CadastroConfirmacao find(String email, String code);
}
