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
        <title>Editar Precios por Presentación</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/CRUDFunctions.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/editFunctions.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js" ></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/precio/precio.presentable.pack.js" ></script>
        <style type="text/css">
            #main {width: 1024px;}
            #main table.list {width: 980px;}
            #main table td.moneda select{width:55px}            
            #functions {width: auto;text-align:center;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:include value="/WEB-INF/jspf/admin_dialogs.jspf" />
            <div>
                <s:hidden value="%{producto.id}" id="productoId" cssClass="globalElement"/>
                <s:hidden value="%{producto.tipoProducto.id}" id="tipoProductoId" cssClass="globalElement" />
                <table class="list" align="center">
                    <thead>
                        <tr>
                            <th colspan="11">
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
                            <td>Moneda</td>
                            <td>Pago</td>
                            <td>Cuotas</td>
                            <td>Tipo de Presentación</td>
                            <td>Presentación</td>
                            <td>Valor</td>
                            <td>En Oferta</td>
                            <td>Oferta</td>
                        </tr>
                        <s:iterator value="producto.preciosCantidad" var="p">
                            <tr>
                                <td class="moneda" val="<s:property value="#p.id.moneda.id" />" name="newMonedaId">
                                    <s:select name="monedaId" list="monedas" listKey="id" listValue="simbolo" value="#p.id.moneda.id" theme="css_xhtml" />
                                </td>
                                <td class="pago" val="<s:property value="#p.id.pago.id" />" name="newPagoId">
                                    <s:select name="pagoId" list="pagos" listKey="id" listValue="nombre" value="#p.id.pago.id" theme="css_xhtml"/>
                                </td>
                                <td class="cuotas" val="<s:property value="#p.id.cuotas" />" name="newCuotas">
                                    <s:textfield value="%{#p.id.cuotas}" theme="css_xhtml" size="1" name="cuotas" />
                                </td>
                                <td class="tipoPresentacion">
                                    <s:select name="tipoPresentacionId" list="#p.id.producto.tipoProducto.tiposPresentacion" listKey="id" listValue="nombre" value="#p.id.presentacion.tipoPresentacion.id" theme="css_xhtml" />
                                </td>
                                <td class="presentacion" val="<s:property value="#p.id.presentacion.id" />" name="newPresentacionId">
                                    <s:select name="presentacionId" list="#p.id.presentacion.tipoPresentacion.presentaciones" listKey="id" listValue="nombre" value="#p.id.presentacion.id" theme="css_xhtml" />
                                </td>
                                <td class="valor">
                                    <s:textfield value="%{#p.valor}" theme="css_xhtml" size="4" name="valor"/>
                                </td>
                                <td class="enOferta">
                                    <s:checkbox name="enOferta" value="#p.enOferta" theme="css_xhtml" />
                                </td>
                                <td class="valorOferta">
                                    <s:if test="#p.enOferta">
                                        <s:textfield name="valorOferta" value="%{#p.valorOferta}" theme="css_xhtml" size="4" disabled="false"/>
                                    </s:if>
                                    <s:else>
                                        <s:textfield name="valorOferta" value="%{#p.valorOferta}" theme="css_xhtml" size="4" disabled="true" cssClass="ui-state-disabled"/>
                                    </s:else>
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
                <button id="btnAgregarNuevo">Agregar Precio</button>
                <button id="btnEditarMonedas">Editar Monedas</button>
                <button id="btnEditarPagos">Editar Forma de Pago</button>
                <button id="btnEditarPresentaciones">Editar Presentaciones</button>
            </div>
        </div>
    </body>
</html>
