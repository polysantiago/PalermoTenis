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
                $("#ciudad").autocomplete("/GetCiudades",
                {
                    minChars:3,
                    matchSubset:1,
                    matchContains:1,
                    cacheLength:10,
                    width: 310,
                    formatItem: function(row) {
                        var cp = "";
                        if(row[2]!="null") cp = " (" + row[2] + ")";
                        return row[1] + "<br><i>" + row[3] + cp + ", " + row[4] + "</i>";
                    },
                    formatResult: function(row) {
                        $("#ciudadId").val(row[0]);
                        var cp = "";
                        if(row[2]!="null") cp = " (" + row[2] + ")";
                        return row[1] + " - " + row[3] + cp + ", " + row[4];
                    },
                    autoFill: true,
                    selectOnly:1
                });
            });
        </script>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <h2>Registrar Nuevo Cliente</h2>
                <s:actionerror />
                <s:actionmessage />
                <s:fielderror />
                <s:form action="ClienteAction_create" namespace="/admin/crud" method="post" validate="true">
                    <s:textfield label="Nombre" name="cliente.nombre" required="true"/>
                    <s:textfield label="E-Mail" name="cliente.email" required="true"  />
                    <s:textfield label="Teléfono" name="cliente.telefono" />
                    <s:textfield label="Dirección" name="cliente.direccion.direccion" size="80"/>
                    <s:textfield label="Ciudad" id="ciudad" name="cliente.direccion.ciudad" size="80"/>
                    <s:checkbox label="Suscribir al Newsletter" name="suscriptor" value="true"  />
                    <s:submit id="btnEnviar" align="left" type="image" src="/images/boton-enviar.jpg" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnEnviar','','/images/boton-enviar-over.jpg',1)" />
                </s:form>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>