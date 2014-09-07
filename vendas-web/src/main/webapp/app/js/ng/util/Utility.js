'use strict';

vendasApp.factory('Utility',function(){
	function Asc(String){
		return String.charCodeAt(0);
	}

	function Chr(AsciiNum){
		return String.fromCharCode(AsciiNum)
	}


	return {		

		encrypt : function(data){
			if(data){
				var mensx="";
				var l;
				var i;
				var j=0;
				var ch;
				ch = "assbdFbdpdPdpfPdAAdpeoseslsQQEcDDldiVVkadiedkdkLLnm";	
				for (i=0;i<data.length; i++){
					j++;
					l=(Asc(data.substr(i,1))+(Asc(ch.substr(j,1))));
					if (j==50){
						j=1;
					}
					if (l>255){
						l-=256;
					}
					mensx+=(Chr(l));
				}
				return mensx;
			} else {
				return null;
			}
		},

		decrypt: function(data){
			if(data){
				var mensx="";
				var l;
				var i;
				var j=0;
				var ch;
				ch = "assbdFbdpdPdpfPdAAdpeoseslsQQEcDDldiVVkadiedkdkLLnm";	
				for (i=0; i<data.length;i++){
					j++;
					l=(Asc(data.substr(i,1))-(Asc(ch.substr(j,1))));
					if (j==50){
						j=1;
					}
					if (l<0){
						l+=256;
					}
					mensx+=(Chr(l));
				}	
				return mensx;
			} else {
				return null;
			}

		}
	}
});