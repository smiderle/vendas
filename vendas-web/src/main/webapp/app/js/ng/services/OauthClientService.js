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
				ContextService.setAccessToken( token );
			},
			
			createToken : function(){				
				var userLoggged = ContextService.getUserLogged();
				var key;
				
				if( userLoggged == undefined){
					key = UtilityService.getCookie(Constants.COOKIE_USER_EMAIL);
				} else {
					key = userLoggged.email;
				}
				
				
				
				var auth = 'first=a&password='+key+'&username='+key+'&grant_type=password&client_id=vendas-api&client_secret=1234&last=1';
				return Restangular.all('oauth').all('token').post(auth,undefined, 
						{ 'Accept': 'application/json', 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'});				
				}
		};
	}]);
})();