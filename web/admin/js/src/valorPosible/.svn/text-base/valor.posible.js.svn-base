function updateValorPosible(input, newUnidad){
    var tr = input.parents("tr:first");
    var valorPosibleId = tr.attr("val");
    var orig = tr.find("td.unidad span");
    var origText = orig.text();

    if(!newUnidad) newUnidad = origText;

    var datos = {
        valorPosibleId: valorPosibleId,
        unidad: newUnidad
    };

    $.ajax({
        type: "POST",
        url: "/admin/crud/ValorPosibleAction_edit",
        data: datos,
        success: function(response){
            if(response=='OK'){
                orig.show("slow").text(newUnidad.toString());
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

function agregarValorPosible(addBtn){
    var tr = addBtn.parents("tr:first");

    var valorPosibleObj = {
        tipoAtributoId: $("#attrFieldset tr.tipoAtributo span.selected").parents("tr:first").attr("val"),
        unidad: tr.find("td.unidad input").val()
    };

    if(!valorPosibleObj.unidad || valorPosibleObj.unidad == ""){
        alert("Por favor, ingrese un valor");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/admin/crud/ValorPosibleAction_create",
        data: valorPosibleObj,
        success: function(response){
            if(response=='OK'){
                $("#attrFieldset tr.tipoAtributo span.selected").click();
            } else {
                alert(response);
            }
        }
    });
}

$(document).ready( function(){
    $("#valoresPosibles tr.valorPosible").each( function(){
        $(this).find("td.unidad span").rightClick( function() {
            var unidad = $(this).text();
            var input = $(this).parent().find("input");
            input.val(unidad);
            $(this).hide("slow");
            input.show("slow");
        });
        $(this).find("td.unidad input").blur( function(i) {
            var newUnidad = $(this).val();
            if(newUnidad == '') return;
            $(this).hide("slow");
            updateValorPosible($(this),newUnidad);
        });
    });
    $('#valoresPosibles tr.valorPosible td.unidad span').tooltip({
        track: false,
        delay: 0,
        showURL: false,
        showBody: " - ",
        fade: 250
    });

    $("#btnBorrarValoresPosibles").click( function(){
        if(confirm("¿Está seguro que quiere borrar los valores posibles seleccionados?")){
            $("#valoresPosibles tr.valorPosible input.delete").each( function(){
                if(this.checked){
                    var tr = $(this).parents("tr:first");
                    $.ajax({
                        type: "POST",
                        url: "/admin/crud/ValorPosibleAction_destroy",
                        data: {
                            valorPosibleId: tr.attr("val")
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

    $("#vpFunctions div.addBtn").click( function(){
        var unidad = $("<input>").attr("size",10);
        var add = $("<div>").addClass("addBtn").attr("title","Agregar").click(function(){
            agregarValorPosible($(this));
        });
        $("#valoresPosibles table").append($("<tr>")
            .append($("<td>"))
            .append($("<td>").addClass("unidad").append(unidad))
            .append($("<td>").append(add)));
    });

});