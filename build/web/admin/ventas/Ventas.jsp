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
        <script type="text/javascript">
            $(document).ready( function(){
               $("#btnBuscarPedido").click( function(){
                  $("#central").load("BuscarPedido.jsp");
               });
               $("#btnNuevaVenta").click( function(){
                  window.location.href = "/admin/ventas/NuevaVenta.jsp";
               });
            });
        </script>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <h2>Registrar una venta</h2>
                <button id="btnBuscarPedido">Buscar pedido</button>
                <button id="btnNuevaVenta">Nueva venta</button>
                <div id="central"></div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>
