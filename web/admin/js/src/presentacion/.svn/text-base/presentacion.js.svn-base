function fieldsAreValid(presentacionObj){
    if(!presentacionObj.cantidad || presentacionObj.cantidad == ""){
        error("El campo cantidad no puede estar vacío");
        return false;
    } else if(isNaN(presentacionObj.cantidad)){
        error("El campo cantidad no es válido");
        return false;
    } else if(!presentacionObj.unidad || presentacionObj.unidad == ""){
        error("El campo unidad no puede estar vacío");
        return false;
    } else if((presentacionObj.nombre == "" || !presentacionObj.nombre)
        && !confirm("El campo nombre est\u00E1 vac\u00Eo. \u00BFQuiere aplicar el nombre por default?")){
        return false;
    }
    return true;
}

var editURL     = "/admin/crud/PresentacionAction_edit";
var destroyURL  = "/admin/crud/PresentacionAction_destroy";
var createURL   = "/admin/crud/PresentacionAction_create";

$(document).ready( function(){

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        var elements = [
        {
            element: $("<input>"),
            attr: [{
                key: "size",
                value: 4
            },{
                key: "name",
                value: "cantidad"
            }]
        },
        {
            element: $("<input>"),
            attr: [{
                key: "name",
                value: "unidad"
            }]
        },
        {
            element: $("<input>"),
            attr: [{
                key: "name",
                value: "nombre"
            }]
        }
        ];
        agregar(elements);
    });
});