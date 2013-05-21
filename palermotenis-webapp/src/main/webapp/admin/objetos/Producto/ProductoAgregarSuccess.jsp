<%-- 
    Document   : AgregarSuccess
    Created on : 11/07/2009, 17:48:23
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
        <script charset="iso-8859-1" src="/js/jquery/pack/jquery-1.4.2.pack.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(parent.secondOptionCallback);
        </script>
        <style type="text/css">
            body {background-color: #FFFFFF;}
            h1{float:left;}
        </style>
    </head>
    <body>
        <div id="contenido">
            <img alt="Exito" src="/admin/images/success.gif" />
            <h1>El producto se ha agregado con éxito!</h1>
        </div>
    </body>
</html>
