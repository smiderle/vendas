package br.com.vendas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by ladairsmiderle on 21/09/2015.
 */
@Entity
@Table(name="CADASTRO_CONFIRMACAO")
public class CadastroConfirmacao extends Domain {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "chave")
    private String chave;

    @Column(name = "ativado")
    private boolean ativado;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
