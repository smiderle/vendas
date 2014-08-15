'use strict';

vendasApp
.controller('OrganizationController',
		function OrganizationController($scope,$location, OrganizationService, ContextService, UtilityService) {
	
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
		var cBranches = OrganizationService.getAllBranchesByOrganizationID(organizationID);
		cBranches.then(function(toReturn){
			$scope.branches = toReturn.value;
			buildBranchOfficeDataTable(toReturn.value);
		});
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
	                       branchOffice.realName,
	                       branchOffice.fancyName,
	                       branchOffice.phoneNumber
	                       ]);
			}
		});

		//Seta os usuarios 
		$scope.rowsDataTable = branchOfficeRows;
	};
	
	/**
	 * Função que inicializa os dados no form da empresa.
	 */
	$scope.initBranchOfficeForm = function(){
		
	};
	
	$scope.newBranchOffice = function(){
		$location.path('/register/organization');
	};
	
	/**
	 * Salva a filial
	 */
	$scope.save = function(branchOffice){
		
		if($('#branchOffice-registration-form').valid()){
			//Esconde a mensagem de erro caso esteja visivel
			$('#alertOrganizationInputsInvalids').hide();
			branchOffice.organization = {
					organizationID: ContextService.getOrganizationID()
			};
			console.log(branchOffice);
			
			var cBranchOffice = OrganizationService.save(branchOffice);
			cBranchOffice.then(function(toReturn){
				if(toReturn.code == '200'){
					UtilityService.showAlertSucess('Sucesso.', 'Empresa salva com sucesso!!');
				} else {
					UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
				}
			});
		} else {
			//Mostra a mensagem de erro caso algo algum campo esteja invalido.
			$('#alertOrganizationInputsInvalids').show();			
		}
	};
	
	
	
	
});
