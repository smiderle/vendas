'use strict';

vendasApp.factory('UtilityService',function(){

	return {

		/**
		 * Retorna um cookie.
		 * @param fields
		 * @returns
		 */
		getCookie : function(cname) {
			var name = cname + "=";
			var ca = document.cookie.split(';');
			for(var i=0; i<ca.length; i++) {
				var c = ca[i];
				while (c.charAt(0)==' ') c = c.substring(1);
				if (c.indexOf(name) != -1) {
					return c.substring(name.length, c.length);
				}
			}
			return "";
		},


		/**
		 * Seta um cookie.
		 * @param fields
		 * @returns
		 */
		setCookie : function(cname, cvalue) {
			var d = new Date();
			//1 Ano
			d.setTime(d.getTime() + (365 * 24 * 60 * 60));		    
			var expires = "expires=" + d.toGMTString();					    
			document.cookie = cname+"="+cvalue+"; "+expires;
		},

		showAlertSucess : function(title, content){
			$.smallBox({
				title : title,
				content : "<i class='fa fa-clock-o'></i> <i>"+ content+"</i>",
				color : "#659265",
				iconSmall : "fa fa-thumbs-up bounce animated",
				timeout : 4000
			});						
		},
		
		showAlertError: function(title, content){
			$.smallBox({
				title : title,
				content : content,
				color : "#C46A69",
				iconSmall : "fa fa-times fa-2x fadeInRight animated",
				timeout : 12000
			});
		},
		
		showAlertWarning: function(title, content, time){
			$.smallBox({
				title : title,
				content : content,
				color : "#C79121",
				iconSmall : "fa fa-warning shake animated",
				timeout : time || 10000
			});
		},		
	};	
});
