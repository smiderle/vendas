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
				
				aUser.then(function(toReturn){
					
					if(toReturn.code === '200'){
						/**
						 * Seta o email do usuairo no cookie e redireciona para a pagina o portal, após o cadastro
						 */					
						UserService.redirectNewUser(toReturn.email, toReturn.password).then(function( toReturn ){						
								UtilityService.setCookie(Constants.COOKIE_USER_EMAIL ,$scope.fields.email);
								$window.location.href = 'http://127.0.0.1/vendas-web';
								//$window.location.href = 'http://54.94.216.207/vendas-web';												
						});
					} else {
						alert( toReturn.message );	
					}	
					
					
            	}, function errorCallback(error) {
            		new alert(error);
            	});
			};
		});
