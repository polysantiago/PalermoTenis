function fieldsAreValid(pagoObj){
    if(!pagoObj.nombre || pagoObj.nombre == ""){
        error("El campo nombre no puede estar vacío");
        return false;
    }
    return true;
}

var elements = [{
    element: $("<input>"),
    attr: [{
        key: "name",
        value: "nombre"
    }]
}];

var editURL     = "/admin/crud/PagoAction_edit";
var destroyURL  = "/admin/crud/PagoAction_destroy";
var createURL   = "/admin/crud/PagoAction_create";

$(document).ready( function(){
    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        agregar(elements);
    });
});