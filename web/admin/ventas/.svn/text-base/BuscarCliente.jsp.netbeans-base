<%-- 
    Document   : BuscarCliente
    Created on : 07/02/2010, 18:08:06
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buscar Cliente</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript">
            $(document).ready( function(){
                $("#tblResultados").tablesorter({widthFixed: true, widgets: ['zebra']}).tablesorterPager({container: $("#pager")});
                $("#tblResultados tr").dblclick( function(){
                    $(opener.document).find("#clienteId").val($(this).closest("tr").attr("val"));
                    window.close();
                });
            });
        </script>
        <style type="text/css">
            body{background-color: #FFFFFF;}
            #wwgrp_BuscarCliente_filter {width: 250px;}
            table.tablesorter tbody tr {cursor: pointer;}
        </style>
    </head>
    <body>
        <div id="resultadosWrapper">
            <s:form action="BuscarCliente" namespace="/clientes_web" theme="css_xhtml">
                <s:select label="Buscar por" labelposition="left" name="filter" list="#{'E':'Email','N':'Nombre'}" />
                <s:textfield name="searchVal" />
                <s:submit value="Buscar" align="left" />
            </s:form>

            <s:if test="clientes != null">
                <script type="text/javascript">
                    $(document).ready( function(){
                        window.resizeTo(1000, ($("#resultadosWrapper").height() > 1024) ? 1000 : $("#resultadosWrapper").height()+160);
                    });
                </script>
                <div id="resultados">
                    <table class="tablesorter" id="tblResultados">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Teléfono</th>
                                <th>Dirección</th>
                                <th>Ciudad</th>
                            </tr>
                        </thead>
                        <tbody>
                            <s:iterator value="clientes" var="c">
                                <tr val="<s:property value="#c.id" />">
                                    <td><s:property value="#c.id" /></td>
                                    <td><s:property value="#c.nombre" /></td>
                                    <td><s:property value="#c.email" /></td>
                                    <td><s:property value="#c.telefono" /></td>
                                    <td><s:property value="#c.direccion.direccion" /></td>
                                    <td><s:property value="#c.direccion.ciudad" /></td>
                                </tr>
                            </s:iterator>
                        </tbody>
                    </table>
                </div>
                <div id="pager" class="pager">
                    <form>
                        <img alt="fiest" src="/css/images/first.png" class="first"/>
                        <img alt="prev" src="/css/images/prev.png" class="prev"/>
                        <input type="text" class="pagedisplay"/>
                        <img alt="next" src="/css/images/next.png" class="next"/>
                        <img alt="last" src="/css/images/last.png" class="last"/>
                        <select class="pagesize">
                            <option selected="selected" value="10">10</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option  value="40">40</option>
                        </select>
                    </form>
                </div>
            </s:if>
        </div>
    </body>
</html>
