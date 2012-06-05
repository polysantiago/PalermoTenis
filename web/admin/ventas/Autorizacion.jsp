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
        <style type="text/css">
            .err{font-size:11px;color:red;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <fieldset>
                    <s:set value="false" var="edit" />
                    <s:action name="ListarListado" executeResult="true" ignoreContextParams="false" />
                </fieldset>
                <h2>Se necesita autorización</h2>
                <s:if test="#parameters['login_error'][0] == 'bc'">
                    <div>
                        <span class="err">Credenciales erróneas</span>
                    </div>
                </s:if>
                <s:if test="#parameters['login_error'][0] == 'ad'">
                    <div>
                        <span class="err">Acceso denegado. Las credenciales ingresadas no pertenecen a un supervisor</span>
                    </div>
                </s:if>
                <sec:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR')">
                    <fieldset>
                        <span>Solamente un supervisor puede asignar nuevos precios.</span><br />
                        <span>Como usted no está autorizado, es necesario la autorización de un supervisor.</span>                        
                        <s:form action="Autorizar">
                            <s:textfield label="Usuario" name="usuario" />
                            <s:password label="Clave" name="clave" />
                            <s:hidden value="%{#parameters['listadoId']}" name="listadoId" />
                            <s:submit value="Enviar" />
                        </s:form>
                        <span>De lo contrario, puede pedir autorización por correo. Por favor, seleccione un supervisor.</span>
                        <s:form>
                            <s:select label="Supervisor" list="#{'poly','raketbroker'}" />
                            <s:hidden value="%{#parameters['listadoId']}" name="listadoId" />
                            <s:submit value="Pedir autorización" />
                        </s:form>
                    </fieldset>
                </sec:authorize>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>
