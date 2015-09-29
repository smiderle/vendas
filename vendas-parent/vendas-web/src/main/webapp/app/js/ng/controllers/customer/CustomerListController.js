'use strict';

vendasApp.controller('CustomerListController',
		['$scope','$location','CustomerService' ,'ContextService','UtilityService', 
        function CustomerListController($scope, $location, CustomerService, ContextService, UtilityService) {
			

	/**
	 * Quando todos os clientes já tiverem sidos retornados do server, é setado como true.
	 * Assim, quando chegar na ultima pagina, não ira mais buscar os clientes no server.
	 */
	var noMoreProduct = false,
	
		userLogged = ContextService.getUserLogged();
	
	/**
	 * Lista de clientes carregados
	 */
	$scope.customers = [];
	
	/**
	 * Linhas que irão popular a datatable dos Usuários.
	 * Contém um array dos clientes, com array de colunas como name, email...
	 */
	$scope.rowsDataTable = [];
	
	
	
	$scope.init = function(){
		listCustomers(0);
	};
		

	/**
	 * Chamado quando clicado no botão Editar.
	 */
	$scope.editCustomer = function(){
		
		//Recupera o usuario selecionado no datatable.
		var customerSelectedID = $( "input:checked" ).val();
		var customers = $scope.customers;
		
		var i;
		for(i in customers){
			var customer = customers[i];			
			if(customer && customer.id == customerSelectedID){				
				CustomerService.setCustomerEdition(customer);
				$location.path('/cliente/cadastro-cliente');
				break;
			}
		}
	};


	/**
	 * Lista os usuarios da empresa.
	 */
	$scope.listUsers = function(){
		/*var organizationID = ContextService.getOrganizationID();
		var offset = 0;
		var cUsers_ = UserService.getAllByOrganization(organizationID, offset);
		cUsers_.then(function(toReturn){
			$scope.users = toReturn.value;
			buildUserDataTable(toReturn.value);
		});*/
	};
	
	
	
	/**
	 * Chamado quando o pesquisar o cliente 
	 */
	$scope.findCustomersByFilter = function(){

		$scope.customers = [];
		noMoreProduct = false;
		
		clearDatateble();
		
		$('#btnCustomerEdit').attr("disabled","disabled");
		$('#btnCustomerDelete').attr("disabled","disabled");
		
		findCustomersByFilter(getFilter(), 0);
	};
	
	
	
	$scope.deleteCustomer = function(){

		$.SmartMessageBox({
			title : "Exclusão",
			content : "Deseja realmente excluir o cliente selecionado?",
			buttons : '[Não][Sim]'
		}, function(ButtonPressed) {
			if (ButtonPressed === "Sim") {
				var customerSelectedID = $( "input:checked" ).val();
				var customers = $scope.customers;
				var customerSelected;
				var i;				
				for(i in customers){
					var customer = customers[i];			
					if(customer && customer.id == customerSelectedID){				
						customerSelected = customer;
						break;
					}
				}
				
				customerSelected.excluded = true;
				
				var aCustomer = $scope.loadOrder = CustomerService.save(customerSelected, userLogged.userID);
				aCustomer.then(function(toReturn){
					if(toReturn.code == '200'){
						clearDatateble();
						listCustomers(0);						
						UtilityService.showAlertSucess('Sucesso.', 'Produto excluido!');
					} else {
						UtilityService.showAlertError('Opss, algo estranho aconteceu.', toReturn.message);
					}
				});
			}
		});
	};
	
	$scope.newCustomer = function(){		
		CustomerService.setCustomerEdition({});
		$location.path('/cliente/cadastro-cliente');
	};	

	
	/**
	 * Cria um array de usuarios, contendo um array das colunas do dataTable, e seta no objeto de escopo rowsDataTable, que esta 
	 * sendo "ouvido" na directive CustomDataTable
	 */
	function buildCustomerDataTable(toReturn){
		
		var customers = toReturn.value,
		customerRows = [];
		
		/*
		 * Quando não retornar nemhum produto, seta essa variavel para sinalizar que não existe mais produtos, e
		 * e não ira mais paginar
		 */
		if(toReturn.rowCount == 0){
			noMoreProduct = true;
		}
		
		
		customers.forEach(function(element, index){
		
			var customer = element;
			
			$scope.customers.push(customer);
			
			if(customer && customer.id){
				
				var classRow = customer.active  ? '' : 'txt-color-red';
				
				customerRows.push([
					              '<label class="checkbox"><input type="radio" name="checkbox-inline" value="'+customer.id+'"><i></i></label>',
					              '<label class="'+classRow+'">'+customer.id+'</label>',
					              '<label class="'+classRow+'">'+customer.name+'</label>',
					              '<label class="'+classRow+'">'+customer.cpfCnpj != null ? customer.cpfCnpj : ' ' +'</label>',
					              '<label class="'+classRow+'">'+customer.cellPhone != null ? customer.cellPhone  : ' ' +'</label>',
					              '<label class="'+classRow+'">'+customer.email != null ? customer.email  : ' '+'</label>',
					              '<label class="'+classRow+'">'+(customer.active ? 'Sim' : 'Não') +'</label>',
					              ]);
			}
		});
		
		//Seta os usuarios 
		$scope.rowsDataTable = customerRows;
	};
	
	/**
	 * Lista os produtos
	 */
	function listCustomers(offset){		
		var _offset = offset | 0;
		
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;		
		var cCustomers = $scope.loadOrder = CustomerService.getAllByOrganization(organizationID,branchID, _offset);
		cCustomers.then(function(toReturn){
			buildCustomerDataTable(toReturn);
		});	
	}
	
	//OPERAÇÕES DATATABLE
	
	/**
	 * Fica ouvindo o evento, que é disparado quando o usuario clicar na ultima pagina do datatable
	 */
	$scope.$on('vendasApp:isLastPage', function (event) {
		if(!noMoreProduct){
			findCustomersByFilter(getFilter(),$scope.customers.length);
		}
	});
	
	
	/**
	 * Remove todos os elementos do datatable
	 */
	function clearDatateble(){
		var dataTable = $('#datatable_customers').dataTable();
		dataTable.fnClearTable(0);
		dataTable.fnDraw();
	}
	
	/**
	 * Function que busca os produtos por determinado filtro, e offset
	 */
	function findCustomersByFilter(filter, offset) {
		
		var organizationID = ContextService.getOrganizationID();
		var branchID = ContextService.getBranchLogged().branchOfficeID;
		
		var cCustomer = $scope.loadOrder = CustomerService.getAllByFilter(filter,organizationID,branchID,offset);
		cCustomer.then(function(toReturn){
			buildCustomerDataTable(toReturn);
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
	
}]);
