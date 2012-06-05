var defaultAction = function(){
    $(this).dialog('close');
};

function error(text){
    $("#dialog-error span.ui-state-error-text").html(text);
    $("#dialog-error").dialog('open');
}

function confirmar(options){
    $("#dialog-confirm span.confirm-text").html(options.text);
    $("#dialog-confirm").dialog("option", "buttons",{
        "S\u00ED, estoy seguro": options.confirm || defaultAction,
        "No, mejor no": options.cancel || defaultAction
    }).dialog('open');
}

function crearFormulario(options){
    $("#dialog-form div.validateTips span.tipsText").html(options.initialText);
    $.each(options.elements, function(){
        $("#dialog-form fieldset").empty().append(
            $("<label>").attr("for",this.id).html(this.label)).append(
            this.field.addClass("text ui-widget-content ui-corner-all"));
    });
    $("#dialog-form").dialog("option", "title", options.title).dialog('option','buttons', {
        Aceptar: options.aceptarFunction,
        Cancelar: options.cancelarFunction
    }).dialog('open');
}

function updateTips(t) {
    $("#dialog-form div.validateTips").text(t).addClass("ui-state-highlight");
    setTimeout(function() {
        $("#dialog-form div.validateTips").removeClass( "ui-state-highlight",1500);
    },500);
}

function checkLength(element,label,min,max) {
    if (element.val().length > max || element.val().length < min) {
        element.addClass("ui-state-error");
        updateTips("La longitud del " + label + " debe tener entre " + min + " y " + max + " caracteres.");
        return false;
    } else {
        return true;
    }
}

$(document).ready(function(){
    $("#dialog-error").dialog({
        autoOpen: false,
        buttons: {
            OK: function(){
                $(this).dialog('close');
            }
        }
    });
    $("#dialog-confirm,#dialog-form").dialog({
        autoOpen: false,
        modal: true
    });    
});