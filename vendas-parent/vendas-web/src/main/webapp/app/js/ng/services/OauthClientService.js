/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';

	vendasApp.factory('OauthClientService',['ContextService', 'Restangular', 'UtilityService','Constants', function(ContextService, Restangular, UtilityService, Constants ){


		return {		

			getAccessToken: function(){
				return ContextService.getAccessToken();
			},


			setAccessToken : function( token ){

				//Hora do usuário, em que o token ira expirar.
				var expiration = new Date().getTime() + ( token.expiresIn * 1000 );
				
				var t = {
						expiration: expiration,
						expiresIn: token.expiresIn,
						value : token.value,											
				};
				
				ContextService.setAccessToken( t );
				
			},
			
			tokenInvalido : function(){
				
				var invalido = true;
				
				var token = ContextService.getAccessToken( );
				
				if( token ) {
										
					if( token.expiration > new Date().getTime() ){
					
						invalido = false;
						
					}	
					
				}
				
				return invalido;
			
				
			},
			

			createToken : function( email ){				
//				var userLoggged = ContextService.getUserLogged();
//				
//				var key;
//
//				if( userLoggged == undefined){
//					key = UtilityService.getCookie( email );
//				} else {
//					key = userLoggged.email;
//				}
//
//

				var auth = 'first=a&password='+email+'&username='+email+'&grant_type=password&client_id=vendas-api&client_secret=1234&last=1';
				return Restangular.all('oauth').all('token').post(auth,undefined, 
						{ 'Accept': 'application/json', 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'});				
			},			
			
		};
	}]);
})();