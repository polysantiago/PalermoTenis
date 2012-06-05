<%-- 
    Document   : AgregarError
    Created on : 11/07/2009, 17:48:53
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <link href="/admin/css/admin-style.css" rel="stylesheet" type="text/css" />
        <link href="/css/jquery/jquery.doubleSelect.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            body {background-color: #FFFFFF;}
            h1{float:left;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <img alt="Error" src="/admin/images/error.gif" />
                <h1>Ha ocurrido un error al agregar producto. Contáctese con el administrador.</h1>
                <div>
                    <span>Mensajes de error:</span>
                    <s:iterator value="errores" var="e">
                        <s:property value="#e" />
                    </s:iterator>
                </div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>