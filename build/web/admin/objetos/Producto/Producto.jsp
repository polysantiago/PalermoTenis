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
        <script charset="ISO-8859-1" type="text/javascript" src="/admin/js/pack/producto/producto.pack.js" ></script>
        <style type="text/css">
            body {background-color: #FFFFFF;}
            .value {margin-left:2px;width:148px;}           
            #imagenes{float: left;}
            #navFunciones {margin: 5px 0 10px 5px;}
            #navFunciones a{margin-right: 5px;text-decoration: none;}
            pre.err {color: red; font-size: 10px;}
            .img {margin: 10px 15px;}
            .img a.lightbox {cursor: -moz-zoom-in;}
            .img a.lightbox:focus {outline: none;}
            img.rsz {cursor:move;}
            #frmEditarProducto{display: inline;float:left;}
            select[multiple]{height:auto;}
        </style>
    </head>
    <body>
        <div id="wrapper">
            <div id="navFunciones">
                <a class="ui-button" href="javascript:addImagePopUp(<s:property value="producto.modelo.id" />);">Agregar Imagen</a>
                <!--<a class="ui-button" href="javascript:editStockPopUp(<s:property value="producto.id" />,<s:property value="producto.tipoProducto.clasificable || producto.tipoProducto.presentable" />);">Stock</a>-->
                <s:url id="stockUrl" action="Stock" namespace="/admin/estadisticas">
                    <s:param name="modeloId" value="%{producto.modelo.id}" />
                    <s:param name="tipoProductoId" value="%{producto.tipoProducto.id}" />
                </s:url>
                <s:a cssClass="ui-button" href="%{stockUrl}" target="_blank">Stock</s:a>
                <a class="ui-button" href="javascript:editPrecios(<s:property value="producto.id" />,<s:property value="producto.tipoProducto.presentable" />);">Precios</a>
                <a class="ui-button" href="javascript:editCostos(<s:property value="producto.id" />);">Costos</a>
            </div>
            <s:form action="ProductoAction_edit" namespace="/admin/crud" id="frmEditarProducto" >
                <s:checkbox label="Activo" labelposition="left" value="producto.activo" name="activo"  cssClass="chkActivo" id="chkActivo"/>
                <s:select label="Categorias" list="categorias" listKey="id" listValue="nombre" multiple="true" value="producto.modelo.categorias.{id}" name="categoriasIds" cssClass="ui-selectmenu ui-widget ui-state-default ui-widget-content ui-corner-bottom ui-selectmenu-menu-dropdown"/>
                <s:select label="Marca" disabled="true" list="marcas" listKey="id" listValue="nombre" value="marca.id" name="marcaId" cssClass="coolSelectMenu" />
                <s:select label="Tipo de Producto" disabled="true" list="tiposProducto" listKey="id" listValue="nombre" value="tipoProducto.id" name="tipoProductoId" cssClass="coolSelectMenu" />
                <s:textfield label="Nombre" value="%{producto.modelo.nombre}" name="nombre" required="true" cssClass="ui-widget ui-state-default ui-corner-all" />
                <s:textarea label="Descripcion" value="%{producto.descripcion}" name="descripcion" cols="16" />
                <s:hidden name="modeloId" value="%{modeloId}" />
                <s:hidden name="productoId" value="%{producto.id}" id="productoId"/>
                <s:hidden name="tipoProductoId" value="%{tipoProducto.id}" />
                <s:hidden name="marcaId" value="%{producto.modelo.marca.id}" />

                <s:if test="!tipoProducto.tipoAtributoSimples.empty">
                    <br />
                    <tr><td><s:text name="atributo.tipo.simple" /></td></tr>
                    <s:iterator value="tipoProducto.tipoAtributoSimples" var="t">
                        <s:set value="producto.atributosSimples.{?#this.tipoAtributo.id == #t.id}.{valor.unidad}" var="valor" />

                        <s:if test="#valor.size > 0">
                            <s:set value="#valor[0]" var="val" />
                        </s:if>
                        <s:else>
                            <s:set value="" var="val" />
                        </s:else>

                        <s:if test="#t.clase.name == 'java.lang.Boolean'">
                            <s:checkbox label="%{#t.nombre}" name="atributosSimples[%{#t.id}]"
                                        labelposition="left"
                                        value="#val" cssClass="chkBinario"
                                        fieldValue="true"/>
                        </s:if>
                        <s:else>
                            <s:if test="#t.unidad != null">
                                <s:textfield name="atributosSimples[%{#t.id}]"
                                             label="%{#t.nombre}"
                                             value="%{#val}"
                                             cssClass="value ui-widget ui-state-default ui-corner-all" tooltip="%{#t.unidad.nombre}" />
                            </s:if>
                            <s:else>
                                <s:textfield name="atributosSimples[%{#t.id}]"
                                             label="%{#t.nombre}"
                                             value="%{#val}"
                                             cssClass="value ui-widget ui-state-default ui-corner-all" />
                            </s:else>
                        </s:else>
                    </s:iterator>
                </s:if>

                <s:if test="!tipoProducto.tiposAtributoTipados.empty">
                    <br />
                    <tr><td><s:text name="atributo.tipo.tipado" /></td></tr>
                    <s:iterator value="tipoProducto.tiposAtributoTipados" var="t">
                        <s:select name="atributosTipados[%{#t.id}]"
                                  label="%{#t.nombre}" list="#t.valoresPosibles"
                                  listKey="id" listValue="nombre"
                                  value="producto.atributosTipados.{?#this.tipoAtributo.id == #t.id}.{valorPosible.id}"
                                  emptyOption="true" headerKey="-1" cssClass="coolSelectMenu"/>
                    </s:iterator>
                </s:if>

                <s:if test="!tipoProducto.tiposAtributoMultiples.empty">
                    <br />
                    <tr><td><s:text name="atributo.tipo.multiple" /></td></tr>
                    <s:iterator value="tipoProducto.tiposAtributoMultiples" var="t">
                        <s:select name="atributosMultiples[%{#t.id}]" label="%{#t.nombre}"
                                  list="#t.valoresPosibles" listKey="id" listValue="nombre"
                                  value="producto.atributosMultiplesValores.{?#this.tipoAtributo.id == #t.id}.{valorPosible.id}"
                                  multiple="true"
                                  cssClass="ui-selectmenu ui-widget ui-state-default ui-widget-content ui-corner-bottom ui-selectmenu-menu-dropdown" />
                    </s:iterator>
                </s:if>

                <s:submit value="Actualizar" cssClass="ui-button" />
            </s:form>

            <div id="imgWrapper">
                <s:action name="GetImagenes" namespace="/"  executeResult="true">
                    <s:param name="modeloId" value="%{producto.modelo.id}" />
                </s:action>
            </div>
        </div>
    </body>
</html>

