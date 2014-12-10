/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	
	'use strict';

	vendasApp.controller('ProductFormController',
			['$scope','ContextService','UtilityService','ProductService','ProductGroupService','DateUtil', 'Constants',
			 function ProductFormController($scope, ContextService, UtilityService, ProductService,ProductGroupService, DateUtil, Constants) {

				var url = '',
					defaultButtonPromotionDescription = 'Adicionar Promoção',
					userLogged = ContextService.getUserLogged();
			
				/**
				 * Produto
				 */
				$scope.product = {};
				
				/**
				 * Grupos
				 */
				$scope.groups = [];
				
				/**
				 * Preços de promoção
				 */
				$scope.promotions = [];
				
				/**
				 * Mensage de erro relacionao aos inputs ou foto
				 */
				$scope.showMessageError = false;
				
				/**
				 * Descrição do botão que adiciona uma promoção.
				 * Quando for edição a descrição muda
				 */
				$scope.buttonPromotionDescription= defaultButtonPromotionDescription;

				/**
				 * Chamado no init do form
				 */
				$scope.initProductForm = function(){
					$scope.product = ProductService.getProductEdition();
					var isEdition = false;
					
					if($scope.product != undefined && $scope.product.id != undefined){
						isEdition = true;
					}

					if(isEdition){
						loadPromotions();
						$scope.selectedGroup = $scope.product.group;
					} else {
						$scope.product = {
								pictureUrl : Constants.URL_DEFAULT_NO_PICTURE						
						};
					}
				};

				/**
				 * Salva ou atualiza o produto
				 */
				$scope.save = function(){
					
					if($('#productForm').valid()){
						
						$('#alertProductInputsInvalids').hide();
						
						$scope.product.organizationID = ContextService.getOrganizationID();
						$scope.product.branchID = ContextService.getBranchLogged().branchOfficeID;
						
						if($scope.selectedGroup && $scope.selectedGroup.id){
							$scope.product.group = $scope.selectedGroup;
						}
						
						var aProduct = ProductService.save($scope.product, userLogged.userID);
						aProduct.then(function(toReturn){
							if(toReturn.code == '200'){
								$scope.product.id = toReturn.value.id; 
								UtilityService.showAlertSucess('Sucesso.', 'Produto salvo com sucesso!!');
							} else {
								UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
							}					
						});
					} else {
						$('#alertProductInputsInvalids').show();
					}
				};
							
			/*	*//**
				 * Quando o autocomplete de catagorias estiver vazio, e quando ganhar o focus é retornado as primeiras categorias
				 *//*
				$scope.onFocusGroups = function(){
					var group = $('#txtProductGroup').val();				
					if(group == ''){					
						var organizationID = ContextService.getOrganizationID();
						var branchID = ContextService.getBranchLogged().branchOfficeID;
						
						var aGroups = ProductGroupService.getAllByBranch(organizationID, branchID, 0);
						
						aGroups.then(function(toReturn){									
							var tmp = [];
							toReturn.value.forEach(function(group){
								if(group.id){
									tmp.push(group);
								}
							});				
							$scope.groups = tmp;						
						});
						
					}
				};*/
				
				$scope.onChangeGroups = function(){
					/*
					 * Pega o texto digitado no imput do grupo(categoria). Não é pego o texto digitado pelo ng-model, 
					 * pois o ng-model seta o objeto selecionado
					 */
					var group = $('#txtProductGroup').val();
					
					var organizationID = ContextService.getOrganizationID();
					var branchID = ContextService.getBranchLogged().branchOfficeID;
					
					var aGroups = ProductGroupService.getAllByDescription(group, organizationID, branchID, 0);
					aGroups.then(function(toReturn){									
						var tmp = [];
						toReturn.value.forEach(function(group){
							if(group.id){
								tmp.push(group);
							}
						});				
						$scope.groups = tmp;
						
					});		
				};

				$scope.dropzoneConfig = {
						'options': {
							'url': Constants.URL_UPLOAD_PRODUCT,						
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
										$scope.$emit('vendasApp:productPictureChange');
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
				$scope.$on('vendasApp:productPictureChange', function (event) {
					$scope.product.pictureUrl = url;
				});
				
							
				/*PROMOÇÃO*/
				
				/**
				 * Salva um preço de promoção e adiciona a table
				 */
				$scope.savePromotion = function(promotion){
					
					if(isValidPromotionPrice()){
						$('#alertProductInputsInvalids').hide();
						//Se não tiver sido digitado nenhum valor, é setado 0
						if(!promotion || !promotion.promotionPrice){
							promotion = { promotionPrice : 0 };
						}
						
						var initialDate = $('#initialDate').val();
						var finalDate = $('#finalDate').val();
										
						if(DateUtil.isValidDate(initialDate) && DateUtil.isValidDate(finalDate)){
													
							promotion.initialDate = DateUtil.getTime(initialDate);
							promotion.finalDate = DateUtil.getTime(finalDate);
							promotion.productID = $scope.product.id;
							
							var aPromotion = ProductService.savePromotionPrice(promotion);
							aPromotion.then(function(toReturn){
								if(toReturn.code == '200'){
									clearPromotionFields();
									loadPromotions();
									UtilityService.showAlertSucess('Sucesso.', 'Preço de promoção salvo com sucesso!!');							
								} else {
									UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
								}					
							});
							
						} else {
							UtilityService.showAlertError('Opss ','Data Inválida');
						}					
					} else {
						$('#alertProductInputsInvalids').show();
					}
				};
				
				$scope.editPromotion = function(index){				
					$scope.promotion = $scope.promotions[index];
					$scope.auxInitDate = DateUtil.format($scope.promotion.initialDate);
					$scope.auxFinalDate = DateUtil.format($scope.promotion.finalDate);
					$scope.buttonPromotionDescription= 'Salvar Edição da Promoção';

				};
				
				$scope.cancelEditionPromotion = function(){
					clearPromotionFields();
				};
				
				/**
				 * Seta o preço de promoção como excluido
				 */
				$scope.removePromotion = function(index){
					$.SmartMessageBox({
						title : "Exclusão",
						content : "Deseja realmente excluir essa promoção?",
						buttons : '[Não][Sim]'
					}, function(ButtonPressed) {
						if (ButtonPressed === "Sim") {
							$scope.promotion = $scope.promotions[index];
							$scope.promotion.excluded = true;
							var aPromotion = ProductService.removePromotionPrice($scope.promotion);
							aPromotion.then(function(toReturn){
								if(toReturn.code == '200'){								
									UtilityService.showAlertSucess('Sucesso.', 'Preço de promoção removido com sucesso!');
									loadPromotions();
									clearPromotionFields();
								} else {
									UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
								}					
							});
							
						}
					});
				};
				
				
				
				function loadPromotions(){
					$scope.promotions = [];
					var aPromotions =  ProductService.getAllPromotionPriceByProductID($scope.product.id);
					aPromotions.then(function(toReturn){
						var tmp = [];
						toReturn.value.forEach(function(promotion){
							if(promotion.id){
								tmp.push(promotion);
							}
						});	
						
						$scope.promotions = tmp;
					});
				}
				
				/**
				 * Valida se os campos no qual foi utilizado as directivas validator
				 */
				function isValidPromotionPrice(){
					if($scope.productForm.promotionPrice.$valid){
						return true;
					}
					return false;
				}
				
				/**
				 * 
				 */
				function clearPromotionFields(){
					$scope.auxFinalDate = "";
					$scope.auxInitDate = "";
					$scope.promotion = {};
					$scope.buttonPromotionDescription= defaultButtonPromotionDescription;
				}
			}]);
})();
