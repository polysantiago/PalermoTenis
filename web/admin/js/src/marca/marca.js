function borrarMarca(marcaId){
    if(confirm("¿Está seguro que desea borrar esta marca?\nTodos los modelos de esta marca serán borrados")){
        $.ajax({
            type: "POST",
            url: "/admin/crud/MarcaAction_destroy",
            data: {
                marcaId: marcaId
            },
            success: function(response){
                (response=='OK') ? $("tr.marca[val='"+marcaId+"']").remove() : alert(response);
            }
        });
    }
}

function updateMarca(input, newNombre){
    var tr = input.parents("tr:first");
    var marcaId = tr.attr("val");
    var orig = tr.find("td.nombre span");
    var origText = orig.text();

    if(!newNombre) newNombre = origText;

    $.ajax({
        type: "POST",
        url: "/admin/crud/MarcaAction_edit",
        data: {
            marcaId: marcaId,
            nombre: newNombre
        },
        success: function(response){
            if(response=='OK'){
                orig.show("slow").text(newNombre.toString())
                .animate({
                    backgroundColor: "#66FF66"
                }, 500)
                .animate({
                    backgroundColor: "white"
                }, 500);
            } else {
                alert(response);
                orig.show("slow").text(origText)
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

function agregarMarca(addBtn){
    var tr = addBtn.closest("tr");

    var obj = {
        nombre: tr.find("td.nombre input").val()
        };
    if(!obj.nombre)alert("Por favor, ingrese un nombre");

    $.ajax({
        type: "POST",
        url: "/admin/crud/MarcaAction_create",
        data: obj,
        success: function(response){
            if(response=='OK')window.location.href = "MarcaAction_show";
        }
    });
}

$(document).ready( function(){

    $("#btnAgregar").click( function(){
        var nombre = $("<input>").attr("size",10);
        var add = $("<div>").addClass("addBtn").attr("title","Agregar").click(function(){
            agregarMarca($(this));
        });
        $("#marcas").append($("<tr>")
            .append($("<td>").addClass("nombre").append(nombre))
            .append($("<td>").append(add)));
    });

    $("tr.marca").each( function(){
        var newNombre, nombre;
        $(this).find("td.nombre input").blur( function(i) {
            newNombre = $(this).val();
            if(newNombre == '') return;
            $(this).hide("slow");
            updateMarca($(this),newNombre);
        });

        $(this).find("td.nombre input").keydown(function(event){
            if(event.keyCode == 13) $(this).blur();
        });

        $(this).find("td.nombre span").rightClick( function(i) {
            nombre = $(this).text();
            var input = $(this).parent().find("input");
            input.val(nombre);
            $(this).hide("slow");
            input.show("slow");
        });
    });
});