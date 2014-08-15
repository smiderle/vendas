'use strict';

vendasApp.controller('UserController',
		['$scope','$location','UserService','UserBranchService' ,'ContextService','UtilityService','Constants', 
        function UserController($scope, $location, UserService,UserBranchService, ContextService, UtilityService,Constants) {

	$scope.userAccount;
	
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
			user: true,
			product : true,
			priceTable: true,
			installment: true,
			order: true,
			organization: true,
	};
	
	/**
	 * Lista de empresas do usuário
	 */
	$scope.userBranches = [];
	
	/**
	 * Função que inicializa os dados no form do usuario.
	 */
	$scope.initUserForm = function(){
		/**
		 * Quando for uma edição de usuario, a pagina list_users.html seta no UserService 
		 * o usuario a ser editado, e a pagina user.html recupera esse usuario 
		 */
		$scope.userAccount = UserService.getUserEdition();
		var userEdition = $scope.userAccount;
		if(userEdition){
			$scope.passwordConfirm = $scope.userAccount.password;
			
			/**
			 * Carrega as empresas do usuario
			 */
			var cUserBranch = UserBranchService.getAllByUserID(userEdition.userID);
			
			cUserBranch.then(function(toReturn){
				if(toReturn.code == '200'){
					$scope.userBranches = toReturn.value;
					console.log(toReturn.value);
				} else {
					console.log('erro');
				}
			});
		}

		/**
		 * Verifica os menus que o 
		 * usuario tem acesso, e seta no objeto de escopo $scope.menusApplication
		 */
		var menuTmp = {};
		if(userEdition && userEdition.menusApplication){
			var menus = userEdition.menusApplication;
			menus.forEach(function(element){
				switch (element.menuID) {
				case Constants.MENUID_PRODUCT:
					menuTmp.product = true;
					break;
				case Constants.MENUID_ORDER:
					menuTmp.order= true;
					break;
				case Constants.MENUID_USER:
					menuTmp.user= true;
					break;
				case Constants.MENUID_PRICE_TABLE:
					menuTmp.priceTable = true;
					break;
				case Constants.MENUID_INSTALLMENT:
					menuTmp.installment= true;
					break;
				case Constants.MENUID_ORGANIZATION:
					menuTmp.organization= true;
					break;					
				default:
					break;
				}				
			});			
			$scope.menusApplication = menuTmp;			
		}
		
		
	};
	
	

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
		 * Não permiete editar o próprio cadastro.
		 */
		if(user.userID == ContextService.getUserLogged().userID){
			UtilityService.showAlertError('Desculpe, operção não permitida.', 'Não é permitido atualizar o próprio usuário.');
		} else {
			/**
			 * Continua somente se o formulario foi validade com sucesso pelo jquery validate
			 */
			if($('#user-registration-form').valid()){
				user.organizationID = ContextService.getOrganizationID();
				user.menusApplication = [];

				//Verifica as opções que estão selecionadas, e adiciona ao menu do usuario			
				if($scope.menusApplication.product){
					user.menusApplication.push({"menuID": Constants.MENUID_PRODUCT});
					user.menusApplication.push({"menuID": Constants.MENUID_REGISTRATION});
				}
				if($scope.menusApplication.order){
					user.menusApplication.push({"menuID": Constants.MENUID_ORDER});
				}
				if($scope.menusApplication.user){
					user.menusApplication.push({"menuID": Constants.MENUID_USER});
					user.menusApplication.push({"menuID": Constants.MENUID_REGISTRATION});
				}
				if($scope.menusApplication.priceTable){
					user.menusApplication.push({"menuID": Constants.MENUID_PRICE_TABLE});
					user.menusApplication.push({"menuID": Constants.MENUID_REGISTRATION});
				}
				if($scope.menusApplication.installment){
					user.menusApplication.push({"menuID": Constants.MENUID_INSTALLMENT});
					user.menusApplication.push({"menuID": Constants.MENUID_REGISTRATION});
				} if($scope.menusApplication.organization){
					user.menusApplication.push({"menuID": Constants.MENUID_ORGANIZATION});
					user.menusApplication.push({"menuID": Constants.MENUID_REGISTRATION});
				}
				
				var cUser = UserService.saveUser(user);
				cUser.then(function(toReturn){
					if(toReturn.code == '200'){
						UtilityService.showAlertSucess('Sucesso.', 'Usuário salvo com sucesso!!');
					} else {
						UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
					}
				});
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
		var organizationID = ContextService.getOrganizationID();
		var offset = 0;
		var cUsers = UserService.findUsersByFilter(organizationID,filter, offset);
		cUsers.then(function(toReturn){
			$scope.users = toReturn.value;
			buildUserDataTable(toReturn.value);
		});
	};
	
	
	$scope.newUser = function(){
		var userAccount ={email: '', name: ' ', password: ''};
		UserService.setUserEdition(userAccount);
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
