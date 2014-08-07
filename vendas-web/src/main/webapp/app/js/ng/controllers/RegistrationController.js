'use strict';

vendasApp
.controller(
		'RegistrationController',
		function RegistrationController($scope, UserService,UtilityService, $routeParams, $window) {

			/**
			 * Registra um novo usuario.
			 */
			$scope.registeringNewUser = function(){

				var aUser = UserService.registeringNewUser($scope.fields);
				
				aUser.then(function(newUser){
					
					/**
					 * Seta o email do usuairo no cookie e redireciona para a pagina o portal, após o cadastro
					 */					
					UserService.redirectNewUser(newUser.email, newUser.password).then(function(){
						
						UtilityService.setCookie("usernamevendaslim",$scope.fields.email);
						$window.location.href = 'http://localhost:8080/vendas-web';
					});
            	}, function errorCallback(error) {
            		new alert(error);
            	});
			};
		});
