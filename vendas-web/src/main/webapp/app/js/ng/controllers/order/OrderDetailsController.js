'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('OrderDetailsController',
		['$scope','$rootScope','$route','$location','OrderService','UtilityService','ContextService','PriceTableService','CustomerService','CalcUtil','FormsPaymentService','InstallmentService','DateUtil',
		 function OrderDetailsController($scope,$rootScope,$route,$location,OrderService, UtilityService, ContextService, PriceTableService, CustomerService, CalcUtil, FormsPaymentService, InstallmentService, DateUtil) {
			
			$scope.branch = ContextService.getBranchLogged();

			$scope.order = {};
			
			$scope.init = function(){
				$scope.order = OrderService.getOrderEdition();
				
				OrderService.getByID($scope.order.id).then(function(toRetun){
					$scope.order = toRetun.value;
					$scope.formPaymentSelected = FormsPaymentService.getByID($scope.order.formPayment);
					console.log($scope.order);
				});
				
				
			};	
			
			/**
			 * Formata o valor com 2 casas decimais e virgula.
			 */
			$scope.formatFloat = function(val){

				if(isNaN(val) || val <= 0){
					return '0,00';
				} else {
					return numberFormat(val, 2, ',', '.'); 
				}			
			};
			
			$scope.parseFloat = function(val) {
			    return isNaN(parseFloat(val)) ? 0: parseFloat(val);
			};
			
			$scope.formatDate = function(dateStr){
				return DateUtil.format(dateStr,'/');
			};
			
		}]);