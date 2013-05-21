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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Palermo Tenis</title>
        <script src="/js/AC_RunActiveContent.js" type="text/javascript"></script>
        <script charset="iso-8859-1" src="/js/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
        <link href="/css/styles.css" rel="stylesheet" type="text/css" />
        <style>
            #okMsg {
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 150px;
            }
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="okMsg">
                <h1>Ha ocurrido un error y su pedido no ha podido ser enviado. Por favor, reintente más tarde</h1>
            </div>
        </div>
    </body>
</html>

