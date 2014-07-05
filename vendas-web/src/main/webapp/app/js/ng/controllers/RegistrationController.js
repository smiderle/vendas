'use strict';

vendasApp
.controller(
		'RegistrationController',
		function RegistrationController($scope, RegistrationService, $routeParams, $window) {

			/**
			 * Registra um novo usuario.
			 */
			$scope.registeringNewUser = function(){

				var aUser = RegistrationService.registeringNewUser($scope.fields);
				
				aUser.then(function(newUser){
					
					/**
					 * Redireciona para a pagina o portal, após o cadastro
					 */					
					RegistrationService.redirectNewUser(newUser.email, newUser.password).then(function(){
						$window.location.href = 'http://localhost:8080/vendas-web';
					});
            	}, function errorCallback(error) {
            		new alert(error);
            	});
			};
		});
