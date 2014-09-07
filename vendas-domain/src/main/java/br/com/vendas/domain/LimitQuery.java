package br.com.vendas.domain;

/**
 * 
 * @author Ladair C. Smiderle Junior 07/09/2014 - 18:23:04
 * Todos os limits de consulta de registros são declarados nesse enum
 *
 */
public enum LimitQuery {
	
	/**
	 * Limite de registros das consultas dos grupos de produtos
	 */
	LIMIT_PRODUCT_GROUP(100),
	
	/**
	 * Limite de registros das consultas de tabela de preço 
	 */
	LIMIT_PRICE_TABLE(100),
	
	/**
	 * Limite de registros das consultas de usuarios
	 */
	LIMIT_USER(100);
	
	private Integer limit;
	
	private LimitQuery(Integer limit) {
		this.limit = limit;
	}

	public Integer getLimit() {
		return limit;
	}	
}