'use strict';

vendasApp
.controller('BranchListController',
		function BranchtionController($scope,$location, BranchService, ContextService) {
	
	/**
	 * Lista de filiais carregados
	 */
	$scope.branches = [];

	/**
	 * Linhas que irão popular a datatable das Filiais.
	 * Contém um array de Filiais, com array de colunas
	 */
	$scope.rowsDataTable = [];

	/**
	 * Lista as filiais da empresa do usuario.
	 */
	$scope.listBranches = function(){
		var organizationID = ContextService.getOrganizationID();
		var cBranches = BranchService.getAllBranchesByOrganizationID(organizationID);
		cBranches.then(function(toReturn){
			$scope.branches = toReturn.value;
			buildBranchOfficeDataTable(toReturn.value);
		});
	};	
	
	$scope.newBranch = function(){
		//Remove qualquer filial que estava na edição
		BranchService.setBranchEdition({});
		
		$location.path('/branch/branch-form');
	};
	
	$scope.editBranch = function(){
		var branchSelectedID = $( "input:checked" ).val();
		var branches = $scope.branches;
		
		var i;
		for(i in branches){
			var branche = branches[i];			
			if(branche && branche.branchOfficeID == branchSelectedID){
				BranchService.setBranchEdition(branche);
				$location.path('/branch/branch-form');
				break;
			}
		}
	};
	

	/**
	 * Cria um array de filiais, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
	 * sendo "ouvido" na directive CustomDataTable
	 */
	function buildBranchOfficeDataTable(branches){
		var branchOfficeRows = [];

		branches.forEach(function(branchOffice, index){			
			if(branchOffice && branchOffice.branchOfficeID){
				//Adiciona um array com as colunas que irão ser apresentadas no dataTable
				branchOfficeRows.push([
	                       '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+branchOffice.branchOfficeID+'"><i></i></label>',
	                       index+1,				               
	                       branchOffice.branchOfficeID,
	                       branchOffice.fancyName,
	                       branchOffice.realName,	                       
	                       branchOffice.phoneNumber
	                       ]);
			}
		});

		//Seta os usuarios 
		$scope.rowsDataTable = branchOfficeRows;
	};
	
});
