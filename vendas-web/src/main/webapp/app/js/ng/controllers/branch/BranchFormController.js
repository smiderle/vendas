'use strict';

vendasApp
.controller('BranchFormController', [
	'$scope','$rootScope', 'BranchService', 'ContextService', 'UtilityService', 'LocationService',
	function BranchController($scope,$rootScope, BranchService, ContextService, UtilityService, LocationService) {

		var userLogged = ContextService.getUserLogged();

		$scope.branchOffice;

		$scope.isEdition = false;

		$scope.cities =[];

		/**
		 * Ações para o select titulos vencidos, e limite de credito
		 */
		$scope.actions = [
		                  { label: 'Avisar', code: 'A'},
		                  { label: 'Bloquear', code: 'B'},
		                  { label: 'Solicitar Liberação', code: 'L'},
		                  { label: 'Não Fazer Nada', code: 'N'}	                  
		                  ];

		/**
		 * Inicializa o formulario da filial.
		 */
		$scope.initBranchForm = function(){

			/**
			 * Quando for uma edição da filial, a pagina branch-list.html seta no BranchService 
			 * o usuario a ser editado, e a pagina brach-form.html recupera essa filial
			 */
			$scope.branchOffice = BranchService.getBranchEdition();

			//Verifica se a filial já tem um id, que significa que é uma edição
			if($scope.branchOffice && $scope.branchOffice.branchOfficeID){			
				$scope.isEdition = true;
			} else{
				//Inicializa alguns campos
				$scope.branchOffice = {
						actionOverdue : 'N',
						actionCreditLimit : 'N',
						maximumDiscount: 100,
						showStockProduct : true,
						negativeStockProduct: false,
						sellerRegisterCustomer: true,
						sendEmailCustomer: false
				};
			}

			//Seta a cidade da filial no objeto $scope
			if($scope.branchOffice){
				$scope.selectedCity = $scope.branchOffice.city;
			}


		};


		/**
		 * Quando for digitado a descrição da cidade, busca cidades que começem com o texto digitado.
		 */
		$scope.onChangeCities = function(){
			/*
			 * Pega o texto digitado no imput da cidade. Não é pego o texto digitado pelo ng-model, 
			 * pois o ng-model seta o objeto selecionado
			 */
			var city = $('#txtCity').val();

			var aCities = LocationService.findAllByDescription(city);
			aCities.then(function(toReturn){
				if(toReturn.code == '200'){				
					var tmp = [];
					toReturn.value.forEach(function(city){
						if(city.cityID){
							tmp.push(city);
						}
					});				
					$scope.cities = tmp;
				}
			});		
		};


		/**
		 * Salva a filial
		 */
		$scope.save = function(branchOffice){
			if($('#branchOffice-registration-form').valid()){
				//Esconde a mensagem de erro caso esteja visivel
				$('#alertOrganizationInputsInvalids').hide();

				var cBranchOffice;
				if($scope.isEdition){
					branchOffice.city = $scope.selectedCity;
					cBranchOffice = BranchService.update(branchOffice, userLogged.userID);
				} else {
					branchOffice.organization = {
							organizationID: ContextService.getOrganizationID()
					};

					branchOffice.city = $scope.selectedCity;
					cBranchOffice = BranchService.save(branchOffice, userLogged.userID);								
				}


				cBranchOffice.then(function(toReturn){
					if(toReturn.code == '200'){
						updateBranchUserLogged(toReturn.value);
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



		/**
		 * Busca o endereço pelo CEP informado
		 */
		$scope.findPostalCode = function(){		
			if($scope.branchOffice && $scope.branchOffice.postalCode){
				//Utilizado no ng-class para mostrar o loading dentro do imput enquanto busca o CEP
				$scope.loadingPostalCode = true;

				var cAddress = LocationService.findAddressByPostalCode($scope.branchOffice.postalCode);
				cAddress.then(function(toReturn){
					if(toReturn.code == '200'){
						var address = toReturn.value;
						$scope.branchOffice.district = address.district;
						$scope.branchOffice.street = address.street;
						$scope.branchOffice.city = address.city;

						$scope.selectedCity = address.city;
					} else {
						UtilityService.showAlertError('Não foi possivel localizar o CEP.', toReturn.message);
					}
					$scope.loadingPostalCode = false;
				});
			}
		};

		/**
		 * Atualiza as informações das empresas do usuário logado, que estão na escopo
		 */
		function updateBranchUserLogged(branchSaved){
			var userLogged = ContextService.getUserLogged();
			var currentBranch = ContextService.getBranchLogged();

			//Se a empresa que esta sendo editada for a mesma do usuário logado
			if(currentBranch.branchOfficeID == branchSaved.branchOfficeID){
				ContextService.setBranchLogged(branchSaved);
			}

			if(userLogged){
				userLogged.userBranches.forEach(function(userBranch) {

					if( userBranch.branchOffice.branchOfficeID == branchSaved.branchOfficeID ){
						userBranch.branchOffice = branchSaved;
					}
				});
			}

			//Seta o usuario logado novamente, agora com as empresas atualizadas
			ContextService.setUserLogged(userLogged);

			//Sera capturado no ContextController para atualizar as empresas no combo do header
			$rootScope.$broadcast('vendasApp:updateBranches');
		}




}]);
