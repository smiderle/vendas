'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('UserListController',
		['$scope','$location','UserService' ,'ContextService', 
        function UserController($scope, $location, UserService, ContextService) {
	
	/**
	 * Lista de usuários carregados
	 */
	$scope.users = [];
	
	/**
	 * Linhas que irão popular a datatable dos Usuários.
	 * Contém um array de usuários, com array de colunas como name, email...
	 */
	$scope.rowsDataTable = [];
		

	/**
	 * Chamado quando clicado no botão Editar.
	 * Percorre os usuarios que foram armazenados no $scope.users, e compara o userID, para encontrar o usuario selecionado. 
	 */
	$scope.editUser = function(){
		
		//Recupera o usuario selecionado no datatable.
		var userSelectedID = $( "input:checked" ).val();
		var users = $scope.users;
		
		var i;
		for(i in users){
			var user = users[i];			
			if(user && user.userID == userSelectedID){				
				UserService.setUserEdition(user);
				$location.path('/user/user-form');
				break;
			}
		}
	};


	/**
	 * Lista os usuarios da empresa.
	 */
	$scope.listUsers = function(){
		var organizationID = ContextService.getOrganizationID();
		var offset = 0;
		var cUsers_ = UserService.getAllByOrganization(organizationID, offset);
		cUsers_.then(function(toReturn){
			$scope.users = toReturn.value;
			buildUserDataTable(toReturn.value);
		});
	};
	
	/**
	 * Busca os usuarios por nome ou código, informados no filtro de pesquisa no list_user.html
	 */
	$scope.findUsersByFilter = function(filter){
		
		clearDatateble();
		
		$('#btnUserEdit').attr("disabled","disabled");
		$('#btnRemoveUser').attr("disabled","disabled");
		
		var organizationID = ContextService.getOrganizationID();
		var offset = 0;
		var cUsers = UserService.findUsersByFilter(organizationID,filter, offset);
		cUsers.then(function(toReturn){
			$scope.users = toReturn.value;
			buildUserDataTable(toReturn.value);
		});
	};
	
	
	$scope.newUser = function(){
		var userAccount ={email: '', name: '', password: ''};
		UserService.setUserEdition(userAccount);
		$location.path('/user/user-form');
	};	

	
	/**
	 * Cria um array de usuarios, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
	 * sendo "ouvido" na directive CustomDataTable
	 */
	function buildUserDataTable(users){
		var userRows = [];
		
		users.forEach(function(element, index){
			var user = element;
			if(user && user.userID){
				//Adiciona um array com as colunas que irão ser apresentadas no dataTable
				userRows.push([
				              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+user.userID+'"><i></i></label>',
				              index+1,				               
				              user.name, 
				              user.email,
				              user.active ? 'Sim' : 'Não',
				              user.portalAccess  ? 'Sim' : 'Não']);
			}
		});
		
		//Seta os usuarios 
		$scope.rowsDataTable = userRows;
	};
	
	/**
	 * Remove todos os elementos do datatable
	 */
	function clearDatateble(){
		var dataTable = $('#datatable_users').dataTable();
		dataTable.fnClearTable(0);
		dataTable.fnDraw();
	}
	
}]);
