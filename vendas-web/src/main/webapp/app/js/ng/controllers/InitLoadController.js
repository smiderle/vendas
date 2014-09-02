'use strict';

vendasApp
.controller('InitLoadController',function InitLoadController($scope, $window, LocalStorageService, UserService, Constants,UtilityService) {
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
				
				//console.log(toReturn.value.userBranches[0].branchOffice);
				UserService.addUserAccess(toReturn.value.userID);
				LocalStorageService.addToLocalStorage(Constants.LOCAL_STORAGE_USER_LOGGED_KEY,toReturn.value);
				callback();
			});
			
		}
	};
});
