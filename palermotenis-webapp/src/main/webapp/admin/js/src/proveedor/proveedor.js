function fieldsAreValid(proveedorObj){
    if(!proveedorObj.nombre || proveedorObj.nombre == ""){
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

var editURL     = "/admin/crud/ProveedorAction_edit";
var destroyURL  = "/admin/crud/ProveedorAction_destroy";
var createURL   = "/admin/crud/ProveedorAction_create";

$(document).ready( function(){
    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        agregar(elements);
    });
});