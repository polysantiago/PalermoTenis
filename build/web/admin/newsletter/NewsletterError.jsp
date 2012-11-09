<%-- 
    Document   : index
    Created on : 10/07/2009, 18:53:33
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
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <img alt="ERROR" src="/admin/images/error.gif" />
                <h1>¡Ha ocurrido un error al enviar el newsletter!</h1>
                <h3>Causa raíz:</h3>
                <h2><s:property value="errorMessage" /></h2>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>