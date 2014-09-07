'use strict';

vendasApp
.controller('InitLoadController',function InitLoadController($scope, $window, LocalStorageService, UserService, Constants,UtilityService, Utility) {
	
	/**
	 * É chamado no body da pagina load.html. Todo o conteudo que deve ser previamente carregado, deve ser 
	 * executado aqui.
	 */
	$scope.initLoginUser = function(){		
		//Mostra a mascara		
		$scope.loading = true;
		var email = UtilityService.getCookie(Constants.COOKIE_USER_EMAIL);
		var aUser = UserService.findUserByEmail(email);

		returnUser(aUser, function(){
			$scope.loading = false;
			//$window.location.href = 'http://www.vendaslim.com.br/vendas-web/app/index.html';
			$window.location.href = 'http://localhost/vendas-web/app/index.html';
		});
	};


	function returnUser(base, callback) {
		if (callback && typeof (callback) === "function") {
			base.then(function(toReturn){
				var user = toReturn.value;
				console.log(user);				
				//console.log(toReturn.value.userBranches[0].branchOffice);
				UserService.addUserAccess(user.userID);				
				LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_USER_LOGGED_KEY,user);
				var lastBranch = LocalStorageService.getFromLocalStorageDecrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY);
				if(!lastBranch){
					var i
					for(i in user.userBranches){
						if(user.userBranches[i].enable){
							LocalStorageService.addToLocalStorageCrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY, user.userBranches[i].branchOffice);
							break;
						}
					}
				}
				callback();
			});
			
		}
	};
});
