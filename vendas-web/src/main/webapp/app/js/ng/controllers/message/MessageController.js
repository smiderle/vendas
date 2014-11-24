/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

(function(){

	'use strict';

	vendasApp.controller('MessageController',['$scope','$http','$compile' , 'ContextService', 'UserService','socket', 'MessageService',
	        function MessageController($scope, $http,$compile, ContextService, UserService, socket, MessageService) {
			
		var userLogged = ContextService.getUserLogged();
		
		/**
		 * Usuário selecionado
		 */
		$scope.userSelected = undefined;
		
		$scope.messages = [];
		
		/**
		 * Function inicial
		 */
		$scope.init = function(){
			
			//Quando entrar nas mensagems, remove o icone da nova mensagem do menu
			$('#iconMenuNewMessage').remove();
			
			$scope.listUsers();		
			
		};
		
		
		
		/**
		 * Quando selecionado um usuário
		 */
		$scope.setUserSelected = function(user, index){

			reset();
			
			//Remove o icone de notificação de nova mensagem 
			var idIcon = 'iconNotification'+index;
			$('#'+idIcon).remove();
			
			$scope.userSelected = user;
			
			
			loadMessages(user.userID, userLogged.userID,  scrollToEnd);
		};
		
		/**
		 * Envia mensagem
		 */
		$scope.sendMessage = function(txtMessage){
			var message = { 
					'from': userLogged.userID,
					'to': $scope.userSelected.userID,
					'message': txtMessage
			};

			socket.emit('private msg', message);
			
			addMessage(false,true, userLogged.name, txtMessage, moment().format('DD/MM/YYYY hh:mm:ss'));
			
			scrollToEnd();
			$scope.message = '';
		};
		
		/**
		 * Lista os usuários
		 */
		$scope.listUsers = function(){
			var organizationID = ContextService.getOrganizationID(),
				userID = userLogged.userID;
			
			var cUsers = UserService.getOtherUsersByOrganizationID(organizationID, userID, 0);
			cUsers.then(function(toReturn){
				$scope.users = toReturn.value;
				checkNewMessages();
			});
		};
		
		function reset(){
			//Limpa as mensagens que estiverem carregadas na tela
			$('.chat-body').empty();
			
			$scope.messages = [];
		}
		
		
		function loadMessages(from, to, callback) {	

			var scrollHeightBefore = $('.chat-body').get(0).scrollHeight;
			
			var aMessage = $scope.promise = MessageService.getMessages(from, to, $scope.messages.length);
			aMessage.then(function(toReturn){
				if(toReturn.code === '200'){
					var messages = toReturn.value;
									
					for( var i = 0; i < messages.length; i++ ) {					
						var message = messages[i]; 
						
						$scope.messages.push(message);
						
						if(message.from == userLogged.userID) {
							addMessage(true,true, userLogged.name, message.message, moment(message.messageDate).format('DD/MM/YYYY hh:mm:ss'));
				        } else {
				        	addMessage(true,false, $scope.userSelected.name, message.message, moment(message.messageDate).format('DD/MM/YYYY hh:mm:ss'));
				        }
					}
					
					if( messages.length >= 20 ){
						addBtnOldMessages();
					} else {
						$('#btnOldMessages').remove();
					}
					
					//scrollToEnd();
					callback(scrollHeightBefore);
				}
			});
		}
		
		/**
		 * Adiciona uma mensagem na página
		 * pre - Se o elemento ira ser inserido no inicio
		 * me - Se quem esta enviando a mensagem é o usuário logado
		 * name - Nome do usuário da mensagem
		 * message - Mensagem
		 * date - Data e Hora da mensagem 
		 */
		function addMessage(pre, me, name, message, date){
			if(pre){
				if(me){
					$('.chat-body').prepend('<li class="message bubble me"><span class="username" style="color:#f78b00">'+name +'</span><span class="messageBody">'+message+'</span><span class="rowMessageTime"><span class="messageTime">'+date+'</span></span></li>');
				} else {
					$('.chat-body').prepend('<li class="message bubble you"><span class="username" style="color:#4192D4">'+ name +' </span><span class="messageBody">'+message+'</span> <span class="rowMessageTime"><span class="messageTime">'+date+'</span></span></li>');
				}
			} else {
				if(me){
					$('.chat-body').append('<li class="message bubble me"><span class="username" style="color:#f78b00">'+name +'</span><span class="messageBody">'+message+'</span><span class="rowMessageTime"><span class="messageTime">'+date+'</span></span></li>');
				} else {
					$('.chat-body').append('<li class="message bubble you"><span class="username" style="color:#4192D4">'+ name +' </span><span class="messageBody">'+message+'</span> <span class="rowMessageTime"><span class="messageTime">'+date+'</span></span></li>');
				}
			}
			
		}
		
		/**
		 * Quando o botão mensagens anteigas for precisonado
		 */
		$scope.onLoadMessages = function(){		
			loadMessages($scope.userSelected.userID, userLogged.userID, function( beforeHeight ){			
				$('.chat-body')[0].scrollTop = $('.chat-body').get(0).scrollHeight - beforeHeight ;			
			});
		};

		
		/**
		 * Adiciona o botão para carregar as mensagens antigas.
		 */
		function addBtnOldMessages(){
			$('#btnOldMessages').remove();
			var $el = $('<button id="btnOldMessages" ng-click="onLoadMessages()" type="button" class="btn btn-primary btn-lg btn-block">Carregar mensagens anteriores</button>')
				.prependTo('.chat-body');
			 $compile($el)($scope);
		}
		
		/**
		 * Posiciona o scroll para o final da página 
		 */
		function scrollToEnd(){
			$('.chat-body')[0].scrollTop = $('.chat-body')[0].scrollHeight;
		}
		
		/**
		 * Verifica os usuários que possuam novas mensagens enviadas para o usuário logado.
		 * Então é criado o icone de nova mensagem do lado do nome do usuário que enviou a mensagem
		 */
		function checkNewMessages(){
			/**
			 * Usuários com novas mensagens.
			 */
			var cUsersNewMsg = MessageService.getUsersWithNewMessage(userLogged.userID);
			cUsersNewMsg.then(function(toReturn){
				//$scope.usersNewMsg = toReturn.value;
				
				toReturn.value.forEach(function(userID){
					for(var i = 0; i < $scope.users.length; i++){
						var user = $scope.users[i];
						if(user.userID == userID){
							var idIcon = 'iconNotification'+i;
							$('#'+idIcon).remove();
							$('#'+i).append('<span id="'+idIcon+'" class="badge bg-color-green bounceIn animated pull-right inbox-badge"><i class="fa fa-md fa-envelope-o"></i></span>');
							break;
						}
					}
				});
			});
		}
		
		
		/**
		 * É lançado uma mensagem do AppController
		 */
		$scope.$on('vendasApp:newMessage', function(event,msg){
					
			if($scope.userSelected && $scope.userSelected.userID == msg.from){		
				addMessage(false, false, $scope.userSelected.name, msg.message, moment().format('DD/MM/YYYY hh:mm:ss'));
				scrollToEnd();
			} else {
				for(var i = 0; i < $scope.users.length; i++){
					var user = $scope.users[i];
					if(user.userID == msg.from){
						var idIcon = 'iconNotification'+i;
						$('#'+idIcon).remove();
						$('#'+i).append('<span id="'+idIcon+'" class="badge bg-color-green bounceIn animated pull-right inbox-badge"><i class="fa fa-md fa-envelope-o"></i></span>');
						break;
					}
				}
			}
			
		});
		
	}]);
})();
