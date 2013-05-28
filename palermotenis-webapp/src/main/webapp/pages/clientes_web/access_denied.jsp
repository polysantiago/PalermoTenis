<div id="login">
    <div>
        <img alt="stop" src="/admin/images/stop.png" />
    </div>
    <div>
        <font color="red">
            ACCESO DENEGADO<br/>
            Su usuario no posee los suficientes privilegios para acceder a esta sección del sitio.<br/>
            Motivo: <%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY) %><br/>
            Probablemente usted haya ingresado al administrador y no se ha deslogueado. Recuerde que no puede efectuar pedidos ni entrar al área seagura para cliente con su usuario del Panel de Control.<br />
            <br />
            <a href="/clientes_web/Logout">Ingresar con otro usuario</a>
        </font>
    </div>
</div>