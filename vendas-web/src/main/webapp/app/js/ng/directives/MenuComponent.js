'use strict';

vendasApp.directive('menuComponent',[ 'UserService', function (UserService) {
    return  {
    	restrict : 'E',
    		
		template: function(){
			var user = UserService.getUser();
			
			if(user){
				var menuHtml = '<navigation>';
				var i;
				for(i in user.menusApplication){				
					var menu = user.menusApplication[i];
					
					//Se for um menu pai
					if(!menu.submenu){
						//Se o menu pai tiver menus filhos
						if(menu.childrenMenu.length > 0){
							//Inicia o pai
							menuHtml += '<nav:group data-icon="'+menu.icon+'" title="'+menu.label+'" >';
							var childrenMenu = menu.childrenMenu;
							var j;
							for(j in childrenMenu){
								var childMenu = childrenMenu[j];
								//Insere o filho
								menuHtml += '<nav:item data-view="'+childMenu.url+'" data-icon="'+childMenu.icon+'" title="'+childMenu.label+'" />';
							}
							//Encerra o pai
							menuHtml += '</nav:group>';
						} else {
							menuHtml += '<nav:item data-view="'+menu.url+'" data-icon="'+menu.icon+'" title="'+menu.label+'" />';
						}
					}
				}
				menuHtml +='</navigation>';
				return menuHtml;
			
			}
		}
	};
}]);
 
