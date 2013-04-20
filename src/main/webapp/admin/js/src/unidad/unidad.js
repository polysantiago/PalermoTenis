function updateUnidad(input, newDesc, newNombre){
    var tr = input.parents("tr:first");
    var unidadId = tr.attr("val");

    if(!newDesc && !newNombre){
        alert("Verifique que los campos no estén vacíos");
        return false;
    }

    if(newDesc && !newNombre){
        var orig = tr.find("td.descripcion span");
        var origText = orig.text();
        newNombre = tr.find("td.nombre span").text();
        var newtext = newDesc;
    } else if(newNombre && !newDesc){
        var orig = tr.find("td.nombre span");
        var origText = orig.text();
        newDesc = tr.find("td.descripcion span").text();
        var newtext = newNombre;
    }

    var datos = {
        unidadId: unidadId,
        descripcion:newDesc,
        nombre: newNombre
    };

    $.ajax({
        type: "POST",
        url: "/admin/crud/UnidadAction_edit",
        data: datos,
        success: function(response){
            if(response=='OK'){
                orig.show("slow").text(newtext.toString());
                tr.animate({
                    backgroundColor: "#66FF66"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);
            } else {
                alert(response);
                orig.show("slow").text(origText);
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

function agregarUnidad(addBtn){
    var tr = addBtn.parents("tr:first");

    var unidadObj = {
        descripcion: tr.find("td.descripcion input").val(),
        nombre: tr.find("td.nombre input").val()
    };

    if(!unidadObj.descripcion || unidadObj.descripcion == ""){
        alert("Por favor, ingrese una descripción");
        return false;
    } else if(!unidadObj.nombre || unidadObj.nombre == ""){
        alert("Por favor, ingrese un nombre");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/admin/crud/UnidadAction_create",
        data: unidadObj,
        success: function(response){
            if(response=='OK'){
                $("#btnEditarUnidades").click();
            } else {
                alert(response);
            }
        }
    });
}

$(document).ready( function(){
    $("#uFieldset tr.unidad").each( function(){
        //Descripción
        $(this).find("td.descripcion span").rightClick( function() {
            var unidad = $(this).text();
            var input = $(this).parent().find("input");
            input.val(unidad);
            $(this).hide("slow");
            input.show("slow");
        });
        $(this).find("td.descripcion input").blur( function(i) {
            var newUnidad = $(this).val();
            if(newUnidad == '') return;
            $(this).hide("slow");
            updateUnidad($(this),newUnidad,null);
        });

        //Nombre
        $(this).find("td.nombre span").rightClick( function() {
            var unidad = $(this).text();
            var input = $(this).parent().find("input");
            input.val(unidad);
            $(this).hide("slow");
            input.show("slow");
        });
        $(this).find("td.nombre input").blur( function(i) {
            var newUnidad = $(this).val();
            if(newUnidad == '') return;
            $(this).hide("slow");
            updateUnidad($(this),null,newUnidad);
        });
    });
    $('#uFieldset tr.unidad td.nombre span, #uFieldset tr.unidad td.descripcion span').tooltip({
        track: false,
        delay: 0,
        showURL: false,
        showBody: " - ",
        fade: 250
    });

    $("#btnBorrarUnidades").click( function(){
        if(confirm("¿Está seguro que quiere borrar las unidades seleccionadas?")){
            $("#uFieldset tr.unidad input.delete").each( function(){
                if(this.checked){
                    var tr = $(this).parents("tr:first");
                    $.ajax({
                        type: "POST",
                        url: "/admin/crud/UnidadAction_destroy",
                        data: {
                            unidadId: tr.attr("val")
                            },
                        success: function(response){
                            if(response=='OK') tr.remove();
                            else alert(response);
                        }
                    });
                }
            });
        }
    });

    $("#uFunctions div.addBtn").click( function(){
        var nombre = $("<input>").attr("size",10).keydown( function(e){
            if(e.keyCode==13)agregarUnidad($(this));
        });
        var desc = $("<input>").attr("size",5).keydown( function(e){
            if(e.keyCode==13)agregarUnidad($(this));
        });
        var add = $("<div>").addClass("addBtn").attr("title","Agregar").click(function(){
            agregarUnidad($(this));
        });
        $("#uFieldset table tbody").append($("<tr>")
            .append($("<td>"))
            .append($("<td>").addClass("descripcion").append(desc))
            .append($("<td>").addClass("nombre").append(nombre))
            .append($("<td>").append(add)));
    });

});