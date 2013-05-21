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
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <span>Mostrando resultados <s:property value="firstResult" />-<s:property value="maxResults+firstResult-1" /> de <s:property value="total" /></span>
                <table align="center">
                    <thead>
                        <tr>
                            <th>Email</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="suscriptores" var="s">
                            <tr>
                                <td><s:property value="#s.email" /></td>
                                <td><s:checkbox value="#s.activo" theme="css_xhtml" name="activo" /></td>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
                <div>
                    <s:if test="firstResult > maxResults">
                        <s:set value="firstResult-maxResults" var="prevRes" />
                        <s:url action="SuscriptorAction_list" namespace="/admin/crud" var="prevUrl">
                            <s:param name="firstResult" value="%{#prevRes}" />
                        </s:url>
                        <span>
                            <s:a href="%{#prevUrl}">Anterior</s:a>
                        </span>
                    </s:if>
                    <s:else>
                        <span style="color: gray;">Anterior</span>
                    </s:else>

                    <s:if test="(firstResult + maxResults) < total">
                        <s:set value="firstResult+maxResults" var="nextRes" />

                        <s:url action="SuscriptorAction_list" namespace="/admin/crud" var="nextUrl">
                            <s:param name="firstResult" value="%{#nextRes}" />
                        </s:url>
                        <span>
                            <s:a href="%{#nextUrl}">Siguiente</s:a>
                        </span>
                    </s:if>
                    <s:else>
                        <span style="color: gray;">Siguiente</span>
                    </s:else>
                </div>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>