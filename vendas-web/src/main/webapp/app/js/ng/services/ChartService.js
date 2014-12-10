/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){	
	'use strict';

	vendasApp.factory('ChartService', ['ContextService', 'Restangular' ,function(ContextService, Restangular){
		
		return {
			
			getChartGoalCurrentMonth : function( userID ){

				var parameters = {
						'userID' : userID
				};

				return Restangular.all("v1").all("chart").all("getChartGoalCurrentMonth").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
			getChartGoalPreviousMonth : function( userID ){

				var parameters = {
						'userID' : userID
				};

				return Restangular.all("v1").all("chart").all("getChartGoalPreviousMonth").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
			getChartSalesPreviousMonth : function( userID ){

				var parameters = {
						'userID' : userID
				};

				return Restangular.all("v1").all("chart").all("getChartSalesPreviousMonth").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
			getChartSalesCurrentMonth : function( userID ){

				var parameters = {
						'userID' : userID
				};

				return Restangular.all("v1").all("chart").all("getChartSalesCurrentMonth").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
			getChartSalesCurrentMonthByBranch : function( branchID ){

				var parameters = {
						'branchID' : branchID
				};

				return Restangular.all("v1").all("chart").all("getChartSalesCurrentMonthByBranch").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},		
			
			getChartSalesPreviousMonthByBranch : function( branchID ){

				var parameters = {
						'branchID' : branchID
				};

				return Restangular.all("v1").all("chart").all("getChartSalesPreviousMonthByBranch").getList( parameters ).then( function( result ){
					var p = {};
					p.code = result.code;
					p.value = result.value;
					p.rowCount = result.rowCount;			
					return p;
				});
			},
			
		};
	}]);
})();