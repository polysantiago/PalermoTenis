<%-- 
    Document   : Stock_one
    Created on : 11/09/2009, 00:40:21
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/stock/stock.simple.pack.js"></script>
        <style type="text/css">
            #unidadesInpt,#btnCambiarStock {float: left;display: block;}
            #main {width: 250px;height: 90px;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:hidden value="%{stock.id}" id="stockId" />
            <s:set value="stock.producto.tipoProducto.nombre" var="tp" />
            <s:set value="stock.producto.modelo.marca.nombre" var="m" />
            <s:set value="stock.producto.modelo.nombre" var="mdl" />
            <h2>Hay <span id="unidades"><s:property value="stock.stock" default="0" /></span> unidad/es disponibles de <s:property value="#tp" /> <s:property value="#m" /> <s:property value="#mdl" /></h2>
            <button id="btnCambiarStock">Cambiar</button><s:textfield value="%{stock.stock}" id="unidadesInpt" size="2" cssStyle="display: none;" theme="css_xhtml" />
            <button id="btnActualizarStock" style="display: none;">Actualizar</button>
        </div>
    </body>
</html>
