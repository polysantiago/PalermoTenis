<%--
    Document   : Palermo
    Created on : 23/11/2009, 18:52:24
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-9"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <s:include value="/WEB-INF/jspf/static_css.jspf" />
        <script type="text/javascript">_gaq.push(['_trackPageview', '/Consulta']);</script>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div class="contenido">
                <div><span class="gris">Completá este formulario con tu consulta y te responderemos a la brevedad</span></div>
                <s:actionerror />
                <s:form action="EnviarConsulta" namespace="/clientes_web" validate="true">
                    <s:textfield name="nombre" label="Nombre" required="true" value='%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.cliente.nombre}'/>
                    <s:textfield name="telefono" label="Teléfono" value='%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.cliente.telefono}' />
                    <s:textfield name="email" label="Mail" required="true" value='%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.username}' />
                    <s:textarea name="consulta" cols="30" required="true" rows="12" label="Consulta" />
                    <s:submit value="Enviar" cssClass="enviar" />
                </s:form>
            </div>
        </div>
    </body>
</html>