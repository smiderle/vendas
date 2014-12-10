package br.com.vendas.domain.chart;

import java.util.List;

public class LineChart {
	
	/**
	 * Legenda da linha do chart. A variavel precisa ter esse nome, porque o chart espera um parametro com o nome de key
	 */
	private String key;
	
	/**
	 * Valores da linha. Sempre sera um array de tamanho 2, sendo que o primeiro indice é a coordenada horizonta e o segundo é o vertical 
	 */
	private List<Object[]> values;

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Object[]> getValues() {
		return values;
	}

	public void setValues(List<Object[]> values) {
		this.values = values;
	}
}