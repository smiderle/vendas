'use strict';

vendasApp.factory('Constants',function(){
	return {
		LOCAL_STORAGE_USER_LOGGED_KEY: 'userLoggedVendas',
		LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY :'lastBranchSelected',
		LOCAL_STORAGE_TOKEN :'vendasAccessToken',
		COOKIE_USER_EMAIL :'usernamevendaslim',
		
		URL_UPLOAD_PRODUCT : 'http://127.0.0.1/vendas-api/public/v1/upload/uploadProductPicture',
		URL_UPLOAD_CUSTOMER : 'http://127.0.0.1/vendas-api/public/v1/upload/uploadCustomerPicture',
		URL_UPLOAD_USER : 'http://127.0.0.1/vendas-api/public/v1/upload/uploadUserPicture',
		//URL_UPLOAD_PRODUCT : 'http://54.94.216.207/vendas-api/public/v1/upload/uploadProductPicture',
		//URL_UPLOAD_CUSTOMER : 'http://54.94.216.207/vendas-api/public/v1/upload/uploadCustomerPicture',
		//URL_UPLOAD_USER : 'http://54.94.216.207/vendas-api/public/v1/upload/uploadUserPicture',

		
		URL_DEFAULT_NO_PICTURE : 'https://s3-sa-east-1.amazonaws.com/vendas.pictures.product/23ab5f12-fd42-4f54-a17d-775e1d63eb0a.gif',
		
				
		MENUID_DASHBOARD : 1,
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
		MENUID_INSTALLMENT : 11,
		//Grupo de Produtos
		MENUID_PRODUCT_GROUP : 12,
		//Cliente
		MENUID_CUSTOMER : 13,
		//Novo Pedido
		MENUID_ORDER_NEW : 14,
		//Historico de pedidos
		MENUID_ORDER_HISTORY : 15,
		
		//Financeiro
		MENUID_FINANCIAL : 16,
		//Contas a receber
		MENUID_FINANCIAL_ENTRY : 17,
		//Mensagems
		MENUID_MESSAGE: 18,
		//Usuarios perfil
		MENUID_USER_PROFILE: 19,
		//MEta
		MENUID_GOAL : 20,
		//Cadastro de MEtas
		MENUID_GOAL_NEW : 21
		
		

		
		
		
	}
});