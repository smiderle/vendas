'use strict';

/*****************************************************************************/
/**    Author: Ladair C. Smiderle Junior - ladairsmiderle@gmail.com        **/
/***************************************************************************/

/**
 * Retorna a data formatada. Utilizado normalmente nos ng-repeat, para formatar as datas no grid
 * Fonte: https://stackoverflow.com/questions/24960489/angular-js-date-format-inside-ng-repeat/24960708#24960708
 */
vendasApp.filter('cmdate', [
    '$filter', function($filter) {
        return function(input, format) {
        	if(typeof input === "string"){
        		input = input.replace('-', '/').replace('-', '/');
        	}
            return $filter('date')(new Date(input), format);
        };
    }
]);