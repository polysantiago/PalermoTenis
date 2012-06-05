function secondOptionCallback(){    
    var tipoPresentacionId = $("#tipoPresentacion option:selected").val();
    $("#loader").show();
    $("#tipoPresentacionId").val(tipoPresentacionId);
    $("#content").load("/admin/crud/PresentacionAction_show",{
        tipoPresentacionId:tipoPresentacionId
    },resizeAndHideLoader);
}

function resizeAndHideLoader(){
    $("#loader").hide();
    if($("#content").width() < $("#content table.list").width()){
        var origContentWidth = parseFloat($("#content").width());
        $("#content").width(parseFloat($("#content table.list").width())*1.1);
        $("#main").width((parseFloat($("#content table.list").width()) - origContentWidth) + parseFloat($("#main").width()));
    }
    if(typeof resize == 'function')resize();
}

$(document).ready( function(){
    $("#btnEditarTiposPresentacion").attr("disabled","true");
    $(".funciones button").button();
    $("#tipoProducto,#tipoPresentacion").selectmenu({
        style:'dropdown'
    });
    $.getJSON("/PrepararPresentaciones",{
        productoId:$("#productoId").val()
    },function(response){
        $("#tipoProducto").doubleSelect('tipoPresentacion', response.tiposProducto, {
            preselectFirst: response.preselectFirst,
            emptyValue: "--- Seleccionar ---"
        }).change( function(){
            $("#tipoPresentacion").selectmenu('destroy').selectmenu({
                style:'dropdown'
            });
        });
        $("#tipoPresentacion").change(secondOptionCallback);
        $("#btnEditarTiposPresentacion").button('destroy').removeAttr("disabled").button();
        $("#tipoProducto,#tipoPresentacion").selectmenu('destroy').selectmenu({
            style:'dropdown'
        });
    });
    $("#btnEditarTiposPresentacion").click( function(){
        var tipoProductoId = $("#tipoProducto option:selected").val();
        $("#loader").show();
        $("#tipoProductoId").val(tipoProductoId);
        $("#content").load("/admin/crud/TipoPresentacionAction_show",{
            tipoProductoId:tipoProductoId
        },resizeAndHideLoader);
    });
});