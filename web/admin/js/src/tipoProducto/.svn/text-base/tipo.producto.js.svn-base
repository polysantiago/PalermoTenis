function updateTipoProducto(event, ui) {
    var tr = $(event.target).closest("tr");
    $.ajax({
        type: "POST",
        url: "/admin/crud/TipoProductoAction_edit",
        data: {
            tipoProductoId: tr.attr("val"),
            presentable:    tr.find("td.presentable input").is(":checked"),
            nombre:         (ui) ? ui.value : tr.find("td.nombre span").text()
        },
        success: function(response){
            if(response=='OK'){
                tr.animate({
                    backgroundColor: "#66FF66"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);
            } else {
                alert(response);
                tr.animate({
                    backgroundColor: "red"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);
            }
        }
    });
}

function borrarTipoProducto(tipoProductoId){
    if(confirm("¿Está seguro que desea borrar este tipo de producto?")){
        $.ajax({
            type: "POST",
            url: "/admin/crud/TipoProductoAction_destroy",
            data: {
                tipoProductoId: tipoProductoId
            },
            success: function(response){
                if(response=='OK') {
                    $("tr.tipoProducto[val='"+tipoProductoId+"']").remove();
                    $("#container").empty();
                } else {
                    alert(response);
                }
            }
        });
    }
}

function agregarTipoProducto(event){
    var tr = $(event.target).closest("tr");

    var tipoProductoObj = {
        nombre:      tr.find("td.nombre input").val(),
        presentable: tr.find("td.presentable :checkbox").is(":checked")
    };

    if(!tipoProductoObj.nombre){
        alert("Por favor, ingrese un nombre");return;
    }

    $.ajax({
        type: "POST",
        url: "/admin/crud/TipoProductoAction_create",
        data: tipoProductoObj,
        success: function(response){
            if(response=='OK') window.location.href = "TipoProductoAction_show";
        }
    });
}

function agregarTipoAtributo(event){
    var tr = $(event.target).closest("tr");

    var tipoAtributoObj = {
        tipoProductoId: $("span.selected:first").closest("tr").attr("val"),
        nombre:         tr.find("td.nombre input").val(),
        clase:          tr.find("td.clase select option:selected").val(),
        unidadId:       tr.find("td.unidad select option:selected").val(),
        clasif:         tr.find("td.clasif select option:selected").val()
    };

    if(!tipoAtributoObj.nombre){
        alert("Por favor, ingrese un nombre");return;
    } else if(tipoAtributoObj.clase == -1){
        alert("Por favor, seleccione un formato");return;
    } else if(tipoAtributoObj.clasif == -1){
        alert("Por favor, seleccione un tipo");return;
    }

    if(!tipoAtributoObj.unidadId) tipoAtributoObj.unidadId = "";

    $.ajax({
        type: "POST",
        url: "/admin/crud/TipoAtributoAction_create",
        data: tipoAtributoObj,
        success: function(response){
            if(response=='OK'){
                $("span.selected").click();
            }
        }
    });
}

function loadTipoAtributos(event){
    var self = $(event.target);

    if(self.is("input") || self.hasClass("ui-icon")) return;

    $("#loader").show();

    $("tr.tipoProducto td.nombre span.selected").removeClass("selected");
    self.addClass("selected");

    $("#container").load("/admin/crud/TipoAtributoAction_show",{
        tipoProductoId: self.closest("tr").attr("val")
    }, function(){
        $("#btnAgregarTipoAtributo").button('destroy').removeAttr("disabled").button();
        $("#loader").hide();
    });
}

function fetchList(url,data){
    var list = [];
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        data: data,
        success: function(data) {
            list=data
        },
        async: false
    });
    return list;
}

$(document).ready( function(){
    $("#btnAgregarTipoAtributo").attr("disabled","true");
    $(".btn").button();
    $("tr.tipoProducto td.presentable input[type='checkbox']").checkbox({
        empty:'/css/images/empty.png',
        cls:'jquery-safari-checkbox'
    });

    $(".tipoProducto td.nombre span").bind('click',loadTipoAtributos);

    $("tr.tipoProducto td.presentable input").bind("check",updateTipoProducto).bind("uncheck",updateTipoProducto);

    $("tr.tipoProducto td.nombre span").inlineEdit({
        saveButton: 'Guardar',
        cancelButton: 'Cancelar',
        save: updateTipoProducto
    });

    $("#btnAgregarTipoProducto").click( function(){
        var nombre = $("<input>").attr("size",10);
        var presentable = $("<input type='checkbox'>");
        var add = $("<div>").addClass("addBtn").attr("title","Agregar").bind('click',agregarTipoProducto);
        $("#tpFieldset table tbody").append($("<tr>")
            .append($("<td>").addClass("nombre").append(nombre))
            .append($("<td>").addClass("presentable").append(presentable))
            .append($("<td>").append(add)));
    });

    $("#btnAgregarTipoAtributo").click( function(){

        var unidadesJson = fetchList("/admin/crud/UnidadAction_getJson");
        var clasificacionesJson = fetchList("/GetClasificaciones");

        var clasesJson = [
        {
            id: 'java.lang.Double',
            text: 'Decimal'
        },

        {
            id: 'java.lang.Integer',
            text: 'Entero'
        },

        {
            id: 'java.lang.Boolean',
            text: 'Binario'
        },

        {
            id: 'java.lang.String',
            text: 'Texto'
        }
        ];
        
        var unidades = $("<select>");
        $("<option>").appendTo(unidades);
        $.each(unidadesJson,function(){
            $("<option>").html(this.descripcion).val(this.id).appendTo(unidades);
        });

        var clasificaciones = $("<select>");
        $("<option>").html("-- Seleccionar --").val(-1).appendTo(clasificaciones);
        $.each(clasificacionesJson,function(){
            $("<option>").html(this.nombre).val(this.clasif).appendTo(clasificaciones)  ;
        });

        var clases = $("<select>");
        $("<option>").html("-- Seleccionar --").val(-1).appendTo(clases);
        $.each(clasesJson,function(){
            $("<option>").html(this.text).val(this.id).appendTo(clases);
        });

        var add = $("<div>").addClass("addBtn").attr("title","Agregar").bind('click',agregarTipoAtributo);

        var nombre = $("<input>").attr("size",10);
        $("#tipoAtributoTable tbody").append($("<tr>")
            .append($("<td>").addClass("nombre").append(nombre))
            .append($("<td>").addClass("clase").append(clases))
            .append($("<td>").addClass("unidad").append(unidades))
            .append($("<td>").addClass("clasif").append(clasificaciones))
            .append($("<td>").append(add)));
    });

    $("#btnEditarUnidades").click( function(){
        $("#container").load("/admin/crud/UnidadAction_show");
    });
});