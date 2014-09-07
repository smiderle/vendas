'use strict';

vendasApp.controller('ProductGroupListController',
		['$scope','$location','ProductGroupService' ,'ContextService', 
        function ProductGroupListController($scope, $location, ProductGroupService, ContextService) {
	
	/**
	 * Lista de grupos carregados
	 */
	$scope.groups = [];
	
	/**
	 * Linhas que irão popular a datatable dos grupos.
	 * 
	 */
	$scope.rowsDataTable = [];
		

	/**
	 * Chamado quando clicado no botão Editar.
	 * Percorre os usuarios que foram armazenados no $scope.groups, e compara o groupID, para encontrar o usuario selecionado. 
	 */
	$scope.editGroup = function(){
		
		//Recupera o usuario selecionado no datatable.
		var groupSelectedID = $( "input:checked" ).val();
		var groups = $scope.groups;
		
		var i;
		for(i in groups){
			var group = groups[i];			
			if(group && group.id == groupSelectedID ){				
				ProductGroupService.setProductGroupEdition(group);
				$location.path('/produto/grupo/cadastro-categoria-produto');
				break;
			}
		}
	};


	/**
	 * Lista os grupos da filial.
	 */
	$scope.listGroups = function(){
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;
		
		var offset = 0;
		var cGroups_ = ProductGroupService.getAllByBranch(organizationID,branchID, offset);
		cGroups_.then(function(toReturn){
			$scope.groups = toReturn.value;
			buildGroupDataTable(toReturn.value);
		});
	};
	
	/**
	 * Busca os usuarios por nome ou código, informados no filtro de pesquisa no list_user.html
	 */
	$scope.findGroupsByFilter = function(filter){
		
		$('#btnProductGroupEdit').attr("disabled","disabled");
		$('#btnProductGroupDelete').attr("disabled","disabled");
		
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;
		var offset = 0;
		var cGroups = ProductGroupService.getAllByDescription(filter,organizationID,branchID,offset);
		cGroups.then(function(toReturn){
			$scope.groups = toReturn.value;
			buildGroupDataTable(toReturn.value);
		});
	};
	
	
	$scope.newGroup = function(){		
		ProductGroupService.setProductGroupEdition({});
		$location.path('/produto/grupo/cadastro-categoria-produto');
	};	
	
	$scope.deleteGroup = function(){
		
	};

	
	/**
	 * Cria um array de grupos, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
	 * sendo "ouvido" na directive CustomDataTable
	 */
	function buildGroupDataTable(groups){
		var groupRows = [];
		
		groups.forEach(function(element, index){
			var group = element;
			if(group && group.id){
				//Adiciona um array com as colunas que irão ser apresentadas no dataTable
				groupRows.push([
				              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+group.id+'"><i></i></label>',
				              index+1,
				              group.groupID,
				              group.description
				              ]);
			}
		});
		
		//Seta os usuarios 
		$scope.rowsDataTable = groupRows;
	};
	
}]);
