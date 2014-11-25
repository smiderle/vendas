'use strict';

vendasApp.directive('menuComponent',[ 'ContextService', 'MessageService','$timeout' , function (ContextService, MessageService, $timeout) {
    return  {
    	restrict : 'E',
    		
		template: function(){
			var user = ContextService.getUserLogged();
			
			if(user){
				var menusApplication = user.menusApplication;
				
				var menuHtml = '<navigation>';
				
				menusApplication.forEach(function(menu){					
					//Se for um menu pai
					if(!menu.submenu){
						//Se o menu pai tiver menus filhos
						if(menu.childrenMenu.length > 0){
							//Inicia o pai
							menuHtml += '<nav:group data-icon="'+menu.icon+'" title="'+menu.label+'" id="menu_'+menu.menuID+'" class="fadeInLeft animated" >';
							var childrenMenu = menu.childrenMenu;
								
							childrenMenu.forEach(function(childMenu){
								
								//Verifica se o usuario tem acesso ao filho
								var i;
								for(i in menusApplication){
									if(childMenu.menuID == menusApplication[i].menuID){
										//Insere o filho
										menuHtml += '<nav:item data-view="'+childMenu.url+'" id="menu_'+childMenu.menuID+'" data-icon="'+childMenu.icon+'" title="'+childMenu.label+'"  />';
										break;
									}
								}
								
							});
							
							//Encerra o pai
							menuHtml += '</nav:group>';
						} else {
							menuHtml += '<nav:item data-view="'+menu.url+'" data-icon="'+menu.icon+'" title="'+menu.label+'" id="menu_'+menu.menuID+'" class="fadeInLeft animated"/>';
						}
					}
				
					
				});
				menuHtml +='</navigation>';
				
				//É setado um tempo para garantir, que ira chamar/retornar o serviço depois que carregar o menu
				$timeout(function(){
					var cMessages = MessageService.getMessagesUnreadCount(user.userID);
					cMessages.then(function(toReturn){
						if(toReturn.code === '200' && toReturn.value > 0){
							
							
							$('#iconMenuNewMessage').remove();
							//Adiciona o icone de nova mensage no lado do meu Mensagens
							$('#menu_18 a').append('<span id="iconMenuNewMessage" class="badge bg-color-green bounceIn animated pull-right inbox-badge"><i class="fa fa-md fa-envelope-o"></i></span>');
						}
					});
				}, 100);
				
				
				
				return menuHtml;
			
			}
		}
	};
}]);
 
