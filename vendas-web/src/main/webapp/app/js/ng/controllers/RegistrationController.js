'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp
.controller(
		'RegistrationController',
		function RegistrationController($scope, UserService,UtilityService, $routeParams, $window, Constants) {

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
						
						UtilityService.setCookie(Constants.COOKIE_USER_EMAIL ,$scope.fields.email);
						$window.location.href = 'http://localhost/vendas-web';
					});
            	}, function errorCallback(error) {
            		new alert(error);
            	});
			};
		});
