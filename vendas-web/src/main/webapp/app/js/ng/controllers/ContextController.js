'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp
.controller('ContextController',function AppController($scope, ContextService,LocalStorageService, Constants, $location) {
	
	//Filial atual
	$scope.currentBranch;
	
	//Usuario logado
	var userLogged = ContextService.getUserLogged();
		
	if(userLogged){			
		var usersBranchTmp = [];
		userLogged.userBranches.forEach(function(userBranch){
			if(userBranch.enable){
				usersBranchTmp.push(userBranch.branchOffice);
			}
		});
			
		$scope.userBranches = usersBranchTmp;
		//Ultima filial selecionada que esta gravada no localStorage
		var lastBranchSelected  = LocalStorageService.getFromLocalStorageDecrypt(Constants.LOCAL_STORAGE_LAST_BRANCH_SELECTED_KEY);
		
		var lastBranchSelectedEnable;
		var i;
		for(i in userLogged.userBranches){
			var userBranch = userLogged.userBranches[i];
			if(userBranch.enable){
				/*
				 * Ira setar qualquer empresa que esteja abilitada, caso não exista ultima filial salva no local storage.
				 * No proximo if, ira setar a ultima empresa que foi selecionada
				 */ 
				
				lastBranchSelectedEnable = userBranch.branchOffice;
				if(lastBranchSelected && userBranch.branchOffice.branchOfficeID == lastBranchSelected.branchOfficeID){
					lastBranchSelectedEnable = userBranch.branchOffice;
					break;
				}
			}
		}			
		$scope.currentBranch = lastBranchSelectedEnable;
		
	}
	
	$scope.setBranch = function(branch){
		ContextService.setBranchLogged(branch);			
		$scope.currentBranch = branch;
		$location.path("/");
	};			

	
});
