package br.com.vendas.domain.user;

import java.io.Serializable;

public class UserTest  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private EmpresaTest empresa;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public EmpresaTest getEmpresa() {
		return empresa;
	}
	public void setEmpresa(EmpresaTest empresa) {
		this.empresa = empresa;
	}
}