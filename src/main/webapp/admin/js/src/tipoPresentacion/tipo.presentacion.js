function fieldsAreValid(tipoPresentacionObj){
    if((tipoPresentacionObj.nombre == "" || !tipoPresentacionObj.nombre)){
        error("Por favor, ingrese un nombre");
        return false;
    }
    return true;
}

function afterDelete(tr,obj){
    $("#tipoPresentacion option[value='"+obj.tipoPresentacionId+"']").remove();
}

var editURL     = "/admin/crud/TipoPresentacionAction_edit";
var destroyURL  = "/admin/crud/TipoPresentacionAction_destroy";
var createURL   = "/admin/crud/TipoPresentacionAction_create";

$(document).ready( function(){

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        var elements = [
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