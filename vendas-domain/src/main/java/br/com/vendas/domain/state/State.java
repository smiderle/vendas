package br.com.vendas.domain.state;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class State {
	
	private String code;
	
	private String name;
	

	public State(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
