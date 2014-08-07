'use strict';

vendasApp
.controller('InitLoadController',function InitLoadController($scope, $window, LocalStorageService, UserService, Constants) {
	/**
	 * É chamado no body da pagina load.html. Todo o conteudo que deve ser previamente carregado, deve ser 
	 * executado aqui.
	 */
	$scope.initLoginUser = function(){

		//Mostra a mascara		
		$scope.loading = true;
		var aUser = UserService.findUserByEmail();

		returnUser(aUser, function(){
			$scope.loading = false;
			//$window.location.href = 'http://www.vendaslim.com.br:8080/vendas-web/app/index.html';
			$window.location.href = 'http://localhost:8080/vendas-web/app/index.html';
		});
	};


	function returnUser(base, callback) {
		if (callback && typeof (callback) === "function") {
			base.then(function(toReturn){
				LocalStorageService.addToLocalStorage(Constants.LOCAL_STORAGE_USER_LOGGED_KEY,toReturn.value);
				callback();
			});
			
		}
	};
});
