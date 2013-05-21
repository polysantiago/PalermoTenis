<%-- 
    Document   : EditarSuccess
    Created on : 12/09/2009, 16:23:05
    Author     : Poly
--%>
<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <style type="text/css" >
            body {background-color: #FFFFFF;}
            img {float: left;}
        </style>
    </head>
    <body>
        <img alt="Success" src="/admin/images/success.gif"/><h2><s:property value="nombre" /> ha sido actualizado con éxito!</h2>
    </body>
</html>
