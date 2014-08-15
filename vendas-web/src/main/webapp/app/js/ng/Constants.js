'use strict';

vendasApp.factory('Constants',function(){
	return {
		LOCAL_STORAGE_USER_LOGGED_KEY: 'userLoggedVendas',
		COOKIE_USER_EMAIL :'usernamevendaslim',
		
		//Produto
		MENUID_PRODUCT : 5,
		//Cadastro
		MENUID_REGISTRATION : 4,
		//Pedido
		MENUID_ORDER : 6,
		//Empresa
		MENUID_ORGANIZATION : 7,
		//Usuario
		MENUID_USER : 8,
		//Configuração
		MENUID_CONFIG : 9,		
		//Configuração
		MENUID_PRICE_TABLE : 10,
		//Parcelamento
		MENUID_INSTALLMENT : 11
		
	}
});