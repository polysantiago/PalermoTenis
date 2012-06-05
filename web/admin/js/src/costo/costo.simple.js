function fieldsAreValid(costoObj){
    if(costoObj.proveedorId == -1){
        error("Por favor, selecciona un proveedor");
        return false;
    } else if(costoObj.monedaId == -1){
        error("Por favor, selecciona una moneda");
        return false;
    } else if(!costoObj.costoVal || costoObj.costoVal == "" || isNaN(costoObj.costoVal)){
        error("Por favor, verifica el valor del costo");
        return false;
    }
    return true;
}

var editURL     = "/admin/crud/CostoAction_edit";
var destroyURL  = "/admin/crud/CostoAction_destroy";
var createURL   = "/admin/crud/CostoAction_create";

$(document).ready( function(){

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");

        var proveedores = [];
        var monedas     = [];

        $.each(fetchList("/admin/crud/ProveedorAction_list"), function(){
            proveedores.push({
                id: this.id,
                value: this.nombre
            });
        });
        $.each(fetchList("/admin/crud/MonedaAction_list"), function(){
            monedas.push({
                id: this.id,
                value: this.simbolo
            });
        });

        var elements = [
        {
            element: $("<select>"),
            data: monedas,
            attr: [{
                key: "name",
                value: "monedaId"
            }]
        }, {
            element: $("<input>"),
            attr: [{
                key: "size",
                value: 3
            },{
                key: "name",
                value: "costoVal"
            }]
        }, {
            element: $("<select>"),
            data: proveedores,
            attr: [{
                key: "name",
                value: "proveedorId"
            }]
        }
        ];
        agregar(elements);
    });
});