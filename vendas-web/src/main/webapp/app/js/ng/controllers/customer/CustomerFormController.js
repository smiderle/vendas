'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

vendasApp.controller('CustomerFormController',
		['$scope','ContextService','UtilityService','CustomerService','Constants',
		 'LocationService','PriceTableService','InstallmentService','FormsPaymentService','UserService',
		 function CustomerFormController($scope, ContextService, UtilityService, CustomerService,Constants, 
				 LocationService, PriceTableService, InstallmentService, FormsPaymentService, UserService) {

			
			
			var url = '';
			
			$scope.cities =[];

			/**
			 * Cliente
			 */
			$scope.customer = {};


			/**
			 * Chamado no init do form
			 */
			$scope.init = function() {
				$scope.customer = CustomerService.getCustomerEdition();
				var isEdition = false;
				
				if($scope.customer != undefined && $scope.customer.id != undefined){
					isEdition = true;
				}
				
				if(isEdition){
					$scope.selectedCity = $scope.customer.city;					
					
				} else {
					$scope.customer = {
							pictureUrl : Constants.URL_DEFAULT_NO_PICTURE,
							personType : 1,
							active: true
					};
				}
				
				/**
				 * Busca as tabelas de preço,e  parcelamentos, e se form um cadastro novo, 
				 * é setado o primeiro item da lista como padrão
				 */
				listTables(
						function(){
							if(!isEdition && $scope.tables.length > 0) 
								$scope.customer.priceTable =  $scope.tables[0].id;							
							});
				listInstallments(
						function(){
							if(!isEdition && $scope.installments.length > 0) 
								$scope.customer.installment =  $scope.installments[0].id;  
							});
				listFormsPayment(
						function(){
							if(!isEdition && $scope.formsPayment.length > 0) 
								$scope.customer.formPayment =  $scope.formsPayment[0].id;								
							});
				
				/**
				 * Carrega os usuarios.
				 * Se for um novo cadastro de cliente, seta o primeiro usuario como padrão,
				 * se for uma edição, seta o representante no combo, cujo o id seja igual ao customer.defaultSeller
				 */ 
				listUsers(						
						function(){
							if(!isEdition && $scope.users.length > 0){
								$scope.defaultSeller =  $scope.users[0];
							}									
						});
			};
			

			/**
			 * Salva ou atualiza o produto
			 */
			$scope.save = function(){

				if($('#customer-form').valid()){
					$('#alertCustomerInputsInvalids').hide();
					
					$scope.customer.organizationID = ContextService.getOrganizationID();
					$scope.customer.branchID = ContextService.getBranchLogged().branchOfficeID;

					
					var aCustomer = CustomerService.save($scope.customer);
					aCustomer.then(function(toReturn){
						if(toReturn.code == '200'){
							$scope.customer.id = toReturn.value.id; 
							UtilityService.showAlertSucess('Sucesso.', 'Cliente salvo com sucesso!!');
						} else {
							UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
						}					
					});
				} else {
					$('#alertCustomerInputsInvalids').show();
				}
			};


			$scope.dropzoneConfig = {
					'options': {
						'url': Constants.URL_UPLOAD_CUSTOMER,						
						'maxFilesize' : 1.30
					},
					'eventHandlers': {
						'sending': function (file, xhr, formData) {
						},
						'success': function (file, response) {

							if(response.code === "200"){
								url = response.payload.value;
								//Tive que fazer dessa forma para atualizar o ng-src, pois de outra forma não estava atualizando
								$scope.$apply(function () {
									$scope.$emit('vendasApp:customerPictureChange');
								});
							} else {

								//alertProductInputsInvalids
								UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
							}							
						},

						'error': function(file , errorMessage){
							alert(errorMessage);
						}
					}
			};

			/**
			 * Intercepta o evento vendasApp:change, para atualizar a imagem
			 */
			$scope.$on('vendasApp:customerPictureChange', function (event) {
				$scope.customer.pictureUrl = url;
			});	
			
			/**
			 * Busca o endereço pelo CEP informado
			 */
			$scope.findPostalCode = function(){		
				if($scope.customer && $scope.customer.postalCode){
					//Utilizado no ng-class para mostrar o loading dentro do imput enquanto busca o CEP
					$scope.loadingPostalCode = true;
					
					var cAddress = LocationService.findAddressByPostalCode($scope.customer.postalCode);
					cAddress.then(function(toReturn){
						if(toReturn.code == '200'){
							var address = toReturn.value;
							$scope.customer.district = address.district;
							$scope.customer.street = address.street;
							$scope.customer.city = address.city;
							
							$scope.selectedCity = address.city;
						} else {
							UtilityService.showAlertError('Não foi possivel localizar o CEP.', toReturn.message);
						}
						$scope.loadingPostalCode = false;
					});
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
			 * Carrega as tabelas de preço 
			 */
			
			function listTables(callback) {
				var aTables = PriceTableService.getAllByBranch(ContextService.getOrganizationID(), ContextService.getBranchLogged().branchOfficeID);
				aTables.then(function(toReturn){
					$scope.tables = toReturn.value;
					callback();
				});
			};
			
			/**
			 * Carrega os parcelamentos
			 */
			function listInstallments(callback) {
				var aInstallments = InstallmentService.getAllByBranch(ContextService.getOrganizationID(), ContextService.getBranchLogged().branchOfficeID);
				aInstallments.then(function(toReturn){
					$scope.installments = toReturn.value;
					callback();
				});
			};
			
			/**
			 * Carrega as formas de pagamento
			 */
			function listFormsPayment(callback){
				$scope.formsPayment =  FormsPaymentService.getFormsPayment();
				callback();
			};
			
			/**
			 * Carrega os usuarios
			 */
			function listUsers (callback){
				var organizationID = ContextService.getOrganizationID();
				var offset = 0;
				var cUsers_ = UserService.getAllByOrganization(organizationID, offset);
				cUsers_.then(function(toReturn){
					$scope.users = toReturn.value;
					if(callback){
						callback();
					}
				});
			};
						
		}]);