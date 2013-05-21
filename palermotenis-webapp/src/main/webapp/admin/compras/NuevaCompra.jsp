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
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <title>Panel de Control - PalermoTenis</title>
    <s:include value="/WEB-INF/jspf/admin_header.jspf" />
    <script type="text/javascript">
        var globalrow;

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


        function validate(){
            $("#log").empty();
            var valid = true;
            var productos = $("#stocks option");
            if(productos.length == 0) {
                $("#log").append($("<div>").addClass("error").html("No hay productos ingresados"));
                valid = false;
            }
            if(valid)
                productos.attr("selected","true");
            return valid;
        }

        function addProductoToList(){
            $("#btnQuitar").removeAttr("disabled");
            var s = new String(globalrow).split(",");
            var cantidad = $("#cantidad").val();
            var html = cantidad+"x "+formatRs(s)+ " - Proveedor: "+$("#proveedorId option:selected").text()+" Costo: "+formatCurrency($("#costo").val());
            var value = s[0]+","+cantidad+","+$("#proveedorId option:selected").val()+","+$("#costo").val();
            $("<option>").html(html).val(value).appendTo($("#stocks"));
            $("#producto").val("");
            $("#costo").val("");
            return false;
        }

        function setGlobalRow(event, data, formatted){
            globalrow = data;
            $("#costo").val(data[data.length-1]);
        }

        function format(row){
            var result = "<i>"+row[1]+" "+row[2]+"</i>&nbsp;";
            result += row[3] + " - ";
            for(var i = 4; i < row.length-1; i++)result += row[i] + ' ';
            result += formatCurrency(row[row.length-1]);
            return result;
        }

        function formatRs(row){
            var result = row[1]+" "+row[2]+" ";
            result += row[3] + " - ";
            for(var i = 4; i < row.length-1; i++)result += row[i] + ' ';
            result = rtrim(result);            
            return result;
        }

        $(document).ready( function(){
            $("#btnQuitar").attr("disabled","true").click(function(e){
                e.preventDefault();
                $("#stocks option:selected").remove();
            });
            var pId = {proveedorId : $("#proveedorId option:selected").val()};
            var options = {
                minChars:3,
                matchSubset:1,
                matchContains:1,
                cacheLength:30,
                formatItem:format,
                formatResult:formatRs,
                scroll:true,
                max:30,
                width:310,
                selectOnly:1,
                extraParams: pId
            };
            $("#producto").autocomplete("/GetProductosAutoCompleteList_allCostos",options).result(setGlobalRow);
            $("#proveedorId").change( function(){
               $("#producto").setOptions({extraParams:{proveedorId: $("#proveedorId option:selected").val()}});
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
            <h2>Registrar nueva compra</h2>
            <div id="log"></div>
            <s:form onsubmit="return addProductoToList();">
                <s:textfield label="Cantidad" id="cantidad" id="cantidad" value="1" size="1"/>
                <s:action name="ProveedorAction_show" namespace="/admin/crud" executeResult="false" ignoreContextParams="true" var="p" />
                <s:select list="#p.proveedores" listKey="id" listValue="nombre" label="Proveedor" id="proveedorId" />
                <s:textfield label="Producto" id="producto" size="80" />
                <s:textfield label="Costo" id="costo" />
                <s:submit id="btnAgregar" value="Agregar" />
            </s:form>
            <s:form action="RegistrarCompra" onsubmit="return validate()">
                <td></td><td><select multiple="true" name="stocks" id="stocks" style="width: 760px;" size="4" /></td>
                <tr><td></td><td><button id="btnQuitar">Quitar</button></td></tr>
                <s:submit value="Registrar Compra" />
            </s:form>
        </div>
        <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
    </div>
</body>
</html>
