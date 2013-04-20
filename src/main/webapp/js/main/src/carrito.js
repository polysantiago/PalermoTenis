function formatCurrency(num,code) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    num = Math.floor(num*100+0.50000000001);
    var cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
        num.substring(num.length-(4*i+3));
    return code + num + '.' + cents;
}

function formatItem(row) {
    return row[0] + "<br><i>" + row[1] + "</i>";
}

function updatePrecios(){
    $(".loader").show();
    $("#btnEnviar").attr("disabled","true");
    $.getJSON("/carrito/CarritoAction_editPago",{
        pagoId: $("#pagoId option:selected").val(),
        cuotas: $("#cuotas option:selected").val()
    },
    function(data){
        var wholeChanged = false;
        $.each(data.items, function(){
            var changed = false;
            var tr = $("tr[val='stock-"+this.id+"']");
            var precioUnitario = parseFloat(tr.find("td.unitario").attr("val"));
            var subtotal = parseFloat(tr.find("td.subtotal").attr("val"));
            var newUnitario = parseFloat(this.item.precioUnitario);
            var newSubtotal = parseFloat(this.item.subtotal);
            if(precioUnitario != newUnitario){
                changed = true;
                wholeChanged = true;
                tr.find("td.unitario").attr("val",newUnitario).find("span").html(formatCurrency(newUnitario, "$"));
            }
            if(subtotal != newSubtotal){
                changed = true;
                wholeChanged = true;
                tr.find("td.subtotal").attr("val",newSubtotal).find("span").html(formatCurrency(newSubtotal, "$"));
            }
            tr.find("td.pago span").html(data.pago);
            if(changed){
                tr.find("td.unitario, td.subtotal, td.pago")
                .animate({
                    backgroundColor:"#66FF66"
                },500).animate({
                    backgroundColor:"#FF6600"
                },500);
            }
        });
        if(wholeChanged){
            $("#producto-total td.precio").animate({
                backgroundColor:"#66FF66"
            },500)
            .animate({
                backgroundColor:"#FFFF00"
            },500).find("strong").html(formatCurrency(data.total, "$"));
        }
        $("#btnEnviar").removeAttr("disabled");
        $(".loader").hide();
    });
}

function validate(){
    var valid = true;
    $("input.cantidad").removeClass("error")
    .each( function(){
        var val = $(this).val();
        if(!val || val == "" || isNaN(val)){
            $(this).addClass("error");
            valid = false;
        }
    });
    if(valid) $("#carritoForm").submit();
}

$(document).ready( function(){
    $("#ciudad").autocomplete("/GetCiudades",
    {
        minChars:3,
        matchSubset:1,
        matchContains:1,
        cacheLength:10,
        width: 310,
        delay: 0,
        formatItem: function(row) {
            var cp = "";
            if(row[2]!="null") cp = " (" + row[2] + ")";
            return row[1] + "<br><i>" + row[3] + cp + ", " + row[4] + "</i>";
        },
        formatResult: function(row) {
            $("#ciudadId").val(row[0]);
            var cp = "";
            if(row[2]!="null") cp = " (" + row[2] + ")";
            return row[1] + " - " + row[3] + cp + ", " + row[4];
        },
        autoFill: true,
        selectOnly:1
    });

    $("#pagoId").change( function(){
        if($(this).val()!=1){
            $("#cuotas").removeAttr("disabled");
        } else {
            $("#cuotas option[value='1'").attr("selected","selected");
            $("#cuotas").attr("disabled","true");
        }
        updatePrecios();
    });
    $("#cuotas").change(updatePrecios);
});