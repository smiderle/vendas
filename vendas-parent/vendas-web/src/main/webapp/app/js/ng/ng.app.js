var vendasApp = angular.module('vendasApp', [ 
                                             'angucomplete-alt',
                                             'ngRoute',
                                             //'ngAnimate', // this is buggy, jarviswidget will not work with ngAnimate :(
                                             'restangular',
                                             'ui.bootstrap',
                                             'plunker',                                             
                                             'app.controllers',
                                             'app.demoControllers',
                                             'app.main',
                                             'app.navigation',
                                             'app.localize',
                                             'app.activity',
                                             'app.smartui',
                                             'cgBusy',
                                             'angularMoment',
                                             'nvd3ChartDirectives'
                                             ]);

/**
 * Restangular service que sera utilizado quando precisar chamar o vendas-web
 * Note que o baseurl para esse restangular é vendas-web e não vendas-api, que é o padrão
 */
vendasApp.factory('VendasWebRestangular', function(Restangular) {
	return Restangular.withConfig(function(RestangularConfigurer) {
		RestangularConfigurer.setBaseUrl('/vendas-web');
	});
});

/**
 * Restangular que mapeado para o NodeJS
 */
vendasApp.factory('RestangularNode', function(Restangular) {
	return Restangular.withConfig(function(RestangularConfigurer) {
		//RestangularConfigurer.setBaseUrl('http://54.94.216.207:3000/vendas-api');
		RestangularConfigurer.setBaseUrl('http://127.0.0.1:3000/vendas-api');
	});
});

/**
 * Interceptrador, para redirecionar para a pagina de login, caso a o token seja inválido
 */
vendasApp.factory('OauthInterceptor',['$rootScope', '$q', '$injector','$location', function( $rootScope, $q, $injector, $location ){
	return function( promise ) {
		return promise.then(function(response) {
			return response;
		}, function (response) {
			if ( response.status === 401 ) {        	   
				var deferred = $q.defer();
				window.location = 'http://127.0.0.1/vendas-web/j_spring_security_logout';        	   
				//window.location = 'http://54.94.216.207/vendas-web/j_spring_security_logout';
				deferred.reject();
			} else if( response.status === 400 ){
				var deferred = $q.defer();
				window.location = 'http://127.0.0.1/vendas-web/app/login.html?error=400';
				alert(response.data.message);
				deferred.reject();

			}    
			return $q.reject(response); 
		});
	};
}]);

