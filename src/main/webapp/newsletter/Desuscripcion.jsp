<%--
    Document   : EnviarPedidoOk
    Created on : 24/09/2009, 18:02:27
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <style type="text/css">
            #okMsg {
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 175px;
                font-size: 11px;
            }
            .errorMessage {color:red; padding: 25px;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="okMsg">
                <div>
                    <span>Para desuscribirse al newsletter, por favor, ingrese su dirección de correo electrónico:</span>
                </div>
                <s:actionerror />
                <s:form action="QuitarSuscriptor" namespace="/newsletter" validate="true" >
                    <s:textfield label="Email" name="email" />
                    <s:submit align="right" type="image" src="/images/boton-enviar.jpg" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnEnviar','','/images/boton-enviar-over.jpg',1)" />
                </s:form>
            </div>
        </div>
    </body>
</html>
