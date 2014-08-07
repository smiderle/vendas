'use strict';
//http://jsfiddle.net/zdam/7kLFU/
vendasApp.directive('customDataTable',function () {
    return  function(scope, element, attrs){
        
        var options = {
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
        
        //Valoriza a datatable
        var dataTable = element.dataTable(options);
        
        /**
         * Toda vez que for alterado o objeto $scope.algumaCoisa, onde algumaCoisa é o objeto $scope declarado na tag "<table aa-data"
         * os registros do datatable serão atualizados.
         * o objeto de $scope.algumaCoisa precisa ser um array de arrays (um array de linhas que contem um array de colunas) 
         */
        scope.$watch(attrs.aaData, function(value) {
        	//Remove todas as linhas existentes do datatable.
        	dataTable.fnClearTable(0);        	
			var val = value || null;
			if (val) {
				var i;
				for(i in val){
					var row = val[i];
					if(row){
						//Adiciona a nova  linha no datatable
						dataTable.fnAddData(row);
					}
				}
			}
			//Atualiza a datatable 
			dataTable.fnDraw();
		});
        
        
        /**
         * Registra um evento no clique da linha do datatable, que ira ativar ou desativar o checkbox da linha 
         */
        $('#'+attrs.id).delegate('tbody tr ', 'click', function (event) {

    		if (event.target.type !== 'radio') {
    			var unchecked = $(':radio', this).prop('checked');					    
    		    $(':radio', this).prop('checked', !unchecked);
    		    
    		    if($( "input:checked" ).val()){
    		    	$('.required-selected').removeAttr("disabled")    		    								
    		    } else {						    
    		    	$('.required-selected').attr("disabled","disabled")    		    	
    			}
    		    
    		    
    		} else {
    			var unchecked = $(':radio', this).prop('checked');
    			
    			if(unchecked){							
    				//$(':radio', this).prop('checked', false);
    			} else {
    				$(':radio', this).prop('checked', true);
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

    