'use strict';

vendasApp.factory('Constants',function(){
	return {
		LOCAL_STORAGE_USER_LOGGED_KEY: 'userLoggedVendas',
		LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY :'lastBranchSelected',
		COOKIE_USER_EMAIL :'usernamevendaslim',
		
		URL_UPLOAD_PRODUCT : 'http://localhost/vendas-api/v1/product/uploadImage',
		URL_DEFAULT_NO_PICTURE : 'https://s3-sa-east-1.amazonaws.com/vendas.pictures.product/23ab5f12-fd42-4f54-a17d-775e1d63eb0a',
		
		
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