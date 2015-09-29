/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';

	vendasApp.controller('GoalFormController',
			['$scope','ContextService','UtilityService','GoalService',
			 function GoalFormController($scope, ContextService, UtilityService, GoalService) {

				$scope.userTargets = [];
				
				var userLogged = ContextService.getUserLogged(),
					organizationID =ContextService.getOrganizationID(),
					branch = ContextService.getBranchLogged();
				
				


				$scope.init = function( ){
					var cTarget = $scope.busyLoader =  GoalService.getAllByBranch(organizationID, branch.branchOfficeID );
					cTarget.then(function( toReturn ){
						if( toReturn.code == '200' ){
							
							$scope.userTargets = toReturn.value;
							console.log(toReturn);
						}
					});
				};
				
				
				$scope.save = function(){
					var targetsWrapper = {};
					
					targetsWrapper.userTargetsDTO = $scope.userTargets;
					
					var cSave = $scope.busyLoader = GoalService.save( targetsWrapper, userLogged.userID );
					cSave.then(function(toReturn){
						if(toReturn.code == '200'){
							$scope.userTargets = toReturn.value;
							UtilityService.showAlertSucess('Sucesso.', 'Metas salvas com sucesso!!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}	
					});
					
				};
				
				
				$scope.getMonthDescription = function(month){
					var monthStr = '';
					switch(month){
					case 1:
						monthStr = 'Janeiro';
						break;
					case 2:
						monthStr = 'Fevereiro';
						break;
					case 3:
						monthStr = 'Março';
						break;
					case 4:
						monthStr = 'Abril';
						break;
					case 5:
						monthStr = 'Maio';
						break;
					case 6:
						monthStr = 'Junho';
						break;
					case 7:
						monthStr = 'Julho';
						break;
					case 8:
						monthStr = 'Agosto';
						break;
					case 9:
						monthStr = 'Setembro';
						break;
					case 10:
						monthStr = 'Outubro';
						break;
					case 11:
						monthStr = 'Novembro';
						break;
					case 12:
						monthStr = 'Dezembro';
						break;						
					}
					
					return monthStr;
					
				};
			}]);
})();
