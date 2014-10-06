'use strict';

vendasApp.controller('ProductListController',['$scope','$location','ProductService' ,'ContextService','UtilityService', 
        function ProductListController($scope, $location, ProductService, ContextService,UtilityService) {
	
	/**
	 * Lista de produtos carregados
	 */
	$scope.products = [];
	
	/**
	 * Linhas que irão popular a datatable dos produtos.
	 * 
	 */
	$scope.rowsDataTable = [];
	
	/**
	 * Quando todos os produtos já tiverem sidos retornados do server, é setado como true.
	 * Assim, quando chegar na ultima pagina, não ira mais buscar os produtos no server.
	 */
	var noMoreProduct = false;
	
	/**
	 * Chamando no init do html
	 */
	$scope.init = function(){
		listProducts(0);
	};
		

	/**
	 * Chamado quando clicado no botão Editar.
	 * Percorre os produtos que foram armazenados no $scope.products, e compara o productID, para encontrar o produto selecionado. 
	 */
	$scope.editProduct = function(){
		
		//Recupera o produto selecionado no datatable.
		var productSelectedID = $( "input:checked" ).val();
		var products = $scope.products;

		var i;
		for(i in products){
			var product = products[i];
			if(product && product.id == productSelectedID ){
				ProductService.setProductEdition(product);
				$location.path('/produto/cadastro-produto');
				break;
			}
		}
	};
	
	/**
	 * Chamado quando o pesquisar o produto 
	 */
	$scope.findProductsByFilter = function(){

		$scope.products = [];
		noMoreProduct = false;
		
		clearDatateble();
		
		$('#btnProductEdit').attr("disabled","disabled");
		$('#btnProductDelete').attr("disabled","disabled");
		
		findProductsByFilter(getFilter(), 0);
	};
	
	/**
	 * Redireciona para a pagina de cadastro de produto
	 */
	$scope.newProduct = function(){
		ProductService.setProductEdition({});
		$location.path('/produto/cadastro-produto');
	};	

	
	/**
	 * Atualiza o produto para excluida. Não ira mais ser listado
	 */
	$scope.deleteProduct = function(){

		$.SmartMessageBox({
			title : "Exclusão",
			content : "Deseja realmente excluir o produto selecionado?",
			buttons : '[Não][Sim]'
		}, function(ButtonPressed) {
			if (ButtonPressed === "Sim") {
				var productSelectedID = $( "input:checked" ).val();
				var products = $scope.products;
				var productSelected;
				var i;				
				for(i in products){
					var product = products[i];			
					if(product && product.id == productSelectedID){				
						productSelected = product;
						break;
					}
				}
				
				productSelected.excluded = true;
				
				
				var aProduct = ProductService.save(productSelected);
				aProduct.then(function(toReturn){
					if(toReturn.code == '200'){
						listProducts(0);
						UtilityService.showAlertSucess('Sucesso.', 'Produto excluido!');
					} else {
						UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
					}					
				});
				
			}
		});
	};

	
	/**
	 * Cria um array de produtos, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
	 * sendo "ouvido" na directive CustomDataTable
	 */
	function buildProductDataTable(toReturn){
		
		var products = toReturn.value;
		var productRows = [];
		
		/*
		 * Quando não retornar nemhum produto, seta essa variavel para sinalizar que não existe mais produtos, e
		 * e não ira mais paginar
		 */
		if(toReturn.rowCount == 0){
			noMoreProduct = true;
		}
		
		products.forEach(function(element, index){
			var product = element;
			if(product && product.id){
				
				if(product.stockAmount == null || product.stockAmount == undefined){
					product.stockAmount = 0;
				}
				
				if(product.salesPrice == null || product.salesPrice == undefined){
					product.salesPrice = 0;
				}
				
				$scope.products.push(product);
				//Adiciona um array com as colunas que irão ser apresentadas no dataTable

				var classRow = product.stockAmount > 0 ? '' : 'txt-color-red';
				productRows.push([
				              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+product.id+'"><i></i></label>',
				              '<label class="'+classRow+'">'+ (index+1) +'</label>',
				              '<label class="'+classRow+'">'+product.productID+'</label>',
				              '<strong><label class="'+classRow+'">'+product.description+'</label></strong>',
				              '<label class="'+classRow+'">'+product.packaging != null ? product.packaging : '' +'</label>',
				              '<label class="'+classRow+'">'+parseFloat(product.stockAmount).toFixed(2).replace('.', ',')+'</label>',
				              '<label class="'+classRow+'"> R$ '+parseFloat(product.salesPrice).toFixed(2).replace('.', ',')+'</label>'		              
				              ]);
			}
		});
		
		//Seta os produtos
		$scope.rowsDataTable = productRows;
	};
	
	/**
	 * Lista os produtos
	 */
	function listProducts(offset){

		var _offset = offset | 0;
		
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;		
		var cProducts = ProductService.getAllByBranch(organizationID,branchID, _offset);
		cProducts.then(function(toReturn){
			buildProductDataTable(toReturn);
		});	
	}
	
	/**
	 * Function que busca os produtos por determinado filtro, e offset
	 */
	function findProductsByFilter(filter, offset) {
		
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;
		
		var cProducts = ProductService.getAllByFilter(filter,organizationID,branchID,offset);
		cProducts.then(function(toReturn){
			buildProductDataTable(toReturn);
		});
	}
	
	/**
	 * Retorna o filtro
	 */
	function getFilter(){		
		if($scope.filter !== undefined){
			return $scope.filter;
		} else {
			return '';
		}
	}
	
	
	//OPERAÇÕES DATATABLE
	
	/**
	 * Fica ouvindo o evento, que é disparado quando o usuario clicar na ultima pagina do datatable
	 */
	$scope.$on('vendasApp:isLastPage', function (event) {
		if(!noMoreProduct){
			findProductsByFilter(getFilter(),$scope.products.length);
			
			//$scope.listProducts($scope.products.length);
		}
	});
	
	/**
	 * Remove todos os elementos do datatable
	 */
	function clearDatateble(){
		var dataTable = $('#datatable_product').dataTable();
		dataTable.fnClearTable(0);
		dataTable.fnDraw();
	}	
}]);
