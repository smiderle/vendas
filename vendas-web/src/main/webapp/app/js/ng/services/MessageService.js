'use strict';

vendasApp.factory('MessageService', 
		['RestangularNode', 
		function(RestangularNode){
	
	return {
		/**
		 * Retorna as mensages entre os usuários
		 */
		getMessages: function( from, to, offset ){
        	return RestangularNode.all('v1').all("message").all("from/" + from+ "/to/"+to+"/offset/"+offset).getList().then(function(result){
				var p = {};
				p.code = result.code;
				p.value = result.value;
				p.rowCount = result.rowCount;
				return p;
			});
        },
        
        /**
         * Retorna os usuários que possuim nova mensagem enviada par o usuário logado
         */
        getUsersWithNewMessage: function( to ){
        	return RestangularNode.all('v1').all("message").all("findUsersWithNewMessage/to/" +to).getList().then(function(result){
				var p = {};
				p.code = result.code;
				p.value = result.value;
				p.rowCount = result.rowCount;
				return p;
			});
        },
        
        getMessagesUnreadCount: function( to ){
        	return RestangularNode.all('v1').all("message").all("messagesUnreadCount/to/" +to).getList().then(function(result){
				var p = {};
				p.code = result.code;
				p.value = result.value;
				p.rowCount = result.rowCount;
				return p;
			});
        } 
	};
}]);