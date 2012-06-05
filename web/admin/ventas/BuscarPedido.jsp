<%@ taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    #buscador {
        margin: 10px;
        border: 1px dotted #000000;
    }

    #resultados {
        margin:5px auto;
        padding:10px 0;
        width: 770px;
    }

    table {
        border-collapse: collapse;
    }

    table thead {
        font-weight:bold;
        text-align:center;
    }

    table thead th {
        border-bottom: maroon solid;
        border-top: maroon solid;
        background-color: #FFFCDF;
    }

    table tbody td {
        border-bottom:1px solid #EFEAB3;
        color:#AF9141;
        font-family: "Lucida Grande,Tahoma,sans-serif";
        font-size:11px;
        padding:5px 3px;
    }

    table tbody tr:hover {
        background-color: #FFFFCC;
    }

    tr.selected {
        background-color: #FFFF99;
    }

    #btnConfirm{
        margin-top: 6px;
    }

    .lupita, .imgEliminar {
        cursor: pointer;
    }

</style>
<script type="text/javascript">
    function formatCurrency(num) {
        num = num.toString().replace(/\$|\,/g,'');
        if(isNaN(num)) num = "0";
        sign = (num == (num = Math.abs(num)));
        num = Math.floor(num*100+0.50000000001);
        cents = num%100;
        num = Math.floor(num/100).toString();
        if(cents<10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
            num = num.substring(0,num.length-(4*i+3))+','+
            num.substring(num.length-(4*i+3));
        return (((sign)?'':'-') + '$' + num + '.' + cents);
    }
    $(document).ready( function(){

        $("#btnSearch").click( function(){
            $("#resultados").empty();
            $("#loader").show();
            $.getJSON("BuscarPedido",{filter:$("#filter option:selected").val(),searchVal: $("#searchVal").val()},
            function(data){
                $("#loader").hide();
                var table =
                    $("<table cellspading='3' cellspacing='3' align='center'>")
                .append($("<thead>")
                .append($("<th>"))
                .append($("<th>").html("Nombre"))
                .append($("<th>").html("Email"))
                .append($("<th>").html("Teléfono"))
                .append($("<th>").html("Dirección"))
                .append($("<th>").html("Ciudad - Provincia"))
                .append($("<th>").html("Pago"))
                .append($("<th>").html("Cuotas"))
                .append($("<th>").html("Total"))
                .append($("<th>").html("Fecha"))
            );
                var tbody = $("<tbody>");

                $.each(data, function(){

                    var lupita = $("<img>").attr("src","/admin/images/ic_lupa.png").addClass("lupita").click( function(){
                        var width = 620;
                        var height = 300;
                        var id = $(this).closest("tr").attr("val");
                        var new_window = window.open('PedidoProductoAction_list?pedidoId='+id,'','width='+width+',height='+height+',scrollbars=1');
                        if (new_window.opener == null) {
                            new_window.opener = self;
                        }
                        new_window.focus();
                    });

                    var attr = {alt:"Eliminar",title:"Eliminar",src:"/images/boton-eliminar.png"};
                    var eliminar = $("<img>").attr(attr).addClass("imgEliminar").click( function(){
                        if(confirm("¿Está seguro que quiere quitar este pedido?")){
                            var tr = $(this).closest("tr");
                            $.getJSON("PedidoAction_destroy",{pedidoId:tr.attr("val")},function(data){
                                (data.result=="success")? tr.remove() : alert(data.msg);
                            });
                        }
                    });

                    with(this){
                        var date = new Date();
                        date.setTime(fecha.time);
                        $("<tr>").attr("val",id)
                        .append($("<td>").append(lupita))
                        .append($("<td>").html(cliente.nombre))
                        .append($("<td>").html(cliente.email))
                        .append($("<td>").html(cliente.usuario))
                        .append($("<td>").html(cliente.telefono))
                        .append($("<td>").html(cliente.direccion.direccion))
                        .append($("<td>").html(cliente.direccion.ciudad))
                        .append($("<td>").html(pago.nombre))
                        .append($("<td>").html(cuotas))
                        .append($("<td>").addClass("total").html(formatCurrency(total)))
                        .append($("<td>").html(date.toLocaleFormat("%a, %b %c")))
                        .append($("<td>").addClass("eliminar").append(eliminar))
                        .appendTo(tbody);
                    }
                });
                tbody.appendTo(table);
                table.appendTo($("#resultados"));
                $("<button>").html("Confirmar venta").attr("id","btnConfirm").attr("disabled","true").click( function(){
                    window.location.href = "ConfirmarVenta_confirmarPedido?pedidoId="+$(".selected:first").attr("val");
                }).appendTo($("#resultados"));

                $("table tbody tr").click( function(){
                    $("table tbody tr.selected").removeClass("selected");
                    $(this).addClass("selected");
                    $("#btnConfirm").removeAttr("disabled");
                });
            });
        });
    });
</script>
<div id="buscador">
    <div id="funciones">
        <label for="filter">Buscar por:</label>
        <select id="filter">
            <option value="E">Email</option>
            <option value="N">Nombre</option>
        </select>
        <input type="text" id="searchVal" />
        <button id="btnSearch">Buscar</button>
        <img alt="Cargando..." id="loader" style="display: none;" src="/images/ajax-loader-cotizacion.gif" />
    </div>
    <div id="resultados">

    </div>
</div>
