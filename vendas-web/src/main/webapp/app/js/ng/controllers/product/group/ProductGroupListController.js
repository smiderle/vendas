'use strict';

vendasApp.controller('ProductGroupListController',
		['$scope','$location','ProductGroupService' ,'ContextService','UtilityService', 
        function ProductGroupListController($scope, $location, ProductGroupService, ContextService,UtilityService) {
	
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
		
		clearDatateble();
		
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

	
	/**
	 * Atualiza o grupo para excluida. Não ira mais ser listada
	 */
	$scope.deleteGroup = function(){
		
		$.SmartMessageBox({
			title : "Exclusão",
			content : "Deseja realmente excluir o grupo selecionado?",
			buttons : '[Não][Sim]'
		}, function(ButtonPressed) {
			if (ButtonPressed === "Sim") {
				var groupSelectedID = $( "input:checked" ).val();
				var groups = $scope.groups;
				var groupSelected;
				var i;
				for(i in groups){
					var group = groups[i];			
					if(group && group.id == groupSelectedID){				
						groupSelected = group;
						break;
					}
				}
				
				groupSelected.excluded = true;
				
				
				var aGroup = ProductGroupService.save(groupSelected);
				aGroup.then(function(toReturn){
					if(toReturn.code == '200'){
						clearDatateble();
						$scope.listGroups();
						UtilityService.showAlertSucess('Sucesso.', 'Categoria excluida!!');
					} else {
						UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
					}					
				});
				
			}
		});
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
	
	
	/**
	 * Remove todos os elementos do datatable
	 */
	function clearDatateble(){
		var dataTable = $('#datatable_groupproduct').dataTable();
		dataTable.fnClearTable(0);
		dataTable.fnDraw();
	}
	
}]);
