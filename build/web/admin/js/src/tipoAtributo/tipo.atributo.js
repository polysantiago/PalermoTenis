function getValoresPosibles(tipoAtributoId){
    $("#loader").show();
    var tipoAtributo = $("tr[val='"+tipoAtributoId+"'].tipoAtributo");
    $("tr.tipoAtributo span.selected").removeClass("selected");
    var tipoAtributoNombre = tipoAtributo.find("span.nombre").addClass("selected").text();
    var tipoProductoNombre = $(".tipoProducto td.nombre a.selected").text();
    var datos = {
        tipoAtributoId: tipoAtributoId,
        tipoAtributoNombre: tipoAtributoNombre,
        tipoProductoNombre: tipoProductoNombre
    };
    $("#valoresPosibles").hide().load("/admin/crud/ValorPosibleAction_show",datos, function(){
        $(this).show("slow");
        $("#loader").hide();
    });

}

function updateNombreTipoAtributo(input, newNombre){

    var tr = input.parents("tr:first");
    var tipoAtributoId = tr.attr("val");
    var orig = tr.find("span.nombre");
    var origText = orig.text();

    var datos = {
        tipoAtributoId: tipoAtributoId,
        nombre: newNombre
    };

    $.ajax({
        type: "POST",
        url: "/admin/crud/TipoAtributoAction_editName",
        data: datos,
        success: function(response){
            if(response=='OK'){
                orig.show("slow")
                .text(newNombre.toString())
                .animate({
                    backgroundColor: "#66FF66"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);
            } else {
                alert(response);
                orig.show("slow")
                .text(origText)
                .animate({
                    backgroundColor: "red"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);

            }
        }
    });
}

function updateData(event){
    var tr = $(event.target).closest("tr");
    var tipoAtributoId = tr.attr("val");
    var clase = tr.find("select.clases").val();
    var unidadId = tr.find("select.unidad").val();
    var clasif = tr.find("select.clasif").val();

    if(clase == 'java.lang.Boolean' && clasif != 'S'){
        alert("El valor binario solo puede del tipo simple");
        return false;
    }

    if(clase == 'java.lang.Integer' && tr.find("select.clases").closest("td").attr("val") == 'java.lang.Double'
        && !confirm("Una posible pérdida de precisión podría ocurrir al cambiar de decimal a formato entero.\n¿Desea continuar de todos modos?")){
        var clases = tr.find("select.clases");
        clases.selectmenu('destroy').val(clases.closest("td").attr("val")).selectmenu();
        return false;
    }

    var datos = {
        tipoAtributoId: tipoAtributoId,
        clase: clase,
        unidadId: unidadId,
        clasif: clasif
    };

    $.ajax({
        type: "POST",
        url: "/admin/crud/TipoAtributoAction_edit",
        data: datos,
        success: function(response){
            if(response=='OK'){
                var span = tr.find(".nombre");
                var parent = span.parent();
                if(parent.is("a") && clasif == 'S'){
                    span.parent().replaceWith(span);
                } else if(parent.is("td") && clasif != 'S'){
                    span.wrap($("<a>").attr("title","Valores Posibles").attr("href","#").attr("onclick","return false;"));
                }
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

function borrarTipoAtributo(tipoAtributoId){
    if(confirm("¿Está seguro que desea borrar este tipo de atributo?")){
        $.ajax({
            type: "POST",
            url: "/admin/crud/TipoAtributoAction_destroy",
            data: {
                tipoAtributoId: tipoAtributoId
            },
            success: function(response){
                if(response=='OK'){
                    $("tr.tipoAtributo[val='"+tipoAtributoId+"']").remove();
                    $("#valoresPosibles").empty();
                } else {
                    alert(response);
                }
            }
        });
    }
}

$(document).ready( function(){
    $("tr.tipoAtributo select").selectmenu();
    $("tr.tipoAtributo").each( function(){
        var newNombre, nombre;
        $(this).find("input").blur( function() {
            newNombre = $(this).val();
            if(newNombre == '') return;
            $(this).hide("slow");
            updateNombreTipoAtributo($(this),newNombre);
        });

        $(this).find("input").keydown(function(event){
            if(event.keyCode == 13) $(this).blur();
        });

        $(this).find("span.nombre").rightClick( function() {
            nombre = $(this).text();
            var input = $(this).parents("tr:first").find("input");
            input.val(nombre);
            $(this).hide("slow");
            input.show("slow");
        });
    });

    $(".clases, .unidad, .clasif").bind('change',updateData);

    $("#attrFieldset tr.tipoAtributo td a.tipado").tooltip({
        track: false,
        delay: 0,
        showURL: false,
        showBody: " - ",
        fade: 250
    });

    $(".help span").click( function(){
        var new_window = window.open("/admin/objetos/TipoAtributo/Help.jsp",'','width=700,height=500,scrollbars=1');
        if (new_window.opener == null) {
            new_window.opener = self;
        }
        new_window.focus();
    });
});