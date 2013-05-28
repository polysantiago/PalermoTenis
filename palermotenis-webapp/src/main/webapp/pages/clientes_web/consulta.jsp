<%@ taglib prefix="s" uri="/struts-tags"%>
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