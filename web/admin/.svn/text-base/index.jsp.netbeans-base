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
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <h2>Bienvenido al Panel de Control de Palermo Tenis!</h2>
                <h4>Utilice el panel de control a su derecha para agregar o modificar objetos</h4>
                <p>
                    <span id="idUsuario">Usuario: <sec:authentication property="principal.username" /></span><br />
                    <s:url action="j_spring_security_logout" var="logoutUrl" />
                    <s:a href="%{#logoutUrl}">Logout</s:a>
                </p>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>