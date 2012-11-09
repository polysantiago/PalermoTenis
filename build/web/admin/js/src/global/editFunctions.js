/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready( function(){

    var loc = escape(window.location);

    $("#btnEditarMonedas").click( function(){
        window.location.href = "/admin/crud/MonedaAction_show?redirect="+loc;
    });

    $("#btnEditarPagos").click( function(){
        window.location.href = "/admin/crud/PagoAction_show?redirect="+loc;
    });

    $("#btnEditarProveedores").click( function(){
        window.location.href = "/admin/crud/ProveedorAction_show?redirect="+loc;
    });

    $("#btnEditarPresentaciones").click( function(){
        window.location.href = "/admin/objetos/Presentacion/Presentaciones.jsp?productoId="+$("#productoId").val()+"&redirect="+loc;
    });

});

