'use strict';

vendasApp.directive('menuComponent',[ 'ContextService', function (ContextService) {
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
							menuHtml += '<nav:group data-icon="'+menu.icon+'" title="'+menu.label+'" >';
							var childrenMenu = menu.childrenMenu;
								
							childrenMenu.forEach(function(childMenu){
								
								//Verifica se o usuario tem acesso ao filho
								var i;
								for(i in menusApplication){
									if(childMenu.menuID == menusApplication[i].menuID){
										//Insere o filho
										menuHtml += '<nav:item data-view="'+childMenu.url+'" data-icon="'+childMenu.icon+'" title="'+childMenu.label+'" />';
										break;
									}
								}
								
							});
							
							//Encerra o pai
							menuHtml += '</nav:group>';
						} else {
							menuHtml += '<nav:item data-view="'+menu.url+'" data-icon="'+menu.icon+'" title="'+menu.label+'" />';
						}
					}
				
					
				});
				menuHtml +='</navigation>';
				return menuHtml;
			
			}
		}
	};
}]);
 
