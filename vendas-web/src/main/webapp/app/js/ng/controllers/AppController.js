/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){
	'use strict';	

	vendasApp
	.controller('AppController',['$rootScope','$scope','$location','ContextService','$window','AuthService', 'MessageService','socket',
	                             function AppController($rootScope, $scope,$location, ContextService, $window, AuthService, MessageService, socket) {
		
		/**
		 * Chamada no body do index.html. Qualquer inicialização do index pode ser feita aqui.
		 */
		$scope.init = function(){
			//Seta o usuario no $rootScope
			$rootScope.user = ContextService.getUserLogged();	

			//Filial atual
			$scope.currentBranch = ContextService.getBranchLogged();

			//Empresa logada
			$scope.organizationID  = ContextService.getOrganizationID();
			
			/**
			 * Quando o admin não autorizar a venda
			 */
			$scope.unauthorizedSale = function(){
				alert('Autorização Negada');
			};

			/**
			 * Quando o admin autorizar a venda
			 */
			$scope.authorizedResponse = function(data, authorized){
				var response = {
						roomName : data.room,
						token : data.token,
						userID: data.user.userID,
						authorized: authorized,
						admin: {
							userID : ContextService.getUserLogged().userID,
							name : ContextService.getUserLogged().name,
						}
				};
				
				socket.emit('authorization response', response);
			};



			socket.on('connect', function(){
				console.log('Conectou');
			});


			socket.emit('join room',  $scope.organizationID , 
					{
						userID: $rootScope.user.userID,
						name: $rootScope.user.name, 
						isAdmin: AuthService.isAdmin()
					}
			);

			socket.on('get msg', function(data) {
				alert(data);
			});
			
			
			socket.on('private msg', function(data){
				//Se o usuário estiver na pagina de mensagens
				if($location.path().indexOf('mensagem/mensagens') > -1) {
					//Se o usuário estiver na pagina de mensagens, ira lançar um evento, para ser recebido e tratada no MessageController 
					$rootScope.$broadcast('vendasApp:newMessage',  data );
				} else {
					$('#iconMenuNewMessage').remove();
					//Adiciona o icone de nova mensage no lado do meu Mensagens
					$('#menu_18 a').append('<span id="iconMenuNewMessage" class="badge bg-color-green bounceIn animated pull-right inbox-badge"><i class="fa fa-md fa-envelope-o"></i></span>');
				}
			});
			
			
			
			socket.on('authorization request', function(data){
			
				//console.log(data);
				
				
				var contentStr = 'O usuário '+data.user.name+' esta solicitando uma autorização de venda'+
								 '<li><strong> Tipo de Autorização : </strong> '+ data.details.authorizationType +' </li>' +
								 '<li><strong> Usuário : </strong> '+ data.user.name+ '</li>' +
				 				 '<li><strong> Cliente : </strong> '+ data.details.customer.customerID + ' - ' + data.details.customer.name + '</li>' +
				 				 '<li><strong> Limite de Crédito Disponivel : </strong> '+ data.details.avaliableCreditLimit+ '</li>' +
				 				 '<li><strong> Valor da Venda : </strong> '+ data.details.netValue+ '</li>' +
				 				 '<li><strong> Forma de Pagamento : </strong> '+ data.details.formPaymentSelected.description + ' - ' + data.details.installment.description + '</li>' +
				 				 '<li><strong> Informação Adicional : </strong> '+ (data.details.authorizationInfo !== undefined ? data.details.authorizationInfo : '')+ '</li>' +
				 				 ''+
				 				 '<p class=\'text-align-right\'><a href=\'javascript:void(0);\' onclick=\'authorizedSale('+JSON.stringify(data)+',false);\' class=\'btn bg-color-redLight txt-color-white btn-sm\'><i class="fa fa-ban"></i> Não Autorizar</a> <a href=\'javascript:void(0);\'  onclick=\'authorizedSale('+JSON.stringify(data)+',true);\' class=\'btn btn-success btn-sm\'><i class="fa fa-check"></i> Autorizar</a></p>';

				$.smallBox({
					title : "Solicitação de Autorização de Venda",
					content: contentStr,
					color : "#5384AF",
					icon : "fa fa-bell swing animated"
				});
				
				/*$.bigBox({
					title : 'Autorização de Venda',
					content : contentStr, //"Lorem ipsum dolor sit amet, test consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam <p class='text-align-right'><a id='confirmNotificationID' href='javascript:void(0);' onclick='unauthorizedSale();' class='btn btn-danger btn-sm'>Não Autorizar</a> <a href='javascript:void(0);'  onclick='authorizedSale();' class='btn btn-success btn-sm'>Autorizar</a></p>",

					//"Someone's at the door...shall one get it sir? <p class='text-align-right'><a href='javascript:void(0);' class='btn btn-primary btn-sm'>Yes</a> <a href='javascript:void(0);'  onclick='noAnswer();' class='btn btn-danger btn-sm'>No</a></p>"
					color : "#3276B1",
					icon : "fa fa-warning shake animated",
					//number : "4"
				}, function() {
					$scope.authorizedSale();
				});*/
			});
			
			
			/* Só ira executar quando todo o DOM tiver sido carregado. Foi feito isso para não seta o icone no menu, antes do menu ter sido renderizado */
			/*angular.element(document).ready(function(){
				var cMessages = MessageService.getMessagesUnreadCount($rootScope.user.userID);
				cMessages.then(function(toReturn){
					console.log(toReturn);
					if(toReturn.code === '200'){
						console.log( toReturn.value );
						addIconNewMessage();
					}
				});
			});*/
		};
	}]);	
})();