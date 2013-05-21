<%-- 
    Document   : index
    Created on : 10/07/2009, 18:53:33
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.locale-es.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.base.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.fmatter.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.common.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.celledit.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.custom.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.formedit.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.subgrid.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqmodal.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqdnr.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.import.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jsonxml.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.postext.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqueryui.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/admin/js/pack/stock/stock.pack.js"></script>
        <style type="text/css">
            .wwgrp {float: left;margin-right: 10px;}            
            #ver{float:left;margin-top:29px;}
            #VerStock {height: 50px;}
            a:hover, a:active {text-decoration: none;}
            #resultados {margin-top: 5px;}
            .ui-select{margin-left:5px;width:190px;border:none;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <s:form action="VerStock" theme="css_xhtml">
                    <s:action name="TipoProductoAction_show" namespace="/admin/crud" executeResult="false" ignoreContextParams="true" var="tp" />
                    <s:select cssClass="ui-select ui-state-default ui-corner-all" list="#tp.tiposProducto" label="Tipo de Producto" labelposition="left" listValue="nombre" listKey="id" theme="css_xhtml" name="tipoProductoId" id="tipoProductoId" headerValue="-- Todos --" headerKey="" />
                    <s:action name="MarcaAction_show" namespace="/admin/crud" executeResult="false" ignoreContextParams="true" var="m"  />
                    <s:select cssClass="ui-select ui-state-default ui-corner-all" list="#m.marcas" label="Marca" labelposition="left" listValue="nombre" listKey="id" theme="css_xhtml" name="marcaId" id="marcaId" value="marcaId" headerValue="-- Todas --" headerKey="" />
                    <s:select cssClass="ui-select ui-state-default ui-corner-all" list="{}" label="Producto" labelposition="left" theme="css_xhtml" name="modeloId" id="modeloId" headerValue="-- Todos --" headerKey="" value="#parameters['modeloId']" />
                    <s:div id="ver">
                        <s:a cssClass="ui-button" href="#" onclick="javascript: verStock();">Ver</s:a>
                    </s:div>
                </s:form>                
                <s:form action="Exportar_toExcel" theme="css_xhtml">
                    <s:submit type="image" src="/admin/images/Icono_Excel.gif" title="Exportar a Excel" />
                </s:form>
                <div id="resultados" >
                    <table class="tablesorter" id="tblResultados"></table>
                    <div id="pager" class="pager"></div>
                </div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>