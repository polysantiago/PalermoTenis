<%-- 
    Document   : Stock_all
    Created on : 11/09/2009, 00:40:29
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
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/stock/stock.clasificable.pack.js"></script>
        <style type="text/css">
            #main {width: 350px;}
            td.unidad {cursor: default;}
            td.unidad input {margin: 0 auto; text-align: center;}
        </style>
    </head>
    <body>
        <div id="main">
            <table class="list" align="center">
                <thead>
                    <tr>
                        <th colspan="4">
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
                        <s:if test="producto.tipoProducto.clasificable">
                            <td>Tipo de Atributo</td>
                            <td>Valor</td>
                        </s:if>
                        <s:if test="producto.tipoProducto.presentable">
                            <td>Tipo de Presentacion</td>
                            <td>Presentacion</td>
                        </s:if>
                        <td>Sucursal</td>
                        <td>Stock</td>
                    </tr>
                    <s:iterator value="stocks" var="s">
                        <tr val="<s:property value="#s.id" />" class="stock">
                            <s:if test="producto.tipoProducto.clasificable">
                                <td><s:property value="#s.valorClasificatorio.tipoAtributo.nombre" /></td>
                                <td><s:property value="#s.valorClasificatorio.nombre" /></td>
                            </s:if>
                            <s:if test="producto.tipoProducto.presentable">
                                <td><s:property value="#s.presentacion.tipoPresentacion.nombre" /></td>
                                <td><s:property value="#s.presentacion.nombre" /></td>
                            </s:if>
                            <td><s:property value="#s.sucursal.nombre" /></td>
                            <td class="unidad">
                                <span title="Click derecho para editar"><s:property value="#s.stock" /></span>
                                <s:textfield size="2" value="%{#s.stock}" cssStyle="display: none;"  theme="css_xhtml"/>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <table class="list">
                <tr class="yellow">
                    <td><strong>TOTAL</strong></td>
                    <td id="total"><s:property value="total" /></td>
                </tr>
            </table>
        </div>
    </body>
</html>
