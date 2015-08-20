/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	'use strict';	

	vendasApp
	.controller('LoginController',['$scope', '$window','$rootScope', 'LocalStorageService', 'UserService', 'Constants','UtilityService', 'Utility', 'OauthClientService',
	                             function LoginController($scope, $window,$rootScope, LocalStorageService, UserService, Constants,UtilityService, Utility, OauthClientService) {
		
		$scope.init = function(){
			
			//alert('Iniciando');
			
			
		};
		
		
		$scope.login = function(){
			
			LocalStorageService.removeItem(Constants.LOCAL_STORAGE_USER_LOGGED_KEY);
			LocalStorageService.removeItem(Constants.LOCAL_STORAGE_TOKEN);
			
			var cToken = OauthClientService.createToken($scope.email);
			
			cToken.then(function(toReturn){
				
				//Garante que o token esteja no rootScope antes do primeiro serviço rest seja invocado
				//$rootScope.accessToken = toReturn.value;
				
				//OauthClientService.setAccessToken( toReturn.value );
				OauthClientService.setAccessToken( toReturn );
				
				//var email = UtilityService.getCookie(Constants.COOKIE_USER_EMAIL);
				var aUser = UserService.findUserByEmail($scope.email);
				
				returnUser(aUser, function(){
					
					$window.location.href = 'http://127.0.0.1/vendas-web/app/index.html#/dashboard';
					//$window.location.href = 'http://127.0.0.1/vendas-web/app/index.html';
					//$window.location.href = 'http://54.94.216.207/vendas-web/app/index.html';
				});
				
			});
			
			
		};
		
		
		
		
		function returnUser(base, callback) {
			if (callback && typeof (callback) === "function") {
				base.then(function(toReturn){
					var user = toReturn.value;							

					UserService.addUserAccess(user.userID);				
					LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_USER_LOGGED_KEY,user);
					var lastBranch = LocalStorageService.getFromLocalStorageDecrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY);
					
					if(!lastBranch){
						
						var i;
						for(i in user.userBranches){
							if(user.userBranches[i].enable){
								LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY, user.userBranches[i].branchOffice);
								break;
							}
						}
						
					} else {
						
						var branch = undefined;
						
						var i;
						
						for( i in user.userBranches  ){
							
							if( user.userBranches[i].enable && lastBranch.branchOfficeID === user.userBranches[i].branchOfficeID ){
								
								branch = user.userBranches[i].branchOffice;
								
								break;
							}
							
						}
						
						if( !branch ){
							
							var i;
							for(i in user.userBranches){
								if(user.userBranches[i].enable){
									
									branch = user.userBranches[i].branchOffice;
									break;
								}
							}
							
						}
						
						LocalStorageService.addToLocalStorageCrypt( Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY, branch );
					}
					
					
					
					callback();
				});
				
			}
		};
		
	}]);	
})();