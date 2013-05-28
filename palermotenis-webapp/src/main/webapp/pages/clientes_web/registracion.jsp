<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="registrar">
	<div id="registrarForm">
		<div id="titulo">
			<strong>Completa este formulario con tus datos para registrarte en PalermoTenis</strong>
		</div>
		<s:actionerror />
		<s:actionmessage />
		<s:fielderror />
		<s:form cssClass="form-registrar" action="Registrar"
			namespace="/clientes_web" method="post">
			<s:textfield label="Nombre" name="nombre" required="true" />
			<s:textfield label="E-Mail" name="email" required="true" />
			<s:textfield label="Teléfono" name="telefono" />
			<s:textfield label="Dirección" name="direccion" size="80" />
			<s:textfield label="Ciudad" id="ciudad" name="ciudad" size="80" />
			<s:password label="Contraseña" name="contrasenia" required="true" />
			<s:password label="Repetir contraseña" name="rptContrasenia" required="true" />
			<s:checkbox label="Suscribirse al Newsletter" name="suscriptor" value="true" />
			<s:submit id="btnEnviar" align="left" type="image" src="/images/boton-enviar.jpg" onmouseout="MM_swapImgRestore()"
				onmouseover="MM_swapImage('btnEnviar','','/images/boton-enviar-over.jpg',1)" />
		</s:form>
	</div>
</div>