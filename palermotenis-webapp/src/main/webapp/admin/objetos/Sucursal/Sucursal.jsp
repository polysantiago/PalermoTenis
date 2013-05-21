<%-- 
    Document   : Marca
    Created on : 02/09/2009, 22:05:24
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>        
        <title>Panel de Control - PalermoTenis</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.locale-es.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.base.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.fmatter.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.common.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.celledit.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.formedit.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqmodal.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqdnr.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.custom.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.import.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jsonxml.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.postext.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/js/jquery/pack/jquery.grid.jqueryui.pack.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="/admin/js/pack/sucursal/sucursal.pack.js"></script>        
        <style type="text/css">
            #izq{float:left;}
            fieldset {width: 150px;}
            #imgWrapper {float:left; width:600px;margin-left: 30px;}
            .imagenMarca {float:left;margin: 10px;}
            .imagenMarca img { width: 150px;height: 100px;}
            div.addBtn {width: 16px; height: 16px; background: url(/admin/images/add-result.png) bottom no-repeat; cursor: pointer;}
            div.addBtn:hover{background-position: top;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="resultados" >                
                <table class="tablesorter" id="tblResultados"></table>
                <div id="pager" class="pager"></div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>
