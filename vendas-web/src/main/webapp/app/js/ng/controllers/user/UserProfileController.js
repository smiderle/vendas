/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	
	'use strict';
	

	vendasApp.controller('UserProfileController',
			['$scope','$location', 'UserService' ,'BranchService', 'ContextService','UtilityService','Constants', 'MessageService','UserActionService','GoalService','ChartService','AuthService',
			 function UserProfileController($scope,$location, UserService,BranchService, ContextService, UtilityService,Constants, MessageService, UserActionService, GoalService, ChartService, AuthService) {

				$scope.goalData = [];
				
				$scope.userSelected ,
				
				$scope.users = [] ,
				
				$scope.userActions ;
				
				$scope.userLogged = ContextService.getUserLogged();
				
				$scope.isAdminLogged = false;
				
				$scope.showBtnMessage = false;
				
				/**
				 * Function inicial
				 */
				$scope.init = function(){
					$scope.listUsers();	
					
					if( AuthService.isAdmin() ){
						$scope.isAdminLogged = true;
					}
					
					if( AuthService.hasAccess( Constants.MENUID_MESSAGE ) ){
						$scope.showBtnMessage = true;
					}
				};
				
				/**
				 * Lista os usuários
				 */
				$scope.listUsers = function(){
					var organizationID = ContextService.getOrganizationID();
					
					var cUsers = UserService.getAllByOrganization(organizationID, 0);
					cUsers.then(function(toReturn){
						$scope.users = toReturn.value;
						
						$scope.users.forEach(function(user){
							if(user.userID == $scope.userLogged.userID){
								$scope.setUserSelected(user);
							}
						});
						
					});
				};
				
				$scope.loadUserActions = function(user){
					if($scope.isAdminLogged){
						var cActions = $scope.loadActions = UserActionService.getUserAction(user.userID, 0, 5);
						cActions.then(function(toReturn){
							if(toReturn.code === '200'){
								$scope.userActions = toReturn.value;
							}						
						});
					}										
				};
				
				$scope.getClassCategory = function(userAction){
					var customClass = 'fa-user';
					switch( userAction.category ){

					case 'LOGIN':
						customClass = 'fa-arrow-up';
						break;
					case 'MESSAGE':
						customClass = 'fa-envelope-o';
						break;
					case 'USER':
						customClass = 'fa-user';
						break;
					case 'CUSTOMER':
						customClass = 'fa-male';
						break;
					case 'BRANCH':
						customClass = 'fa-building';
						break;
					case 'PRICE_TABLE':
						customClass = 'fa-usd';
						break;	
					
					case 'INSTALLMENT':
						customClass = 'fa-list-ol';
						break;
						
					case 'PRODUCT':
						customClass = 'fa-cube';
						break;	
						
					case 'PRODUCT_GROUP':
						customClass = 'fa-cubes';
						break;	
						
					case 'ORDER':
						customClass = 'fa-shopping-cart';
						break;
						
					case 'PAYMENT':
						customClass = 'fa-usd';
						break;
					case 'TARGETS':
						customClass = 'fa-bar-chart-o';
						break;						
					default:
						customClass = 'fa-check';
					}
					
					return customClass;

				};
				
				$scope.getClassOperation = function(userAction){
					var customClass = 'fa-file-blue';
					switch( userAction.operation ){

					case 'SAVE':
						customClass = 'bg-color-greenDark';
						break;
					case 'UPDATE':
						customClass = 'fa-file-blue';
						break;
					case 'DELETE':
						customClass = 'bg-color-red';
						break;

					default:
						customClass = 'fa-user-blue';
					}
					
					return customClass;

				};
				
				
				$scope.setUserSelected = function(user){
					
					$scope.loadUserActions(user);
					
					var cUser = $scope.loaderPictureBusy = UserService.findUserByEmail(user.email);
					//$scope.userSelected = user;
					
					cUser.then(function(toReturn){
						$scope.userSelected = toReturn.value;
						if($scope.isAdminLogged){
							$scope.onTabGoalCurrentMonth();
							$scope.onTabSalesCurrentMonth();
						}						
					});					
				};
				
				$scope.getPermissionsStr = function(){


					if($scope.userSelected && $scope.userSelected.userRoles){
						var isAdmin = false,
						isSeller = false;


						if( UserService.isAdmin($scope.userSelected.userRoles) ){
							isAdmin = true;
						}

						if( UserService.isSeller($scope.userSelected.userRoles) ){
							isSeller = true;						
						}

						return (isAdmin && isSeller) ?  'Administrador e Vendedor' : isAdmin ? 'Administrador' : 'Vendedor';
					}
				};	
				
				/**
				 * Retorna true se o usuário for administrador
				 */
				$scope.isAdmin = function(){
					return UserService.isAdmin($scope.userLogged.userRoles);
				};
				
				/**
				 * Redireciona para a pagina de edição do usuário
				 */
				$scope.userEdit = function() {
					UserService.setUserEdition($scope.userSelected);
					$location.path('/user/user-form');
				};
				
				/**
				 * Redireciona para a pagina de mensagens
				 */
				$scope.userMessage = function() {
					MessageService.setUserSelected($scope.userSelected);
					$location.path('/mensagem/mensagens');
				};
				
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
				
				$scope.legendColor = function(){
					return function(d, i) {
				    	return color[i];
				    };
				};
				
				/**
				 * Carrega as coordernadas do mes anterior
				 */
				$scope.onTabGoalPreviousMonth = function() {					
					var cCurrent = $scope.loadChartGoal = ChartService.getChartGoalPreviousMonth( $scope.userSelected.userID );
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
				 * Carrega as coordernada das metas do mes atual
				 */
				$scope.onTabGoalCurrentMonth = function() {
					var cCurrent = $scope.loadChartGoal = ChartService.getChartGoalCurrentMonth( $scope.userSelected.userID );
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
				$scope.onTabSalesCurrentMonth = function(){
					var cCurrent = $scope.loadChartSales = ChartService.getChartSalesCurrentMonth( $scope.userSelected.userID );
					cCurrent.then( function( toReturn ){
						if(toReturn.code == '200'){
							$scope.salesData = toReturn.value;
						}						
					});
				};
				
				/**
				 * Carega as coordenadas do mês anterior
				 */
				$scope.onTabSalesPreviousMonth = function() {					
					var cCurrent = $scope.loadChartSales = ChartService.getChartSalesPreviousMonth( $scope.userSelected.userID );
					cCurrent.then( function( toReturn ){
						if(toReturn.code == '200'){
							$scope.salesData = toReturn.value;
						}
					});
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
	
})();
