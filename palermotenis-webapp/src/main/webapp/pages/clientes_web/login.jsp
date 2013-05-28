<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="login">
    <div id="loginForm">
        <div id="loginWrapper">
            <s:if test="#parameters['loginError'] != null">
                <div class="error">
                    <span class="login-error">
                        Su intento de logueo no tuvo éxito, por favor intente nuevamente.<br/>
                        Motivo: <s:property value="#session.SPRING_SECURITY_LAST_EXCEPTION.message" />.
                    </span>
                </div>
            </s:if>
            <div id="titulo"><strong>INGRESAR A MI CUENTA</strong></div>
            <s:form action="SecurityCheck" namespace="/clientes_web" cssClass="form-ingresar">
                <s:textfield name="j_username" label="Email" />
                <s:password name="j_password" label="Clave" />
                <s:checkbox name="_spring_security_remember_me" label="Recordarme" value="true" />
                <s:submit id="btnIngresar" align="right" type="image" src="/images/boton-ingresar.jpg" 
                	onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnIngresar','','/images/boton-ingresar-over.jpg',1)" />
            </s:form>
            <div id="registrar">
                <span>*Si no estás registrado, <a href="/clientes_web/Registracion"><b>clickeá aquí</b></a></span>
            </div>
        </div>

        <div id="imgWrapper">
            <img alt="Candado" src="/images/seguridad.jpg" width="175" height="176" />
        </div>
    </div>
</div>