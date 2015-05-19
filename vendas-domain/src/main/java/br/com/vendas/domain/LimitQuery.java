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
	LIMIT_PRODUCT_GROUP(60),
	
	/**
	 * Limite de registros das consultas de tabela de preço 
	 */
	LIMIT_PRICE_TABLE(60),
	
	/**
	 * Limite de registros das consultas de usuarios
	 */
	LIMIT_USER(60),
	
	/**
	 * Limite de registros das consultas de produto
	 */
	LIMIT_PRODUCT(60),
	
	/**
	 * Limite de registros das consultas de clientes
	 */
	LIMIT_CUSTOMER(60),
	
	/**
	 * Limite de registros das consultas do pedido
	 */
	LIMIT_ORDER(60),
	
	/**
	 * Limite de registros das consultas de parcelas
	 */
	LIMIT_ORDER_PAYMENT(60),
	
	/**
	 * Ações do usuário
	 */
	LIMIT_USER_ACTIONS(20),
	
	/**
	 * Limite de registros a serem sincronizados na carga inicial
	 * 
	 */
	
	LIMIT_SYNC_INIT_LOAD(250);
	
	
	private final Integer limit;
	
	private LimitQuery(Integer limit) {
		this.limit = limit;
	}

	public Integer value() {
		return limit;
	}	
}