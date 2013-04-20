$(document).ready( function(){
    $("#btnCambiarStock").click( function(){
        $("#unidadesInpt").show("slow");
        $("#btnCambiarStock").hide();
        $("#btnActualizarStock").show();
    });

    $("#btnActualizarStock").click( function(){
        var cantidad = $("#unidadesInpt").val();
        if(!cantidad || cantidad == "") cantidad = 0;
        var stockId = $("#stockId").val();
        var datos = {
            stockId: stockId,
            cantidad: cantidad
        };

        $.ajax({
            type: "POST",
            url: "/admin/crud/StockAction_edit",
            data: datos,
            cache: false,
            success: function(response){
                if(response != 'OK') alert("Ha ocurrido un error: \n" + response);
                else {
                    $("#unidades").text(cantidad)
                    .animate({
                        backgroundColor: "#66FF66"
                    }, 500).animate({
                        backgroundColor: "white"
                    }, 500);
                    $("#btnActualizarStock").hide();
                    $("#unidadesInpt").hide();
                    $("#btnCambiarStock").show();
                }
            }
        });
    });
});