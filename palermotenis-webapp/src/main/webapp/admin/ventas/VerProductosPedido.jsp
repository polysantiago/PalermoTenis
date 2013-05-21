<%-- 
    Document   : VerProductosPedido
    Created on : 16/10/2009, 02:28:44
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Productos de Pedido</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js" ></script>
        <style type="text/css">
            #main {width: 570px;}
            #main table.list{width:560px;}
            td.unidad {cursor: default;}
            td.unidad input {margin: 0 auto; text-align: center;}
            div.flecha {
                background: transparent url('/images/Flechas.gif') no-repeat;
                width: 16px;
                height: 16px;
                float: left;
                margin: 0 3px;
            }
            #btnAgregar {margin: 5px;}
            div.up {background-position: top left;}
            div.down {background-position:-37px 0;}
            div.up:hover {background-position: bottom left;}
            div.down:hover {background-position:-37px -18px;}
            div.loader {margin: 0 auto;}
            span.error {font-size: 8px;color: red;}
            img.cancelar {cursor: pointer;}
            td.eliminar img {cursor: pointer;}
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


            function ltrim(s) {return s.replace(/^\s+/, "");}
            function rtrim(s) {return s.replace(/\s+$/, "");}
            function trim(s)  {return rtrim(ltrim(s));}

            function updateCantidad(tr,cantidad,stock){
                tr.find("td.cantidad div.loader").show();
                $.getJSON("PedidoProductoAction_editCantidad",{cantidad:cantidad, pedidoId: $("#pedidoId").val(),stockId: tr.attr("val")},
                function(data){
                    tr.find("td.subtotal span").html(formatCurrency(data.subtotal));
                    tr.find("td.stock span").html(stock);
                    tr.find("td.cantidad span").html(cantidad);
                    $("#total span").html(formatCurrency(data.total));
                    $(opener.document).find("tr[val='"+$("#pedidoId").val()+"'] td.total").html(formatCurrency(data.total));
                    tr.find("td.cantidad .loader").hide();
                });
            }

            function findValueCallback(event, data, formatted) {
                var valid = true;
                var input = $(event.target);
                var s = new String(data).split(",");
                $("span.error").remove();
                $("tr.pedidoProducto").each(function(){
                    if(parseInt($(this).attr("val"))==parseInt(s[0])){
                        input.val("").closest("td").append($("<span>").addClass("error").html("El producto seleccionado ya es parte del pedido"));
                        valid = false;
                    }
                });
                if(valid){
                    input.closest("div.autocomplete").hide("slow").closest("td").find("div.loader").show();
                    $.getJSON("PedidoProductoAction_editStock",
                    {pedidoId: $("#pedidoId").val(),stockId: input.closest("tr").attr("val"), newStockId: s[0]},
                    function(data){
                        var tr = input.closest("tr");
                        tr.find("td.producto div.loader").hide();
                        var result = "<strong>"+s[1]+" "+s[2]+"</strong>&nbsp;";
                        result += s[3] + " - ";
                        for(var i = 4; i < s.length; i++)result += s[i] + ' ';
                        input.closest("td").find("span").html(result).show("slow");
                        tr.find("td.cantidad span").html(1);
                        tr.find("td.subtotal span").html(formatCurrency(data.subtotal));
                        tr.find("td.stock span").html(parseInt(data.stock)-1);
                        tr.attr("val",s[0]);
                        $("#total span").html(formatCurrency(data.total));
                        $(opener.document).find("tr[val='"+$("#pedidoId").val()+"'] td.total").html(formatCurrency(data.total));
                    });
                }
            }

            function addValueCallback(event, data, formatted){
                var valid = true;
                var input = $(event.target);
                var s = new String(data).split(",");
                $("span.error").remove();
                $("tr.pedidoProducto").each(function(){
                    if(parseInt($(this).attr("val"))==parseInt(s[0])){
                        input.val("").closest("td").append($("<span>").addClass("error").html("El producto seleccionado ya es parte del pedido"));
                        valid = false;
                    }
                });
                if(valid){
                    var tr = input.closest("tr");
                    tr.find("input.stockId").val(s[0]);
                }
            }

            function format(row){
                var result = "<i>"+row[1]+" "+row[2]+"</i>&nbsp;";
                result += row[3] + " - ";
                for(var i = 4; i < row.length; i++)result += row[i] + ' ';
                return result;
            }

            function formatRs(row){
                var result = row[1]+" "+row[2]+" ";
                result += row[3] + " - ";
                for(var i = 4; i < row.length; i++)result += row[i] + ' ';
                return result;
            }

            $(document).ready( function(){

                var options = {minChars:3,matchSubset:1,matchContains:1,cacheLength:30,formatItem:format,formatResult:formatRs,scroll:true,max:20,width:310,selectOnly:1};

                if(parseFloat($("#main").height())>1024){
                    window.resizeTo(parseFloat($("#main").width())+100, 1000);
                } else {
                    window.resizeTo(parseFloat($("#main").width())+100, parseFloat($("#main").height())+150);
                }

                $("td.producto input").autocomplete("/GetProductosAutoCompleteList_active",options).result(findValueCallback);

                $("div.up").click( function(){
                    var tr = $(this).closest("tr");
                    var stock = parseInt(trim(tr.find(".stock span").text()));
                    var qty = parseInt(trim(tr.find(".cantidad span").text()));
                    if(stock != 0){
                        updateCantidad(tr,qty+1,stock-1);
                    }
                });

                $("div.down").click(function(){
                    var tr = $(this).closest("tr");
                    var stock = parseInt(trim(tr.find(".stock span").text()));
                    var qty = parseInt(trim(tr.find(".cantidad span").text()));
                    if(qty != 0){
                        updateCantidad(tr,qty-1,stock+1);
                    }
                });

                $("tr.pedidoProducto td.producto").each( function (){
                    $(this).find("span").rightClick( function() {
                        $(this).hide("slow").parent().find("input").val("").closest("div.autocomplete").show("slow");
                    }).tooltip({
                        track: false,
                        delay: 0,
                        showURL: false,
                        showBody: " - ",
                        fade: 250
                    });
                    $(this).find("img.cancelar").click( function(){
                        var td = $(this).closest("td");
                        td.find("span.error").remove();
                        td.find("div.autocomplete").hide().find("input").val("");
                        td.find("span").show();
                    });
                });

                $("tr.pedidoProducto td.eliminar img").click( function(){
                    if(confirm("¿Está seguro que desea quitar este producto del pedido?")){
                        if($("tr.pedidoProducto").length == 1){
                            alert("El pedido no puede tener menos de un producto");
                            return false;
                        }
                        var tr = $(this).closest("tr");
                        var data = {pedidoId: $("#pedidoId").val(), stockId:tr.attr("val")};
                        $.getJSON("PedidoProductoAction_destroy",data, function(data){
                            if(data.result=="success"){
                                tr.remove();
                                $("#total span").html(formatCurrency(data.total));
                                $(opener.document).find("tr[val='"+$("#pedidoId").val()+"'] td.total").html(formatCurrency(data.total));
                            } else {
                                alert(data.msg);
                            }
                        });
                    }
                });

                $("#btnAgregar").click(function(){
                    var input = $("<input>").attr("size",40).autocomplete("/GetProductosAutoCompleteList_active",options).result(addValueCallback)
                    .append($("<input>").attr({type:"hidden"}).addClass("stockId"));
                    var cantidad = $("<span>").html(1);
                    var agregar = $("<button>").addClass("btnAgregar").html("Agregar").click( function(){
                        var tr = $(this).closest("tr");
                        var stockId = tr.find("input.stockId").val();
                        if(!stockId || stockId=="" || isNaN(stockId)){
                            alert("Debe seleccionar un producto");
                            return false;
                        }
                        var data = {pedidoId: $("#pedidoId").val(), stockId:stockId};
                        $.getJSON("PedidoProductoAction_add",data,function(data){
                            if(data.result=="success"){
                                $(opener.document).find("tr[val='"+$("#pedidoId").val()+"'] td.total").html(formatCurrency(data.total));
                                window.location.href = window.location.href;
                            } else {
                                alert(data.msg);
                            }
                        });
                    });

                    $("table:first tbody").append($("<tr>")
                    .append($("<td>").addClass("producto").append(input))
                    .append($("<td>").addClass("cantidad").append(cantidad))
                    .append($("<td>").append(agregar)));
                });

            });
        </script>
    </head>
    <body>
        <div id="main">
            <div id="result" />
            <s:hidden id="pedidoId" value="%{pedidoId}" />
            <table class="list" align="center">
                <thead>
                <th colspan="4">
                    <b>Pedido #<s:property value="pedidoId" /></b>
                </th>
                </thead>
                <tbody>
                    <tr class="yellow">
                        <td>Producto</td>
                        <td>Cantidad</td>
                        <td>Subtotal</td>
                        <td>Stock</td>
                    </tr>
                    <s:iterator value="pedidosProductos" var="pp">
                        <s:set var="producto" value="#pp.id.stock.producto" />
                        <s:set var="presentacion" value="#pp.id.stock.presentacion" />
                        <s:set var="valorClasif" value="#pp.id.stock.valorClasificatorio" />
                        <tr val="<s:property value="#pp.id.stock.id" />" class="pedidoProducto">
                            <td class="producto">
                                <span title="Click derecho para editar">
                                    <strong>
                                        <s:property value="#producto.tipoProducto.nombre" />
                                        <s:property value="#producto.modelo.marca.nombre" />
                                    </strong>
                                    <s:iterator value="#producto.modelo.padres" var="m">
                                        <s:property value="#m.nombre + ' '" />
                                    </s:iterator>
                                    <s:property value="#producto.modelo.nombre" />
                                    <s:if test="#presentacion != null">
                                        <s:property value="' - ' + #presentacion.tipoPresentacion.nombre + ' ' + #presentacion.nombre" />
                                    </s:if>
                                    <s:if test="#valorClasif != null">
                                        <s:property value="' - ' + #valorClasif.tipoAtributo.nombre + ': ' + #valorClasif.nombre" />
                                    </s:if>
                                </span>
                                <s:div cssClass="autocomplete" cssStyle="display:none;">
                                    <s:textfield size="40" theme="css_xhtml"/>
                                    <img alt="Cancelar" title="Cancelar" class="cancelar" src="/images/boton-eliminar.png" />
                                </s:div>
                                <div class="loader" style="display:none;">
                                    <img alt="Cargando..." src="/images/indicator.gif" />
                                </div>
                            </td>
                            <td class="cantidad">
                                <span><s:property value="#pp.cantidad" /></span>
                                <div class="flecha up"></div>
                                <div class="flecha down"></div>
                                <div class="loader" style="display:none;">
                                    <img alt="Cargando..." src="/images/indicator.gif" />
                                </div>
                            </td>
                            <td class="subtotal">
                                <span><s:text name="format.currency"><s:param value="#pp.subtotal" /></s:text></span>
                            </td>
                            <td class="stock">
                                <span><s:property value="#pp.id.stock.stock-#pp.cantidad" /></span>
                            </td>
                            <td class="eliminar">
                                <img alt="Eliminar" title="Eliminar" class="imgEliminar" src="/images/boton-eliminar.png" />
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <table class="list">
                <tr class="yellow">
                    <td><strong>TOTAL</strong></td>
                    <td id="total">
                        <span>
                            <s:text name="format.currency">
                                <s:param value="pedido.total" />
                            </s:text>
                        </span>
                    </td>
                </tr>
            </table>
            <button id="btnAgregar">Agregar producto</button>
        </div>
    </body>
</html>
