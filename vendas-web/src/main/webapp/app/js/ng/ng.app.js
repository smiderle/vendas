var vendasApp = angular.module('vendasApp', [                                             
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
                                             'app.smartui'                                                                                                                                  
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

vendasApp.config(['$routeProvider','RestangularProvider', '$provide', function($routeProvider,RestangularProvider,$provide) {
	//Define a url base url como vendas-api
	RestangularProvider.setBaseUrl('/vendas-api');
	RestangularProvider.setResponseExtractor(function(response,operation, what, url) {
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
	});


	$routeProvider
	.when('/', {
		redirectTo: '/dashboard'
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
	
	.when("/error404",{
		templateUrl : 'views/misc/error404.html'	
	})
	
	.otherwise({
		redirectTo: '/error404'
	})
	;

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

vendasApp.run(['$rootScope', 'settings', function($rootScope, settings) {
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
}])