$(document).ready( function(){
    if($("#panel input.chkbox").is(":checked")){
        enableReorder("/admin/crud/PrecioAction_move");
    }
    $("#panel input.chkbox").bind("check",function(){
        enableReorder("/admin/crud/PrecioAction_move");
    });
});

function getData(ui){
    return {
        productoId: $(ui.item).find(".productoId").val(),
        productoOrden: $(ui.item).find(".productoOrden").val(),
        productoRgtId: $(ui.item).next().find(".productoId").val(),
        productoRgtOrden: $(ui.item).next().find(".productoOrden").val()
    };
}