vendasApp.config(['$routeProvider','RestangularProvider', '$provide', '$httpProvider', function( $routeProvider,RestangularProvider,$provide,$httpProvider ) {
	//Define a url base url como vendas-api
	RestangularProvider.setBaseUrl('/vendas-api');
	RestangularProvider.setResponseExtractor(function(response,operation, what, url) {	
		if( response.payload ){
			var newResponse = response.payload;
			newResponse.message = response.message;
			newResponse.code = response.code;
			if (angular.isArray(newResponse)) {
				angular.forEach(newResponse, function(value, key) {
					if (newResponse[key] != undefined) {
						newResponse[key].originalElement = angular.copy(value);
					}
				});
			} else {
				if (newResponse != undefined) {
					newResponse.originalElement = angular.copy(newResponse);
				}
			}
			return newResponse;
		} else {
			return response;
		}


	});

	// Iterceptor para tokens oauth 
	$httpProvider.responseInterceptors.push('OauthInterceptor');

	$routeProvider
	.when('/', {
		redirectTo: '/dashboard'
	})

	.when("/accessdenied",{
		templateUrl : 'views/accessdenied.html'
	})

	.when("/dashboard",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/dashboard.html';
			}
		},
		controller : 'DashboardController'
	})


	.when("/user/user-form",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/user/user-form.html';
			}
		},
		controller : 'UserFormController'		
	})
	.when("/user/user-list",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/user/user-list.html';
			}
		},
		controller : 'UserListController'		
	})	

	.when("/branch/branch-list",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/branch/branch-list.html';
			}
		},
		controller : 'BranchListController'		
	})
	.when("/branch/branch-form",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/branch/branch-form.html';
			}
		},
		controller : 'BranchFormController'		
	})




	.when("/pedido/lista-parcelamento",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/installment-list.html';
			}
		},
		controller : 'InstallmentListController'		
	})


	.when("/pedido/cadastro-parcelamento",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/installment-form.html';
			}
		},
		controller : 'InstallmentFormController'		
	})	

	.when("/pedido/lista-tabela-de-preco",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/pricetable-list.html';
			}
		},
		controller : 'PriceTableListController'		
	})

	.when("/pedido/cadastro-tabela-de-preco",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/pricetable-form.html';
			}
		},
		controller : 'PriceTableFormController'		
	})

	.when("/produto/grupo/lista-categoria-produto",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/product/group/product-group-list.html';
			}
		},
		controller : 'ProductGroupListController'		
	})

	.when("/produto/grupo/cadastro-categoria-produto",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/product/group/product-group-form.html';
			}
		},
		controller : 'ProductGroupFormController'		
	})

	.when("/produto/lista-produto",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/product/product-list.html';
			}
		},
		controller : 'ProductListController'		
	})

	.when("/produto/cadastro-produto",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/product/product-form.html';
			}
		},
		controller : 'ProductFormController'		
	})

	.when("/cliente/cadastro-cliente",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/customer/customer-form.html';
			}
		},
		controller : 'CustomerFormController'		
	})

	.when("/cliente/lista-cliente",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/customer/customer-list.html';
			}
		},
		controller : 'CustomerListController'		
	})

	.when("/pedido/cadastro-pedido",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/order-form.html';
			}
		},
		controller : 'OrderFormController'		
	})

	.when("/pedido/lista-pedido",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/order-list.html';
			}
		},
		controller : 'OrderListController'		
	})

	.when("/pedido/detalhes-pedido",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/order/order-details.html';
			}
		},
		controller : 'OrderDetailsController'		
	})

	.when("/financeiro/lista-contas-receber",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/payment/payment-list.html';
			}
		},
		controller : 'PaymentListController'		
	})

	.when("/financeiro/detalhes-contas-receber",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/payment/payment-details.html';
			}
		},
		controller : 'PaymentDetailsController'		
	})


	.when("/usuarios",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/user/user-profile.html';
			}
		},
		controller : 'UserProfileController'		
	})

	.when("/metas/cadastro-metas",{
		templateUrl : function($routeParams) {
			if(false){
				return  'views/accessdenied.html';
			} else {
				return  'views/targets/sales-targets.html';
			}
		},
		controller : 'GoalFormController'		
	})

	.when("/error404",{
		templateUrl : 'views/misc/error404.html'	
	})

	.otherwise({
		redirectTo: '/error404'
	});

	// with this, you can use $log('Message') same as $log.info('Message');
	$provide.decorator('$log', function($delegate) {
		// create a new function to be returned below as the $log service (instead of the $delegate)
		function logger() {
			// if $log fn is called directly, default to "info" message
			logger.info.apply(logger, arguments);
		}
		// add all the $log props into our new logger fn
		angular.extend(logger, $delegate);
		return logger;
	});

}]);

/**
 * Intercepta todas as requests para inserir o token no header.
 */
vendasApp.run(['$rootScope', 'settings','$injector','OauthClientService', function($rootScope, settings, $injector, OauthClientService) {
	$injector.get("$http").defaults.transformRequest = function(data, headersGetter,a, b, c) {
		
		
		var index = window.location.href.indexOf('login');
		var index2 = window.location.href.indexOf('index');
		
		if( OauthClientService.tokenInvalido() && window.location.href.indexOf('login') == -1 ) {
			
			window.location = 'http://127.0.0.1/vendas-web/app/login.html?error=400';
			
		} else {
		
			if ( OauthClientService.getAccessToken() ) headersGetter()['Authorization'] = "Bearer "+ OauthClientService.getAccessToken().value;
			if (data) {
				return angular.toJson(data);
			}
			
		}
		
		
	};


	settings.currentLang = settings.languages[0]; // en

	$rootScope.hasRole = function(role) {

		if ($rootScope.user === undefined) {
			return false;
		}

		if ($rootScope.user.roles[role] === undefined) {
			return false;
		}

		return $rootScope.user.roles[role];
	};
}]);