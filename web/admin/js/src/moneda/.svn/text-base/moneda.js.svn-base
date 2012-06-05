function fieldsAreValid(monedaObj){
    if(monedaObj.contrarioId == monedaObj.monedaId){
        error("La moneda contraria no puede ser la misma");
        return false;
    } else if(monedaObj.contrarioId == -1){
        error("Por favor, selecciona una moneda contraria");
        return false;
    } else if(!monedaObj.simbolo || monedaObj.simbolo == ""){
        error("El campo simbolo no puede estar vacío");
        return false;
    } else if(!monedaObj.codigo || monedaObj.codigo == ""){
        error("El campo codigo no puede estar vacío");
        return false;
    } else if(!monedaObj.nombre || monedaObj.nombre == ""){
        error("El campo nombre no puede estar vacío");
        return false;
    }
    return true;
}

var editURL     = "/admin/crud/MonedaAction_edit";
var destroyURL  = "/admin/crud/MonedaAction_destroy";
var createURL   = "/admin/crud/MonedaAction_create";

$(document).ready(function(){

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        var contrarios = [];

        $.each(fetchList("/admin/crud/MonedaAction_list"), function(){
            contrarios.push({
                id: this.id,
                value: this.simbolo
            });
        });

        var elements = [
        {
            element: $("<input>"),
            data: [],
            attr: [{
                key: "size",
                value: 3
            },{
                key: "name",
                value: "simbolo"
            }]
        }, {
            element: $("<input>"),
            data: [],
            attr: [{
                key: "size",
                value: 3
            },{
                key: "name",
                value: "codigo"
            }]
        }, {
            element: $("<input>"),
            data: [],
            attr: [{
                key: "name",
                value: "nombre"
            }]
        }, {
            element: $("<select>"),
            data: contrarios,
            attr: [{
                key: "name",
                value: "contrarioId"
            }]
        }
        ];
        agregar(elements);
    });
});