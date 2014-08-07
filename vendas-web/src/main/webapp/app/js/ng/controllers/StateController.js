'use strict';

vendasApp
		.controller(
				'StateController',
				function StateController($scope, StateService, $routeParams, UserService) {
					console.log('Chamando da empresa');
					
					
					StateService.findAll().get().then(
						function(object) {						
							console.log(object.value);
							$scope.states = object.value;						
						});

					$scope.ShowAlert = function(){
						alert('Mostrando Mensagem');
					};
				});
