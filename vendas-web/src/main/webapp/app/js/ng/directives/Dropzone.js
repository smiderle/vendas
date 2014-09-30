'use strict';

vendasApp.directive('dropzone', function () {
  return function (scope, element, attrs) {
    var config, dropzone;
 
    config = scope[attrs.dropzone];
    config.options.acceptedFiles = '.jpeg,.jpg,.png,.gif,.JPEG,.JPG,.PNG,.GIF',
    config.options.dictFileTooBig = 'A imagem é muito grande ({{filesize}} MB). O tamanho máximo é {{maxFilesize}} MB';
    config.options.dictInvalidFileType = "Arquivo inválido. Selecione uma imagem com as seguintes extensões jpeg, jpg, png ou gif  ";
 
    // create a Dropzone for the element with the given options
    dropzone = new Dropzone(element[0], config.options);
 
    // bind the given event handlers
    angular.forEach(config.eventHandlers, function (handler, event) {
      dropzone.on(event, handler);
    });
  };
});