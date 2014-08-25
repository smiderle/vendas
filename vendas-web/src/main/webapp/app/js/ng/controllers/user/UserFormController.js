'use strict';

vendasApp.controller('UserFormController',
		['$scope','UserService','UserBranchService' ,'ContextService','UtilityService','Constants', 
        function UserController($scope, UserService,UserBranchService, ContextService, UtilityService,Constants) {

	$scope.userAccount;
	
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


	
}]);
