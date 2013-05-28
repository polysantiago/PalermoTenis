function getData(ui){
    return {
        modeloId: $(ui.item).find(".modeloId").val(),
        modeloOrden: $(ui.item).find(".modeloOrden").val(),
        modeloRgtId: $(ui.item).next().find(".modeloId").val(),
        modeloRgtOrden: $(ui.item).next().find(".modeloOrden").val()
    };
}

define(["jquery", "app/admin"], function($){
	$(function(){
	    if($("#panel input.chkbox").is(":checked")){
	        enableReorder("/admin/crud/ModeloAction_move");
	    }
	    $("#panel input.chkbox").bind("check",function(){
	        enableReorder("/admin/crud/ModeloAction_move");
	    });
	});
});