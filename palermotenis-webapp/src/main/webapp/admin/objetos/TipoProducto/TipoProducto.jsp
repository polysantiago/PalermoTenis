<%-- 
    Document   : TipoProducto
    Created on : 27/08/2009, 19:19:05
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
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/tipoProducto/tipo.producto.pack.js"></script>
        <style type="text/css">
            #izq{float:left;margin-right: 7px;}
            #tpFieldset, .funcFieldset {width:174px;padding: 5px;}
            #btnAgregarFunciones{margin: 0 auto;}
            #btnAgregarFunciones button.btn{width: 120px;}
            div.addBtn {width: 16px; height: 16px; background: url(/admin/images/add-result.png) bottom no-repeat; cursor: pointer;}
            div.addBtn:hover{background-position: top;}
            #tpFieldset table tbody tr td.nombre span{cursor:pointer;}
            #tpFieldset table tbody tr td.nombre span:hover{color: orange;}
            #tpFieldset table tbody tr td.nombre span:focus{outline:none;}
            #tpFieldset table tbody tr td.presentable{text-align: center;}
            #tpFieldset tr.tipoProducto td.nombre span.selected{font-weight: bolder;}
            #loader{float:right;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="izq">
                <fieldset id="tpFieldset">
                    <legend>Tipos de Producto</legend>
                    <table>
                        <thead>
                            <tr>
                                <th></th>
                                <th>Presentable</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="tiposProducto" var="t">
                                <tr class="tipoProducto" val="<s:property value="#t.id" />">
                                    <td class="nombre">
                                        <span><s:property value="#t.nombre" /></span>
                                    </td>
                                    <td class="presentable">
                                        <s:checkbox value="#t.presentable" name="presentable"  theme="css_xhtml" />
                                    </td>
                                    <td>
                                        <a href="#" onclick="borrarTipoProducto(<s:property value="#t.id" />);">
                                            <img border="0" src="/admin/images/delete_small.gif" alt="Borrar" title="Borrar" />
                                        </a>
                                    </td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </fieldset>
                <fieldset class="funcFieldset">
                    <legend>Agregar</legend>
                    <div id="btnAgregarFunciones" align="center">
                        <button class="btn" id="btnAgregarTipoProducto">Tipo de Producto</button>
                        <button class="btn" disabled="true" id="btnAgregarTipoAtributo">Tipo de Atributo</button>
                    </div>
                </fieldset>
                <fieldset class="funcFieldset">
                    <legend>Editar</legend>
                    <div id="btnEditarFunciones" align="center">
                        <button class="btn" id="btnEditarUnidades">Unidades</button>
                    </div>
                </fieldset>
            </div>
            <div id="container"></div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
            <div id="loader" style="display: none;">
                <img src="/images/ajax-loader-contenidos.gif" />
            </div>
        </div>
    </body>
</html>
