function fieldsAreValid(obj){
    var moneda = obj.newMonedaId || obj.monedaId;
    var pago = obj.newPagoId || obj.pagoId;

    if(moneda == -1){
        error("Por favor, seleccione una moneda");
        return false;
    } else if(pago == -1){
        error("Por favor, seleccione un m\u00E9todo de pago");
        return false;
    } else if(obj.enOferta && (!obj.valorOferta || isNaN(obj.valorOferta))){
        error("Por favor, verifique la oferta ingresada");
        return false;
    } else if($("input[name='enOferta']:checked").length > 1){
        error("No puede haber m\u00E1s de dos ofertas para un mismo producto");
        return false;
    }
    return true;
}

function beforeUpdate(tr,obj){
    var oldCuotas = obj.newCuotas;
    var oldMoneda = obj.newMonedaId;
    var oldPago = obj.newPagoId;

    obj.newCuotas = obj.cuotas;
    obj.newMonedaId = obj.monedaId;
    obj.newPagoId = obj.pagoId;

    obj.cuotas = oldCuotas;
    obj.monedaId = oldMoneda;
    obj.pagoId = oldPago;
}

function afterUpdate(tr,obj){
    tr.find("td.moneda").attr("val",obj.newMonedaId);
    tr.find("td.pago").attr("val",obj.newPagoId);
    tr.find("td.cuotas").attr("val",obj.newCuotas);
}

function enableOferta(event){
    $(event.target).closest("tr").find("input[name='valorOferta']").removeAttr("disabled").removeClass("ui-state-disabled");
}

function disableOferta(event){
    $(event.target).closest("tr").find("input[name='valorOferta']").attr("disabled","true").addClass("ui-state-disabled");
}

var editURL     = "/admin/crud/PrecioAction_edit";
var destroyURL  = "/admin/crud/PrecioAction_destroy";
var createURL   = "/admin/crud/PrecioAction_create";

$(document).ready( function(){
    $("td.enOferta input").bind("check",enableOferta).bind("uncheck",disableOferta);

    $("#btnAgregarNuevo").click( function(){
        $(this).removeClass("ui-state-focus ui-state-hover");

        var monedas = [];
        var pagos   = [];

        $.each(fetchList("/admin/crud/MonedaAction_list"), function(){
            monedas.push({
                id: this.id,
                value: this.simbolo
            });
        });

        $.each(fetchList("/admin/crud/PagoAction_list"), function(){
            pagos.push({
                id: this.id,
                value: this.nombre
            });
        });

        var chbxEnOferta = $("<input>").bind("check",enableOferta).bind("uncheck",disableOferta);

        var elements = [
        {
            element: $("<select>"),
            data: monedas,
            attr: [{
                key: "name",
                value: "monedaId"
            }]
        },
        {
            element: $("<select>"),
            data: pagos,
            attr: [{
                key: "name",
                value: "pagoId"
            }]
        },
        {
            element: $("<input>"),
            attr: [{
                key: "size",
                value: 1
            },{
                key: "name",
                value: "cuotas"
            }]
        },
        {
            element: $("<input>"),
            attr: [{
                key: "size",
                value: 4
            },{
                key: "name",
                value: "valor"
            }]
        },
        {
            element: chbxEnOferta,
            attr: [{
                key: "type",
                value: "checkbox"
            }, {
                key: "name",
                value: "enOferta"
            }]
        },
        {
            element: $("<input>"),
            attr: [{
                key: "size",
                value: 4
            },{
                key: "name",
                value: "valorOferta"
            },{
                key: "disabled",
                value: "true"
            },{
                key: "class",
                value: "ui-state-disabled"
            }]
        }
        ];
        agregar(elements);
    });
});