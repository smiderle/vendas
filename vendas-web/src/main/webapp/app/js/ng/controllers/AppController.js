'use strict';

vendasApp
.controller('AppController',function AppController($rootScope, $scope, UserService, UtilityService, $window) {
	
	/**
	 * Chamada no body do index.html. Qualquer inicialização do index pode ser feita aqui.
	 */
	$scope.init = function(){
		//Seta o usuario no $rootScope
		$rootScope.user = UserService.getUser();
	};
});
