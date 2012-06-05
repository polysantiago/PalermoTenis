<%--
    Document   : index
    Created on : 10/07/2009, 18:53:33
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Panel de Control - PalermoTenis</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <style type="text/css">.err{font-size:11px;color:red;}</style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <h2>Resumen de Venta</h2>
                <fieldset>
                    <legend>Datos cliente</legend>
                    <s:property value="listado.cliente.nombre" />
                    <s:property value="listado.cliente.telefono" />
                    <s:property value="listado.cliente.direccion.direccion" />
                    <s:property value="listado.cliente.direccion.ciudad" />
                </fieldset>
                <s:if test="#parameters['error'][0] == 'zp'">
                    <div>
                        <span class="err">Existen algunos precios sin valor. Por favor, asígneles un precio y haga click en 'Actualizar Precios'.</span>
                    </div>
                </s:if>
                <fieldset>
                    <legend>Resumen</legend>
                    <s:form action="ListadoAction">
                        <s:hidden value="%{listado.id}" name="listadoId" />
                        <s:include value="Listado.jsp" />
                        <table>
                            <tr>
                                <td><s:submit value="Actualizar Precios" name="accion" theme="css_xhtml" align="center" /></td>
                                <td><s:submit value="Actualizar Cantidades" name="accion" theme="css_xhtml" align="center" /></td>
                                <td><s:submit value="Registrar Venta" action="ConfirmarVenta_confirmarNuevaVenta" disabled="%{#parameters['error'] != null}" theme="css_xhtml" align="center"/></td>
                            </tr>
                        </table>
                    </s:form>
                </fieldset>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>
