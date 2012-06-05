<%--
    Document   : Carrito
    Created on : 19/09/2009, 17:03:43
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/carrito.pack.js"></script>
        <style type="text/css">
            body {overflow:visible;}
            #loginForm{
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 150px;
            }

            #loginWrapper {float:left;margin-right: 25px;}

            #titulo {background-color: #cccccc;font-size: 11px;border:1px solid #CCCCCC;padding: 7px;}
            .form-ingresar{
                width: 346px;
                margin-top: 0px;
                font-size: 11px;
                border-width: 1px;
                border-color: #CCCCCC;
                border-style: solid;
                background-color:#E5E5E5;
            }

            .form-ingresar td {padding: 7px;}

            #registrar {
                background-color:#FD9F01;
                border-color:-moz-use-text-color #CCCCCC #CCCCCC;
                border-style:none solid solid;
                border-width:medium 1px 1px;
                font-size:11px;
                padding:7px;
            }

            #registrar a{font-size: 11px;color: #000000;}
            .label, .checkboxLabel {font-size: 11px;font-weight: normal;}
            .error{margin-bottom:5px;width:346px;}
            .login-error{font-size:11px;font-weight:bold;color:red;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
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
                            <s:submit id="btnIngresar" align="right" type="image" src="/images/boton-ingresar.jpg"  onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnIngresar','','/images/boton-ingresar-over.jpg',1)" />
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
        </div>
    </body>
</html>
