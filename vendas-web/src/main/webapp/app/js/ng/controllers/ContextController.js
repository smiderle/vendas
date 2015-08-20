'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp
.controller('ContextController',function AppController($scope,ContextService,LocalStorageService, Constants, $location) {
	
	//Filial atual
	$scope.currentBranch;
	
	//Empresa logada
	$scope.organizationID  = ContextService.getOrganizationID();
	
	//Usuario logado
	var userLogged = ContextService.getUserLogged();
		
	if(userLogged){
		console.log('Chamando no ContextController');
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
	
	$scope.logout = function(){
		
		LocalStorageService.removeItem(Constants.LOCAL_STORAGE_USER_LOGGED_KEY);
		LocalStorageService.removeItem(Constants.LOCAL_STORAGE_TOKEN);
		window.location = 'http://127.0.0.1/vendas-web/app/login.html';
		
	};
	
	/**
	 * Atualiza $scope.userBranches com as empresas do usuário.
	 * É invocado quando o usuário faz alguma alteração no cadastro da empresa, então é emitido um evento
	 * para atualizar as empresas que estão no escopo, para ficar com os dados atualizados
	 */
	$scope.$on('vendasApp:updateBranches', function(){
		console.log('CHAMOU UPDATE BRANCHES');
		var usersBranchTmp = [];
		userLogged.userBranches.forEach(function(userBranch){
			if(userBranch.enable){
				usersBranchTmp.push(userBranch.branchOffice);
			}
		});

		$scope.userBranches = usersBranchTmp;
	});
	
	
	 
	 
});
