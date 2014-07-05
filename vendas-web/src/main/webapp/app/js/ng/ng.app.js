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
		
		.when("/empresa",{
				templateUrl : function($routeParams) {
					if(true){
						return  'views/accessdenied.html';
					} else {
						return  'views/empresa.html';
					}
				},
				controller : 'StateController'		
		})

		/* For DEMO purposes, we are loading our views dynamically by passing arguments to the location url */

		// A bug in smartwidget with angular (routes not reloading). 
		// We need to reload these pages everytime so widget would work
		// The trick is to add "/" at the end of the view.
		// http://stackoverflow.com/a/17588833
		.when('/:page', { // we can enable ngAnimate and implement the fix here, but it's a bit laggy
			templateUrl: function($routeParams) {
				console.log($routeParams.page);
				return 'views/'+ $routeParams.page +'.html';
			},
			controller: 'PageViewController'
		})
		.when('/:page/:child*', {
			templateUrl: function($routeParams) {
				console.log($routeParams.page);
				return 'views/'+ $routeParams.page + '/' + $routeParams.child + '.html';
			},
			controller: 'PageViewController'
		})
		.otherwise({
			redirectTo: '/dashboard'
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