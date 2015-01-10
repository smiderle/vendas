'use strict';

vendasApp.controller('DashboardController',['$scope','ContextService','ChartService','AuthService','ReportService',
        function DashboardController($scope, ContextService, ChartService, AuthService, ReportService ) {
	
	var userLogged = ContextService.getUserLogged(),
		branch = ContextService.getBranchLogged(),
		organizationID  = ContextService.getOrganizationID();
		
	$scope.isAdminLogged = false;
	
	$scope.init = function(){
		if(AuthService.isAdmin()){
			$scope.isAdminLogged = true;
		}
		
		$scope.onTabSalesCurrentMonth();
		$scope.onTabGoalCurrentMonth();
		$scope.onTabSalesBranchCurrentMonth();
		getCurrentCountSalesByBranch();
	};
	
	/**
	 * Carrega as coordenadas de vendas do mes atual
	 */
	$scope.onTabSalesCurrentMonth = function(){
		var cCurrent = $scope.loadChartSales = ChartService.getChartSalesCurrentMonth( userLogged.userID );
		cCurrent.then( function( toReturn ){
			console.log(toReturn.value);
			$scope.salesData = toReturn.value;
		});
	};
	
	/**
	 * Carega as coordenadas do mês anterior
	 */
	$scope.onTabSalesPreviousMonth = function() {					
		var cCurrent = $scope.loadChartSales = ChartService.getChartSalesPreviousMonth( userLogged.userID );
		cCurrent.then( function( toReturn ){
			$scope.salesData = toReturn.value;
		});
	};
	
	/**
	 * Carrega as coordernada das metas do mes atual
	 */
	$scope.onTabGoalCurrentMonth = function() {
		var cCurrent = $scope.loadChartGoal = ChartService.getChartGoalCurrentMonth( userLogged.userID );
		cCurrent.then( function( toReturn ){
			if(toReturn.code == '200'){
				var atingido = toReturn.value[0];
				var meta = toReturn.value[1];
				/*
				 * Foi feito esse ajuste, porque quando no dia um, se os valores ma meta e atingido forem iguais, o gráfico
				 * não esta mostrando o tooltip, então é setado o valor de 0.01 no primeiro dia da meta. 
				 */
				if( atingido.values[0] != undefined && atingido.values[0][1] === meta.values[0][1]){
					meta.values[0][1] = 0.01;
				}
				
				$scope.goalData = toReturn.value;
				calcularDiferencaMeta();
			}
			
		});
	};
	
	/**
	 * Carrega as coordernadas do mes anterior
	 */
	$scope.onTabGoalPreviousMonth = function() {					
		var cCurrent = $scope.loadChartGoal = ChartService.getChartGoalPreviousMonth( userLogged.userID );
		cCurrent.then( function( toReturn ){
			if(toReturn.code == '200'){
				var atingido = toReturn.value[0];
				var meta = toReturn.value[1];
				/*
				 * Foi feito esse ajuste, porque quando no dia um, se os valores ma meta e atingido forem iguais, o gráfico
				 * não esta mostrando o tooltip, então é setado o valor de 0.01 no primeiro dia da meta. 
				 */
				if( atingido.values[0][1] === meta.values[0][1]){
					meta.values[0][1] = 0.01;
				}
				
				$scope.goalData = toReturn.value;
				calcularDiferencaMeta();
			}
		});
	};
	
	/**
	 * Carrega as coordenadas de vendas do mes atual
	 */
	$scope.onTabSalesBranchCurrentMonth = function(){
		var cCurrent = $scope.loadChartBranchSales = ChartService.getChartSalesCurrentMonthByBranch( branch.branchOfficeID );
		cCurrent.then( function( toReturn ){
			if(toReturn.code == '200'){
				$scope.salesDataBranch = toReturn.value;
				$scope.calcTotalValueSalesBranch( $scope.salesDataBranch[0].values );
			}			
		});
	};
	
	/**
	 * Carega as coordenadas do mês anterior
	 */
	$scope.onTabSalesBranchPreviousMonth = function() {
		var cCurrent = $scope.loadChartBranchSales = ChartService.getChartSalesPreviousMonthByBranch( branch.branchOfficeID );
		cCurrent.then( function( toReturn ){
			if(toReturn.code == '200'){
				$scope.salesDataBranch = toReturn.value;
			}			
		});
	};
	
	$scope.calcTotalValueSalesBranch = function( salesDataBranch ){
		var total = 0;
		salesDataBranch.forEach(function(sales){
			total += sales[1];
			
		});
		$scope.totalValueSalesBrahcn = numberFormat( total, 2, ',', '.' );
	};

	function getCurrentCountSalesByBranch(){
		var cTotal = ReportService.getCurrentCountSalesByBranch(organizationID , branch.branchOfficeID);
		cTotal.then( function(toReturn){
			if(toReturn.code == '200' ){
				$scope.countSalesByBranch = toReturn.value;
			}						
		});
	}
	
	/**
	 * Configurações do gráfico
	 */
	$scope.toolTipGoalContentFunction = function(){
		return function(key, x, y, e, graph) {			
	    	return  'Meta de vendas' +
	        	'<h1>' + key + '</h1>' +
	            '<p> R$ ' +  y + ' até o dia ' + x + '</p>';
		};
	};
	
	$scope.toolTipSalesContentFunction = function(){
		return function(key, x, y, e, graph) {
	    	return  'Total de venda' +
	        	'<h1> R$' + y + '</h1>' +
	            '<p> Vendido R$ ' +  y + ' no dia ' + x + '</p>';
		};
	};
	
	
	var color = ['#296191', '#356E35'];
	
	$scope.colorFunction = function() {
		return function(d, i) {
	    	return color[i];
	    };
	};
	
	$scope.colorFunction2 = function() {
		return function(d, i) {
	    	return color[1];
	    };
	};
	
	$scope.legendColor = function(){
		return function(d, i) {
	    	return color[i];
	    };
	};
	
	function calcularDiferencaMeta(){
		var arrayAtingido = $scope.goalData[1],
			arrayMeta = $scope.goalData[0],
			totalValue = 0,
			goalValue = 0,
			remaining = 0;
		
		
		
		if(arrayAtingido && arrayAtingido.values.length > 0){
			goalValue = arrayAtingido.values[arrayAtingido.values.length -1][1];
		} else {
			goalValue = 0.0;
		}
		
		if(arrayMeta && arrayMeta.values.length > 0){
			totalValue = arrayMeta.values[arrayMeta.values.length -1][1];
		} else {
			totalValue = 0.0;
		}
		
		if(goalValue > 0 ){
			remaining =  goalValue - totalValue; 
		} else {
			remaining = 0.0;
		}
		
		$scope.totalValue = numberFormat(totalValue, 2, ',', '.'); 
		$scope.goalValue = numberFormat(goalValue, 2, ',', '.');
		
		if(remaining > 0){
			$scope.remaining = numberFormat(remaining, 2, ',', '.');
		} else {
			$scope.remaining = '0,0';
		}
		

	}
	
}]);
