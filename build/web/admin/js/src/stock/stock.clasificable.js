function updateStock(input, newUnidad){
    var tr = input.parents("tr:first");
    var stockId = tr.attr("val");
    var orig = tr.find("td.unidad span");
    var origText = orig.text();

    if(!newUnidad) newUnidad = origText;

    var datos = {
        stockId: stockId,
        cantidad: newUnidad
    };

    $.ajax({
        type: "POST",
        url: "/admin/crud/StockAction_edit",
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
                var total = $("#total");
                var t = parseInt(total.text());
                var o = parseInt(origText);
                var n = parseInt(newUnidad);
                (n < o) ?  $("#total").text(t-(o-n)) : $("#total").text(t+(n-o));
                total.animate({
                    backgroundColor: "#66FF66"
                }, 500)
                .animate({
                    backgroundColor: "#FFFFCC"
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

$(document).ready( function(){
    $('tr.stock td.unidad span').tooltip({
        track: false,
        delay: 0,
        showURL: false,
        showBody: " - ",
        fade: 250
    });

    $("tr.stock").each( function (){
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
            updateStock($(this),newUnidad);
        }).keydown(function(event){
            if(event.keyCode == 13) $(this).blur();
        });
    });
});