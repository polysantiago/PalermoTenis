<%--
    Document   : ProductoEditable
    Created on : 06/07/2009, 17:43:25
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />        
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <script charset="ISO-8859-1" type="text/javascript" src="/admin/js/pack/modelo/modelo.pack.js" ></script>
        <style type="text/css">
            #columnaIzquierda div {width: 200px;}
            #columnaIzquierda a {margin: 0 auto;}
            #divNuevo {position: relative;text-align:center;width:200px;}
            #divNuevo button {width:150px;}
            #funciones {float: left;}
            #funciones fieldset {padding: 10px;}
            .treeview li.rootNode span {cursor: pointer;}
            iframe {border: 0px none;width: 100%;min-height: 411px;}
            .placeholder {border:1px dashed #777777;}
            .log{color:red;}
            #modelos li.ui-button {font-size:11px;display:inherit;white-space:normal;text-align:inherit;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:include value="/WEB-INF/jspf/admin_dialogs.jspf" />
            <div id="contenido">
                <div class="container">
                    <div class="izq">
                        <fieldset>
                            <legend>Tipo de Producto / Marca</legend>
                            <div id="columnaIzquierda">
                                <div>
                                    <label for="tipoProducto">Tipo de Producto:</label>
                                    <select id="tipoProducto" size="1"><option value=""><i>Cargando...</i></option></select>
                                </div>
                                <div>
                                    <label for="marca">Marca:</label>
                                    <select id="marca" size="1"><option value=""><i>Cargando...</i></option></select>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Modelo</legend>
                            <div id="columnaDerecha">
                                <div class="loader" style="display: none;">
                                    <img src="/images/oceanblue-ajax-preloaders.gif" alt="Cargando..." />
                                </div>
                                <ul id="modelos"></ul>
                                <div id="divNuevo" style="display: none;">
                                    <button class="ui-button" id="btnNuevoModelo" value="nuevo">Agregar Modelo</button>
                                    <button class="ui-button" disabled id="btnNuevoProducto" value="nuevo">Agregar Producto</button>
                                    <button class="ui-button" disabled id="btnEliminarModelo" value="nuevo">Eliminar Modelo</button>
                                </div>
                            </div>
                        </fieldset>
                    </div>


                    <div class="ctr">
                        <div class="log"></div>
                        <fieldset style="width: 580px;">
                            <legend>Producto</legend>

                            <div id="central">
                                <div class="loader" style="display: none;">
                                    <img src="/images/ajax-modelo-loader.gif" alt="Cargando..." />
                                </div>
                                <iframe id="content" style="display:none;" ></iframe>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div id="funciones">
                    <fieldset>
                        <legend>Funciones</legend>
                        <button id="btnEditarTiposProducto" class="ui-button" onclick="window.location = '/admin/crud/TipoProductoAction_show';">Editar Tipos de Producto</button>
                        <button id="btnEditarMarcas" class="ui-button" onclick="window.location = '/admin/crud/MarcaAction_show';">Editar Marcas</button>
                        <button id="btnEditarSucursales" class="ui-button" onclick="window.location = '/admin/crud/SucursalAction_show';">Editar Sucursales</button>
                    </fieldset>
                </div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>