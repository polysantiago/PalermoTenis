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
                <h2>Cambiar mis datos</h2>
                <s:actionerror />
                <s:form action="MiUsuarioAction_edit" namespace="/admin/crud" validate="true">
                    <s:textfield label="Usuario" name="username" value="%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.usuario}" />
                    <s:password label="Clave" name="contrasenia" />
                    <s:password label="Repetir Clave" name="rptContrasenia" />
                    <s:submit value="Actualizar" />
                </s:form>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>