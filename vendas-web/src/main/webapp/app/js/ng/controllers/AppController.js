/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	'use strict';	

	vendasApp
	.controller('AppController',['$rootScope','$scope','$location','ContextService','$window','AuthService', 
	                             function AppController($rootScope, $scope,$location, ContextService, $window, AuthService) {
		
		/**
		 * Chamada no body do index.html. Qualquer inicialização do index pode ser feita aqui.
		 */
		$scope.init = function(){
			//Seta o usuario no $rootScope
			$rootScope.user = ContextService.getUserLogged();	

			//Filial atual
			$scope.currentBranch = ContextService.getBranchLogged();

			//Empresa logada
			$scope.organizationID  = ContextService.getOrganizationID();
			
			/**
			 * Quando o admin não autorizar a venda
			 */
			$scope.unauthorizedSale = function(){
				alert('Autorização Negada');
			};

			/**
			 * Quando o admin autorizar a venda
			 */
			$scope.authorizedResponse = function(data, authorized){
				var response = {
						roomName : data.room,
						token : data.token,
						userID: data.user.userID,
						authorized: authorized,
						admin: {
							userID : ContextService.getUserLogged().userID,
							name : ContextService.getUserLogged().name,
						}
				};
				
			};
			
			
			
			
		};
	}]);	
})();