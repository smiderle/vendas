'use strict';

vendasApp.factory('LocalStorageService',function(){
	var addToLocalStorage = function (key, value) {
		localStorage.setItem(key, JSON.stringify(value));
	};
	
	var getFromLocalStorage = function (key) {
		return JSON.parse(localStorage.getItem(key));
	};
	
	
	var clear=  function(){
		localStorage.clear();
	};


	return {
		addToLocalStorage : addToLocalStorage,
		getFromLocalStorage: getFromLocalStorage,
		clear : clear
	};	
});
