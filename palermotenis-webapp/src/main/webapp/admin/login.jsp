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
        <style type="text/css">
            label {display: inline;}
        </style>
    </head>
    <body onload='document.j_spring_security_check.j_spring_security_check_j_username.focus();'>
        <div id="main">
            <div id="contenido">
                <h2>Bienvenido al Panel de Control de Palermo Tenis!</h2>
                <s:if test="#parameters['loginError'] != null">
                    <font color="red">
                        Su intento de logueo no tuvo éxito, por favor intente nuevamente.<br/>
                        Motivo: <s:property value="#session.SPRING_SECURITY_LAST_EXCEPTION.message" />.
                    </font>
                </s:if>
                <s:form action="j_spring_security_check">
                    <s:textfield name="j_username" label="Usuario" />
                    <s:password name="j_password" label="Clave" />
                    <s:checkbox name="_spring_security_remember_me" label="Recordarme" value="true" />
                    <s:submit value="Ingresar" />
                </s:form>
            </div>
        </div>
    </body>
</html>