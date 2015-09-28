package br.com.vendas.dao;

import br.com.vendas.domain.CadastroConfirmacao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by ladairsmiderle on 21/09/2015.
 */
@Repository
public class CadastroConfirmacaoDAOImpl  extends ResourceDAO<CadastroConfirmacao> implements CadastroConfirmacaoDAO {
    @Override
    public CadastroConfirmacao find(String email, String code) {


        Session session = getSession();
        Criteria criteria = session.createCriteria(CadastroConfirmacao.class);
        criteria.add(Restrictions.eq("email", email))
                .add(Restrictions.eq("chave", code));


        return (CadastroConfirmacao) criteria.uniqueResult();

    }
}
