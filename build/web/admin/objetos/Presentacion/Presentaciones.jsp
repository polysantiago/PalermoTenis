<%-- 
    Document   : Presentaciones
    Created on : 23/08/2009, 19:28:08
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Presentaciones</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/presentacion/presentacion.menu.pack.js"></script>
        <style type="text/css">
            #main {min-height: 250px;width: 800px;}
            #selection {width: 250px;float:left;}
            #content {width: 400px;float:left;margin-left: 20px;}
            .funciones {margin-top: 5px;width:260px;}
            #loader{bottom:-180px;float:right;position:relative;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:include value="/WEB-INF/jspf/admin_dialogs.jspf" />
            <s:hidden value="%{#parameters['productoId']}" id="productoId" cssClass="globalElement" />
            <s:hidden value="" id="tipoProductoId" cssClass="globalElement" />
            <s:hidden value="" id="tipoPresentacionId" cssClass="globalElement" />
            <div id="selection">
                <fieldset>
                    <legend>Tipo de Producto / Tipo de Presentacion</legend>
                    <div>
                        <label for="tipoProducto">Tipo de Producto:</label>
                        <select id="tipoProducto" size="1"><option value="">--</option></select>
                    </div>
                    <div>
                        <label for="tipoPresentacion">Tipo de Presentacion:</label>
                        <select id="tipoPresentacion" size="1"><option value="">--</option></select>
                    </div>
                </fieldset>
                <div class="funciones">
                    <button onclick="window.location.href = '<s:property value="#parameters['redirect']" />'">Volver</button>
                    <button id="btnEditarTiposPresentacion">Editar Tipos de Presentación</button>
                </div>
            </div>
            <div id="content"></div>
        </div>
    </body>
</html>