'use strict';

vendasApp
.controller('BranchFormController',
		function BranchController($scope, BranchService, ContextService, UtilityService, LocationService) {
	
	
	$scope.branchOffice;
	
	$scope.isEdition = false;
	
	/**
	 * Ações para o select titulos vencidos, e limite de credito
	 */
	$scope.actions = [
	                  { label: 'Bloquear', code: 'B'},
	                  { label: 'Avisar', code: 'A'},
	                  { label: 'Não Fazer Nada', code: 'N'},
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
		
		//Verifica se a filial tem um já tem um id, que significa que é uma edição
		if($scope.branchOffice && $scope.branchOffice.branchOfficeID){			
			$scope.isEdition = true;
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
				cBranchOffice = BranchService.update(branchOffice);
			} else {
				branchOffice.organization = {
						organizationID: ContextService.getOrganizationID()
				};
				
				branchOffice.city = $scope.selectedCity;
				cBranchOffice = BranchService.save(branchOffice);								
			}
			
			
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
	
	$scope.cities =[];
	
	
});
