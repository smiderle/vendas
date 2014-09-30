'use strict';

vendasApp.controller('UserFormController',
		['$scope','UserService' ,'BranchService', 'ContextService','UtilityService','Constants', 
		 function UserController($scope, UserService,BranchService, ContextService, UtilityService,Constants) {

			$scope.userAccount;

			$scope.isEdition = false;
		

			/**
			 * Lista de empresas do usuário
			 */
			$scope.userBranches = [];

			/**
			 * Empresas que irão popular o datatable de empresas do usuario
			 */
			$scope.userBranchesDataTable = [];



			/**
			 * Função que inicializa os dados no form do usuario.			
			 * Quando for uma edição de usuario, a pagina list_users.html seta no UserService 
			 * o usuario, com as informações basicas a ser editado, e a pagina user.html recupera esse usuario, 
			 * e busca no server as informações completa dele
			 */
			$scope.initUserForm = function(){
				
				//Usuario com as informações basicas
				var userEdition = UserService.getUserEdition();
				
				
				//Se for uma edição de usuario
				if(userEdition && userEdition.userID){
					$scope.isEdition = true;
					$scope.userIsSeller = false;
					$scope.userIsAdministrator = false;
					
					
					//Busca as informações detalhadas do usuario, como menus, empresas, permissões...
					var cUser = UserService.findUserByEmail(userEdition.email);
					cUser.then(function(toReturn){
						//Usuario com as informações completas.
						userEdition = toReturn.value;						
						
						$scope.passwordConfirm = userEdition.password;							
							
						//Verifica  as permissões do usuario, e seta o objeto de escopo que controla os checkbox
						if(userEdition.userRoles){
							userEdition.userRoles.forEach(function(userRole){
								if(userRole.role == 'ROLE_USER'){
									$scope.userIsSeller = true;
								} else if(userRole.role == 'ROLE_ADMIN'){
									$scope.userIsAdministrator = true;
								}
							});
						}					
						
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
						
						
						//Busca as empresas salvas no sistema						
						var cBranches = BranchService.getAllBranchesByOrganizationID(ContextService.getOrganizationID());
						cBranches.then(function(toReturn){
							$scope.branches = toReturn.value;
							buildBranchOfficeDataTable($scope.branches, userEdition);
						});
						
						$scope.userAccount = userEdition;						
					});
				} else {
					$scope.userAccount = {
							active : true
					};
					
					$scope.menusApplication = {
							user: true,
							product : true,
							priceTable: true,
							installment: true,
							order: true,
							organization: true,
					};
					
					//Busca as empresas salvas no sistema					
					var cBranches = BranchService.getAllBranchesByOrganizationID(ContextService.getOrganizationID());
					cBranches.then(function(toReturn){
						$scope.branches = toReturn.value;
						buildBranchOfficeDataTable($scope.branches, undefined);
					});
				}				
			};


			/**
			 * Chamado quando clicado no botão Salvar
			 */
			$scope.saveUser = function(user){
				
				$('#alertUserInputsInvalids').hide();

				/**
				 * Não permite editar o próprio cadastro.
				 */
				if(user && user.userID == ContextService.getUserLogged().userID){
					UtilityService.showAlertError('Desculpe, operção não permitida.', 'Não é permitido atualizar o próprio usuário.');
				} else {
					/**
					 * Continua somente se o formulario foi validade com sucesso pelo jquery validate
					 */
					if($('#user-registration-form').valid()){
						user.organizationID = ContextService.getOrganizationID();
						
						user.userBranches = [];
						
						var someBranchSelected = false;

						//Verifica se as empresas estão selecionadas ou não
						var oTable = $('#datatable_branches').dataTable();
						$("input:checkbox", oTable.fnGetNodes()).each(function(){
							var selected = $(this).prop('checked');
							//Adiciona a empresa na variavel userBranch para posteriormente ser adicionada ao array de empresas do usuario
							var userBranch = {
									'userID': user.userID,
									'enable': selected,
									'branchOffice': {
			                    		'branchOfficeID': $(this).val(),
			                    		'organization':{
			                    			'organizationID': user.organizationID
			                    		}
			                    	}
									
							};
							
							if(selected){
								someBranchSelected = true;
							}
								
							user.userBranches.push(userBranch);
						});
						
						//Valida se pelo menos uma empresa esta selecionada.
						if(!someBranchSelected){
							$scope.errorMessage = "Por favor, selecione pelo menos uma empresa para o usuário ter acesso."; 
							$('#alertUserInputsInvalids').show();
							return;
						} 
																	
						
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
					
						user.userRoles = [];
						if($scope.userIsSeller){
							user.userRoles.push({'userID': user.userID,role: 'ROLE_USER'});
						}
						
						if($scope.userIsAdministrator){
							user.userRoles.push({'userID': user.userID,role: 'ROLE_ADMIN'});
						}						
						
						//Salva o usuario
						var cUser = UserService.saveUser(user);
						cUser.then(function(toReturn){
							if(toReturn.code == '200'){
								UtilityService.showAlertSucess('Sucesso.', 'Usuário salvo com sucesso!!');
							} else {
								UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
							}
						});
					} else {
						//Mostra a mensagem de erro caso algo algum campo esteja invalido.
						$scope.errorMessage = "Alguns campos estão incorretos. Por favor, verifique antes de continuar."; 
						$('#alertUserInputsInvalids').show();			
					}
				}		
			};


			/**
			 * Cria um array de filiais, contendo um array das colunas do dataTable, e seta no objeto de escopo userBranchesDataTable, que esta 
			 * sendo "ouvido" na directive CustomDataTable
			 */
			function buildBranchOfficeDataTable(branches, userEdition){
				var branchOfficeRows = [];

				branches.forEach(function(branchOffice, index){			
					if(branchOffice && branchOffice.branchOfficeID){
						
						//Verifica se o usuarioFilial estas abilitado, se tiver, o checka o checkbox
						var checked = '';
						
						if(userEdition){
							if(userEdition.userBranches){
								var i;
								for(i in userEdition.userBranches){
									var userBranch = userEdition.userBranches[i];
									if(branchOffice.branchOfficeID == userBranch.branchOffice.branchOfficeID){
										checked = userBranch.enable == true ? 'checked' : '';
										break;
									}
								}
							}
						} else {
							checked = 'checked';
						}

						//Adiciona um array com as colunas que irão ser apresentadas no dataTable
						branchOfficeRows.push([
						                       '<label class="checkbox"><input type="checkbox" name="checkbox-inline" '+checked+' value="'+branchOffice.branchOfficeID+'"><i></i></label>',	                       				               
						                       	branchOffice.branchOfficeID,
						                       	branchOffice.fancyName,
						                       	branchOffice.maximumDiscount || '0,00',
						                       	branchOffice.showStockProduct ? 'Sim' : 'Não',
						                    	branchOffice.negativeStockProduct ? 'Sim' : 'Não',
						                    	branchOffice.sellerRegisterCustomer ? 'Sim' : 'Não',
						                    	branchOffice.actionOverdue == 'B' ? 'Bloquear' : branchOffice.actionOverdue == 'N' ? 'Nada' : 'Não Fazer Nada' ,
						                    	branchOffice.actionCreditLimit == 'B' ? 'Bloquear' : branchOffice.actionCreditLimit == 'N' ? 'Nada' : 'Avisar' 
						                    	]);
					}
				});

				//Seta os usuarios 
				$scope.userBranchesDataTable = branchOfficeRows;
			};
		}]);
