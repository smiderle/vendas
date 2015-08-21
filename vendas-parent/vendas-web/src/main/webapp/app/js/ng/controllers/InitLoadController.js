'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp
.controller('InitLoadController',function InitLoadController($scope, $window,$rootScope, LocalStorageService, UserService, Constants,UtilityService, Utility, OauthClientService) {
	
	/**
	 * É chamado no body da pagina load.html. Todo o conteudo que deve ser previamente carregado, deve ser 
	 * executado aqui.
	 */
	$scope.initLoginUser = function(){	
		//Mostra a mascara		
		$scope.loading = true;
		
		//Gera o token.
		var cToken = OauthClientService.createToken();
		cToken.then(function(toReturn){
			
			//Garante que o token esteja no rootScope antes do primeiro serviço rest seja invocado
			$rootScope.accessToken = toReturn.value;
			
			OauthClientService.setAccessToken( toReturn.value );			
			var email = UtilityService.getCookie(Constants.COOKIE_USER_EMAIL);
			var aUser = UserService.findUserByEmail(email);
			
			returnUser(aUser, function(){
				$scope.loading = false;
				$window.location.href = 'http://127.0.0.1/vendas-web/app/index.html';
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
});
