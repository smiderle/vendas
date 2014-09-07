'use strict';

vendasApp.factory('LocalStorageService',function(Utility){
	var addToLocalStorage = function (key, value) {
		localStorage.setItem(key, JSON.stringify(value));
	};
	
	var addToLocalStorageCrypt = function (key, value) {		
		localStorage.setItem(key, Utility.encrypt(JSON.stringify(value)));
	};
	
	var getFromLocalStorage = function (key) {
		return JSON.parse(localStorage.getItem(key));
	};
	
	var getFromLocalStorageDecrypt = function (key) {
		return JSON.parse(Utility.decrypt(localStorage.getItem(key)));
	};
	
	
	var clear=  function(){
		localStorage.clear();
	};


	return {
		addToLocalStorage : addToLocalStorage,
		addToLocalStorageCrypt: addToLocalStorageCrypt,
		getFromLocalStorage: getFromLocalStorage,
		getFromLocalStorageDecrypt: getFromLocalStorageDecrypt,
		clear : clear
	};	
});
