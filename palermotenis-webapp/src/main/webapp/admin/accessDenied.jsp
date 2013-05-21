<%--
    Document   : index
    Created on : 10/07/2009, 18:53:33
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@page import="org.springframework.security.web.access.AccessDeniedHandlerImpl" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <jsp:include page="/WEB-INF/jspf/admin_header.jspf" flush="false" />
        <style type="text/css">
            label {display: inline;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <div>
                    <img alt="stop" src="/admin/images/stop.png" />
                </div>
                <div>
                    <font color="red">
                        ACCESO DENEGADO<br/>
                        Su usuario no posee los suficientes privilegios para acceder a esta sección del sitio.<br/>
                        Motivo: <%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY) %><br/>
                        <br />
                        <a href="/admin/j_spring_security_logout.action">Ingresar con otro usuario</a>
                    </font>
                </div>
            </div>
        </div>
    </body>
</html>