function fieldsAreValid(costoObj){
    if(costoObj.tipoPresentacionId == -1){
        error("Por favor, selecciona un tipo de presentacion");
        return false;        
    } else if(costoObj.presentacionId == -1){
        error("Por favor, selecciona una presentacion");
        return false;        
    } else if(costoObj.proveedorId == -1){
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

function fillPresentacionesByTipo(event){
    var element = $(event.target);
    var tipoPresentacionId = element.val();
    var select = element.closest("tr").find("select[name='presentacionId']");

    var presentaciones = fetchList("/GetPresentacionesByTipo", {
        tipoPresentacionId: tipoPresentacionId
    });

    select.empty().selectmenu('destroy');
    $("<option>").html("-- Seleccionar --").attr("value",-1).appendTo(select);
    $.each(presentaciones,function(){
        $("<option>").html(this.nombre).attr("value",this.id).appendTo(select);
    });
    select.selectmenu({style:'dropdown'});
}

var editURL     = "/admin/crud/CostoAction_edit";
var destroyURL  = "/admin/crud/CostoAction_destroy";
var createURL   = "/admin/crud/CostoAction_create";

$(document).ready( function(){
    $("td.tipoPresentacion select").bind("change",fillPresentacionesByTipo);

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        
        var tipoProductoId = $("#tipoProductoId").val();

        var tiposPresentacion = [];
        var proveedores = [];
        var monedas = [];

        $.each(fetchList("/admin/crud/TipoPresentacionAction_listByTipoProducto",{
            tipoProductoId: tipoProductoId
        }), function(){
            tiposPresentacion.push({
                id: this.id,
                value: this.nombre
            });
        });

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

        var tipoPresentacionSelect = $("<select>").bind("change",fillPresentacionesByTipo);

        var elements = [
        {
            element: tipoPresentacionSelect,
            data: tiposPresentacion,
            attr: [{
                key: "name",
                value: "tipoPresentacionId"
            }]
        },
        {
            element: $("<select>"),
            attr: [{
                key: "name",
                value: "presentacionId"
            }]
        },
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