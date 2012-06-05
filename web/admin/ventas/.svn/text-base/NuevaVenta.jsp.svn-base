<%--
    Document   : index
    Created on : 10/07/2009, 18:53:33
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <script type="text/javascript">

            function validate(){
                $("#log").empty();
                var valid = true;
                var clienteId = $("#clienteId").val();
                var productos = $("#stocks option");
                if(!clienteId || clienteId==''){
                    $("#log").append($("<div>").addClass("error").html("Debe ingresar un número de cliente"));
                    valid = false;
                }
                if(productos.length == 0) {
                    $("#log").append($("<div>").addClass("error").html("No hay productos ingresados"));
                    valid = false;
                }
                if(valid){
                    productos.attr("selected","true");
                }
                return valid;
            }

            function addProductoToList(event, data, formatted){
                event.preventDefault();
                $("#btnQuitar").removeAttr("disabled");
                var input = $(event.target);
                var s = new String(data).split(",");
                var cantidad = $("#cantidad").val();
                $("<option>").html(cantidad+"x "+formatRs(s)).val(s[0]+","+cantidad).appendTo($("#stocks"));
                input.val("");
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
                $("#btnQuitar").attr("disabled","true");
                $("#btnBuscarCliente").click( function(){
                    var new_window = window.open('BuscarCliente.jsp','Buscar Cliente','width=900,height=200,scrollbars=1');
                    if (new_window.opener == null) {
                        new_window.opener = self;
                    }
                    new_window.focus();
                });
                var options = {minChars:3,matchSubset:1,matchContains:1,cacheLength:30,formatItem:format,formatResult:formatRs,scroll:true,max:30,width:310,selectOnly:1};
                $("#producto").autocomplete("/GetProductosAutoCompleteList_active",options).result(addProductoToList);

                $("#pagoId").change( function(){
                    if($(this).val()!=1){
                        $("#cuotas").removeAttr("disabled");
                    } else {
                        $("#cuotas option[value='1'").attr("selected","selected");
                        $("#cuotas").attr("disabled","true");
                    }
                });

            });
        </script>
        <style type="text/css">
            .error{color:red;font-size:13px;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <h2>Datos Venta</h2>
                <button id="btnBuscarCliente">Buscar cliente</button>
                <button id="btnNuevoCliente" onclick="window.location = '/admin/ventas/NuevoCliente'">Nuevo cliente</button>
                <div id="log"></div>
                <s:form action="Resumen" onsubmit="return validate()">
                    <s:textfield name="clienteId" label="Número de cliente" size="3" value="%{cliente.id}" id="clienteId" />
                    <s:action name="PagoAction_show" ignoreContextParams="true" namespace="/admin/crud" var="p" />
                    <s:select label="Pago" list="#p.pagos" listKey="id" listValue="nombre" name="pagoId" id="pagoId"  />
                    <s:select label="Cuotas" list="{1,3,6}" name="cuotas" id="cuotas" />
                    <s:textfield label="Cantidad" id="cantidad" id="cantidad" value="1" size="1"/>
                    <s:textfield label="Producto" id="producto" size="50" />
                    <tr><td></td><td><button id="btnQuitar">Quitar</button></td></tr>
                    <td></td><td><select multiple="true" name="stocks" id="stocks" style="width: 560px;" size="4" /></td>
                    <s:submit value="Enviar" />
                </s:form>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>