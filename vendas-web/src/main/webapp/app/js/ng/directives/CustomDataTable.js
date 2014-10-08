'use strict';
//http://jsfiddle.net/zdam/7kLFU/
vendasApp.directive('customDataTable', function () {	
	return  function(scope, element, attrs){
		/**
		 * Controla quando o envento scope.$emit('vendasApp:isLastPage') pode ser emitido. 
		 */
		//var enventActive = false, 
		
		/**
		 * é um datatable com paginação ?
		 */
		var paging = attrs.paging;

		var options = {

				"fnDrawCallback": function(){
					//console.log('Total ' + this.fnPagingInfo().iTotalPages + ' Current '+ this.fnPagingInfo().iPage + ' Rows dt'+ this.fnPagingInfo().iTotal + 'Total de reg ' + attrs.aaData);

					/*if(paging && enventActive && (this.fnPagingInfo().iTotalPages == this.fnPagingInfo().iPage)){
						scope.$emit('vendasApp:isLastPage');
					}*/
				},

				"bStateSave": false,	
				"bFilter": false,
				"iDisplayLength": 15,		    	
				"sDom": "<'top't>"+				
				"<'dt-toolbar-footer'<'col-xs-6'i><'col-xs-6'p>>",
				"oLanguage": {
					"sEmptyTable": "Nenhum registro encontrado",
					"sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
					"sInfoFiltered": "(Filtrados de _MAX_ registros)",
					"sInfoPostFix": "",
					"sInfoThousands": ".",
					"sLengthMenu": "_MENU_ resultados por página",
					"sLoadingRecords": "Carregando...",
					"sProcessing": "Processando...",
					"sZeroRecords": "Nenhum registro encontrado",
					"sSearch": "Pesquisar",
					"oPaginate": {
						"sNext": "Próximo",
						"sPrevious": "Anterior",
						"sFirst": "Primeiro",
						"sLast": "Último"
					},
					"oAria": {
						"sSortAscending": ": Ordenar colunas de forma ascendente",
						"sSortDescending": ": Ordenar colunas de forma descendente"
					}
				}
		};


		/**
		 * Permite que algumas informações referente ao datatable possam ser recuperadas
		 */
		$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings ) {
			return {
				"iStart":         oSettings._iDisplayStart,
				"iEnd":           oSettings.fnDisplayEnd(),
				"iLength":        oSettings._iDisplayLength,
				"iTotal":         oSettings.fnRecordsTotal(),
				"iFilteredTotal": oSettings.fnRecordsDisplay(),
				"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ) +1,
				"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
			};
		};

		//Valoriza a datatable
		var dataTable = element.dataTable(options);


		/**
		 * Toda vez que for alterado o objeto $scope.algumaCoisa, onde algumaCoisa é o objeto $scope declarado na tag "<table aa-data"
		 * os registros do datatable serão atualizados.
		 * o objeto de $scope.algumaCoisa precisa ser um array de arrays (um array de linhas que contem um array de colunas) 
		 */
		scope.$watch(attrs.aaData, function(value) {
			var currentPage = dataTable.fnPagingInfo().iPage;
			var iLength = dataTable.fnPagingInfo().iLength;
			
			
			var val = value || null;
			//Quantidade de registros no datatable
			var iTotal = dataTable.fnPagingInfo().iTotal;
			

			
			
			
			//enventActive = false;
			//Remove todas as linhas existentes do datatable.
			//dataTable.fnClearTable(0);
						
			if (val) {
				//Quantidade de registros no datatable mais a quantidade que sera inserida
				var totalRow = dataTable.fnPagingInfo().iTotal + val.length;
				
				var i=0,
				len = val.length;
				
				for(;i<len; i++){
					var row = val[i];
					if(row){
						//Adiciona a nova  linha no datatable
						dataTable.fnAddData(row);
					}
					/*
					 * Só ativa o evento quando a paginação estiver ativa 
					 * e for o penultimo produto a ser carregado 
					 * e a quantidade de item que foi retornado do server for maior que o limite de linhas por pagina
					 */ 
					if(paging && dataTable.fnPagingInfo().iTotal == (totalRow - 1) /*&& (len == 4)*/){
						console.log('Ativando evento');
						//enventActive = true;
						scope.$emit('vendasApp:isLastPage');
					}

					/*if(paging && i == (parseInt(i) + iTotal) == totalRow){
						console.log('Desativando evento');
						//enventActive = false;
					}*/
					
				}
				dataTable.fnPageChange(currentPage-1,true);
				
				
			}
			
			//Atualiza a datatable 
			//dataTable.fnDraw();
		});



		var element = attrs.radio == 'true' ? 'radio' : 'checkbox' ;


		/**
		 * Registra um evento no clique da linha do datatable, que ira ativar ou desativar o checkbox da linha 
		 */
		$('#'+attrs.id).delegate('tbody tr ', 'click', function (event) {

			if (event.target.type !== element) {
				var unchecked = $(':'+element, this).prop('checked');					    
				$(':'+element, this).prop('checked', !unchecked);

				if($( "input:checked" ).val()){
					$('.required-selected').removeAttr("disabled");    		    								
				} else {						    
					$('.required-selected').attr("disabled","disabled");    		    	
				}


			} else {
				var unchecked = $(':'+element, this).prop('checked');

				if(unchecked){							
					//$(':radio', this).prop('checked', false);
				} else {
					$(':'+element, this).prop('checked', true);
				}


				if($( "input:checked" ).val()){
					$('.required-selected').removeAttr("disabled");
				} else {
					$('.required-selected').attr("disabled","disabled");
				}
			}    		
		});
	};
});

