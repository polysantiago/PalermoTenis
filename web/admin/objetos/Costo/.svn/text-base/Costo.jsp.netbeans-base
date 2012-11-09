<%--
    Document   : Editar_PreciosUnidad
    Created on : 17/08/2009, 20:24:40
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="pragma" content="no-cache" />
        <title>Editar Costos</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/CRUDFunctions.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/editFunctions.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js" ></script>
        <s:if test="producto.tipoProducto.presentable">
            <script charset="ISO-8859-1" type="text/javascript" src="/admin/js/pack/costo/costo.presentable.pack.js" ></script>
        </s:if>
        <s:else>
            <script charset="ISO-8859-1" type="text/javascript" src="/admin/js/pack/costo/costo.simple.pack.js" ></script>
        </s:else>
        <style type="text/css">
            #main {width: 850px;}
            #main table.list {width: 720px;}
            #main table td.moneda select{width:55px}
            #functions {width: auto;text-align:center;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:include value="/WEB-INF/jspf/admin_dialogs.jspf" />
            <div>
                <s:hidden value="%{producto.id}" id="productoId" cssClass="globalElement" />
                <s:hidden value="%{producto.tipoProducto.id}" id="tipoProductoId" cssClass="globalElement" />
                <table class="list" align="center">
                    <thead>
                        <tr>
                            <th colspan="8">
                                <s:property value="producto.tipoProducto.nombre + ' > '" />
                                <s:property value="producto.modelo.marca.nombre + ' > '" />
                                <s:iterator value="producto.modelo.padres" var="m">
                                    <s:property value="#m.nombre + ' > '" />
                                </s:iterator>
                                <s:property value="producto.modelo.nombre" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="yellow">
                            <s:if test="producto.tipoProducto.presentable">
                                <td>Tipo de Presentacion</td>
                                <td>Presentacion</td>
                            </s:if>
                            <td>Moneda</td>
                            <td>Costo</td>
                            <td>Proveedor</td>
                        </tr>
                        <s:iterator value="producto.costos" var="c">
                            <tr val="<s:property value="#c.id" />" name="costoId">
                                <s:if test="producto.tipoProducto.presentable">
                                    <td class="tipoPresentacion">
                                        <s:select name="tipoPresentacionId" list="producto.tipoProducto.tiposPresentacion" listKey="id" listValue="nombre" value="#c.presentacion.tipoPresentacion.id" theme="css_xhtml" />
                                    </td>
                                    <td class="presentacion">
                                        <s:select name="presentacionId" list="#c.presentacion.tipoPresentacion.presentaciones" listKey="id" listValue="nombre" value="#c.presentacion.id" theme="css_xhtml" />
                                    </td>
                                </s:if>
                                <td class="moneda" val="<s:property value="#c.moneda.id" />">
                                    <s:action name="MonedaAction_show" ignoreContextParams="true" namespace="/admin/crud" var="m" executeResult="false"/>
                                    <s:select list="#m.monedas" name="monedaId" listKey="id" listValue="simbolo" value="#c.moneda.id" theme="css_xhtml" />
                                </td>
                                <td class="costoVal">
                                    <s:textfield value="%{#c.costo}" theme="css_xhtml" size="4" name="costoVal" />
                                </td>
                                <td class="pago" val="<s:property value="#c.proveedor.id" />">
                                    <s:action name="ProveedorAction_show" ignoreContextParams="true" namespace="/admin/crud" var="p" executeResult="false"/>
                                    <s:select list="#p.proveedores" name="proveedorId" listKey="id" listValue="nombre" value="#c.proveedor.id" theme="css_xhtml"/>
                                </td>
                                <td>
                                    <button class="btnEditar">Editar</button>
                                </td>
                                <td>
                                    <button class="btnBorrar">Borrar</button>
                                </td>
                            </tr>
                        </s:iterator>

                    </tbody>
                </table>
            </div>
            <div id="functions">
                <button id="btnAgregarNuevo">Agregar Costo</button>
                <button id="btnEditarMonedas">Editar Monedas</button>
                <button id="btnEditarProveedores">Editar Proveedores</button>
            </div>
        </div>
    </body>
</html>
