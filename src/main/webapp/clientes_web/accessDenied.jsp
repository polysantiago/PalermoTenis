<%--
    Document   : Carrito
    Created on : 19/09/2009, 17:03:43
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.web.access.AccessDeniedHandlerImpl" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:include page="/WEB-INF/jspf/main_head.jspf" flush="false" />
        <style type="text/css">
            body {overflow:visible;}
            #login{
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 175px;
            }
        </style>
    </head>
    <body>
        <div id="todo">
            <jsp:include page="/WEB-INF/jspf/header.jspf" flush="false" />
            <div id="login">
                <div>
                    <img alt="stop" src="/admin/images/stop.png" />
                </div>
                <div>
                    <font color="red">
                        ACCESO DENEGADO<br/>
                        Su usuario no posee los suficientes privilegios para acceder a esta sección del sitio.<br/>
                        Motivo: <%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY) %><br/>
                        Probablemente usted haya ingresado al administrador y no se ha deslogueado. Recuerde que no puede efectuar pedidos ni entrar al área seagura para cliente con su usuario del Panel de Control.<br />
                        <br />
                        <a href="/clientes_web/Logout">Ingresar con otro usuario</a>
                    </font>
                </div>
            </div>
        </div>
    </body>
</html>