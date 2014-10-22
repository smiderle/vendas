'use strict';

/*var INTEGER_REGEXP = /^\-?\d+$/;
vendasApp.directive('discountMaximo2', function () {
	return {
	    require: 'ngModel',
	    link: function(scope, elm, attrs, ngModel) {
	    	ngModel.$parsers.unshift(function(viewValue) {	    	
	        if (INTEGER_REGEXP.test(viewValue)) {
	          // it is valid
	          ngModel.$setValidity('discountMaximo2', false);
	          //ngModel.$setValidity('integer', false);
	          return 'BLALBALBALBALBLA';
	        } else {
	          // it is invalid, return undefined (no model update)
	        	ngModel.$setValidity('discountMaximo2', false);
	          return 'ERRROO';
	        }
	      });
	    }
	  };
});*/

/**
 * Valida se o desconto que esta sendo digitado no input esta é superior ao desconto máximo
 */
vendasApp.directive('maximumdiscountvalidator', function (ContextService) {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ngModel) {

			var branch = ContextService.getBranchLogged();
			var maximumDiscount = branch.maximumDiscount;
			
			/*
			    //For DOM -> model validation
 				ngModel.$parsers.unshift(function(value) {

				if(!isNaN(value) && value !=='') {
					var valid = parseInt(value) <= maximumDiscount;
					ngModel.$setValidity('maximumdiscountvalidator', valid);
					return valid ? value : undefined;
				} else {
					ngModel.$setValidity('maximumdiscountvalidator', true);
					return undefined;
				}	               

			});*/

			//For model -> DOM validation
			ngModel.$formatters.unshift(function(value) {				
				if(!isNaN(value) && value !=='') {
					var valid = parseFloat(value) <= maximumDiscount;
					ngModel.$setValidity('maximumdiscountvalidator', valid);
					return value;
				} else {
					ngModel.$setValidity('maximumdiscountvalidator', true);
					return undefined;
				}	               

			});
		}
	};
});


/**
 * Valida se o valor é um numero, 
 */
vendasApp.directive('number', function () {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ngModel) {



			//For DOM -> model validation
			ngModel.$parsers.unshift(function(value) {
				if(!isNaN(value)) { 						
					ngModel.$setValidity('number', true);
					return value;
				} else { 						
					ngModel.$setValidity('number', false);
					return undefined;
				}	               

			});

			//For model -> DOM validation
			ngModel.$formatters.unshift(function(value) {
				if(!isNaN(value)) {
					ngModel.$setValidity('number', true);
					return value;
				} else {					
					ngModel.$setValidity('number', false);
					return undefined;
				}	               

			});
		}
	};
});


/*vendasApp.directive('blacklist', function (){ 
	   return {
	      require: 'ngModel',
	      link: function(scope, elem, attr, ngModel) {
	          var blacklist = attr.blacklist.split(',');

	          //For DOM -> model validation
	          ngModel.$parsers.unshift(function(value) {
	             var valid = blacklist.indexOf(value) === -1;
	             ngModel.$setValidity('blacklist', valid);
	             return valid ? value : undefined;
	          });

	          //For model -> DOM validation
	          ngModel.$formatters.unshift(function(value) {
	             ngModel.$setValidity('blacklist', blacklist.indexOf(value) === -1);
	             return value;
	          });
	      }
	   };
	});*/