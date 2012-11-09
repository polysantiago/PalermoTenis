<%-- 
    Document   : Pago
    Created on : 23/08/2009, 18:33:02
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Formas de pago</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/CRUDFunctions.pack.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/resizePopUp.pack.js"></script>
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/proveedor/proveedor.pack.js" ></script>
        <style type="text/css">
            #main {width: 400px;}
            #functions {width: auto;text-align:center;}
        </style>
    </head>
    <body>
        <div id="main">
            <s:include value="/WEB-INF/jspf/admin_dialogs.jspf" />
            <div>
                <table class="list" align="center">
                    <thead>
                        <tr>
                            <th colspan="3">Proveedores</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="yellow">
                            <td>Nombre</td>
                        </tr>
                        <s:iterator value="proveedores" var="p" >
                            <tr val="<s:property value="#p.id" />" name="proveedorId">
                                <td class="nombre">
                                    <s:textfield name="nombre" value="%{#p.nombre}" theme="css_xhtml" />
                                </td>
                                <td>
                                    <button class="btnEditar">Editar</button>
                                </td>
                                <td>
                                    <button class="btnBorrar">Borrar</button>
                                </td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
            </div>
            <div id="functions">
                <button onclick="window.location.href = '<s:property value="#parameters['redirect']" />'">Volver</button>
                <button id="btnAgregarNuevo">Agregar Proveedor</button>
            </div>
        </div>
    </body>
</html>
