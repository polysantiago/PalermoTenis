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
        <script type="text/javascript">
            function applyFormatting(){
                $("#frmAgregarProducto br").remove();
                applyUIFormat($("#frmAgregarProducto input:text"));
                applyUIFormat($("#frmAgregarProducto textarea"));
                $("select.coolSelectMenu").selectmenu({
                    width: 150
                });
                
                $("input.chkActivo").checkbox({
                    empty:'/css/images/empty.png'
                });
                $("input.chkBinario").checkbox({
                    empty:'/css/images/empty.png',
                    cls:'jquery-safari-checkbox'
                });
                $(".ui-button").button();
            }

            function applyUIFormat(element){
                element
                .mouseenter( function(){
                    $(this).addClass("ui-state-hover");
                })
                .focus( function(){
                    $(this).addClass("ui-state-focus");
                })
                .mouseleave( function(){
                    $(this).removeClass("ui-state-hover ui-state-focus");
                })
                .mouseout( function(){
                    $(this).removeClass("ui-state-hover ui-state-focus");
                })
                .blur( function(){
                    $(this).removeClass("ui-state-hover ui-state-focus");
                });
            }

            $(document).ready( function(){
                applyFormatting();
            });
        </script>
        <style type="text/css">
            body{background-color: #FFFFFF;}
            .value{margin-left:2px;width:148px;}
            .img{float: left;margin: 10px 15px;}
            .errorMessage{color: red; font-size: 10px;}
            select[multiple]{height:auto;}
        </style>
    </head>
    <body>
        <s:form action="ProductoAction_create" validate="true" namespace="/admin/crud" id="frmAgregarProducto">
            <s:select label="Deportes" list="deportes" listKey="id" listValue="nombre" multiple="true" name="deportesIds" required="true" cssClass="ui-selectmenu ui-widget ui-state-default ui-widget-content ui-corner-bottom ui-selectmenu-menu-dropdown" />
            <s:select label="Marca" disabled="true" list="marcas" listKey="id" listValue="nombre" value="marca.id" cssClass="coolSelectMenu" />
            <s:select label="Tipo de Producto" disabled="true" list="tiposProducto" listKey="id" listValue="nombre" value="tipoProducto.id" name="tipoProductoId" cssClass="coolSelectMenu" />
            <s:textfield label="Nombre" disabled="true" value="%{modelo.nombre}" cssClass="value ui-state-default ui-state-disabled" />
            <s:textarea label="Descripcion" name="descripcion" cols="16" />
            <s:hidden name="modeloId" value="%{modelo.id}" />
            <s:hidden name="tipoProductoId" value="%{tipoProducto.id}" />

            <s:if test="!tipoProducto.tipoAtributoSimples.empty">
                <br />
            <tr><td><s:text name="atributo.tipo.simple" /></td></tr>
            <s:iterator value="tipoProducto.tipoAtributoSimples" var="t">

                <s:if test="#t.clase.name == 'java.lang.Boolean'">
                    <s:checkbox label="%{#t.nombre}" name="atributosSimples[%{#t.id}]" labelposition="left" fieldValue="true" cssClass="chkBinario"/>
                </s:if>
                <s:else>
                    <s:if test="#t.unidad != null">
                        <s:textfield name="atributosSimples[%{#t.id}]" label="%{#t.nombre}" cssClass="value ui-widget ui-state-default ui-corner-all" tooltip="%{#t.unidad.nombre}" />
                    </s:if>
                    <s:else>
                        <s:textfield name="atributosSimples[%{#t.id}]" label="%{#t.nombre}" cssClass="value ui-widget ui-state-default ui-corner-all" />
                    </s:else>
                </s:else>
            </s:iterator>
        </s:if>

        <s:if test="!tipoProducto.tiposAtributoTipados.empty">
            <br />
            <tr><td><s:text name="atributo.tipo.tipado" /></td></tr>
            <s:iterator value="tipoProducto.tiposAtributoTipados" var="t">
                <s:select name="atributosTipados[%{#t.id}]" label="%{#t.nombre}" list="#t.valoresPosibles" listKey="id" listValue="nombre" emptyOption="true" value="-1" cssClass="coolSelectMenu" />
            </s:iterator>
        </s:if>

        <s:if test="!tipoProducto.tiposAtributoMultiples.empty">
            <br />
            <tr><td><s:text name="atributo.tipo.multiple" /></td></tr>
            <s:iterator value="tipoProducto.tiposAtributoMultiples" var="t">
                <s:select name="atributosMultiples[%{#t.id}]" label="%{#t.nombre}" list="#t.valoresPosibles" listKey="id" listValue="nombre" multiple="true" cssClass="ui-selectmenu ui-widget ui-state-default ui-widget-content ui-corner-bottom ui-selectmenu-menu-dropdown" />
            </s:iterator>
        </s:if>

        <s:submit value="Agregar" cssClass="ui-button" />
    </s:form>
</body>
</html>