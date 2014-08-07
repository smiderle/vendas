'use strict';

vendasApp.controller('UserController',['$scope','UserService','ContextService','UtilityService','$location', function UserController($scope, UserService, ContextService, UtilityService, $location) {
	
	/**
	 * Quando for uma edição de usuario, a pagina list_users.html seta no UserService 
	 * o usuario a ser editado, e a pagina user.html recupera esse usuario 
	 */
	$scope.userAccount = UserService.getUserEdition();
	console.log(UserService.getUserEdition() ? UserService.getUserEdition().menusApplication : '');

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
	 * Model com as permições de acesso ao sistema
	 */
	$scope.menusApplication = {
			user : true,
			product : true,
			priceTable: true,
			installment: true,
			order: true
	};

	/**
	 * Chamado quando clicado no botão Editar.
	 * Percorre os usuarios que foram armazenados no $scope.users, e compara o userID, para encontrar o usuario selecionado. 
	 */
	$scope.editeUser = function(){
		
		//Recupera o usuario selecionado no datatable.
		var userSelectedID = $( "input:checked" ).val();
		var users = $scope.users;
		
		var i;
		for(i in users){
			var user = users[i];			
			if(user && user.userID == userSelectedID){
				UserService.setUserEdition(user);
				$location.path('/register/user');
				break;
			}
		}
	};

	/**
	 * Chamado quando clicado no botão Salvar
	 */
	$scope.saveUser = function(user){

		/**
		 * Continua somente se o formulario foi validade com sucesso pelo jquery validate
		 */
		if($('#user-registration-form').valid()){
			user.organizationID = ContextService.getOrganizationID();
			user.menusApplication = [];

			//Verifica as opções que estão selecionadas, e adiciona ao menu do usuario
			if($scope.menusApplication.user)
				user.menusApplication.push({"menuID": 1});
			if($scope.menusApplication.product)
				user.menusApplication.push({"menuID": 2});
			if($scope.menusApplication.priceTable)
				user.menusApplication.push({"menuID": 3});
			if($scope.menusApplication.installment)
				user.menusApplication.push({"menuID": 4});
			if($scope.menusApplication.order)
				user.menusApplication.push({"menuID": 5});


			var cUser = UserService.saveUser(user);
			cUser.then(function(toReturn){
				if(toReturn.code == '200'){
					UtilityService.showAlertSucess('Sucesso.', 'Usuário inserido com sucesso!!');
				} else {
					UtilityService.showAlertError('Opss, algo estranho aconteceu.', 'Usuário não foi inserido!');
				}
			});
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
		var organizationID = ContextService.getOrganizationID();
		var offset = 0;
		var cUsers = UserService.findUsersByFilter(organizationID,filter, offset);
		cUsers.then(function(toReturn){
			$scope.users = toReturn.value;
			buildUserDataTable(toReturn.value);
		});
	};
	
	
	$scope.newUser = function(){
		UserService.setUserEdition({email: '', name: ''});
		$location.path('/register/user');
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
				              user.userID, 
				              user.name, 
				              user.email,
				              user.active ? 'Sim' : 'Não',
				              user.portalAccess  ? 'Sim' : 'Não']);
			}
		});
		
		//Seta os usuarios 
		$scope.rowsDataTable = userRows;
	};
	
}]);
