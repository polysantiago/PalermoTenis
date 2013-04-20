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
        <script type="text/javascript">_gaq.push(['_trackPageview', '/Registracion']);</script>
        <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/carrito.pack.js"></script>
        <style type="text/css">
            body {overflow:visible;}

            #registrarForm {
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 110px;
            }

            .form-registrar{
                width: 710px;
                font-size: 11px;
                border-width: 1px;
                border-color: #CCCCCC;
                border-style: solid;
                background-color: #e5e5e5;
            }
            .form-registrar td {border-spacing: 0px;padding: 5px;}            
            #registrarForm {margin-top: 35px;width: 712px;}
            #registrarForm #titulo {background-color: #cccccc;font-size: 11px;border:1px solid #CCCCCC;}
            .errorMessage {color:red; padding: 25px;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="registrar">
                <div id="registrarForm">
                    <div id="titulo"><strong>Completa este formulario con tus datos para registrarte en PalermoTenis</strong></div>
                    <s:actionerror />
                    <s:actionmessage />
                    <s:fielderror />
                    <s:form cssClass="form-registrar" action="Registrar" namespace="/clientes_web" method="post">
                        <s:textfield label="Nombre" name="nombre" required="true"/>
                        <s:textfield label="E-Mail" name="email" required="true"  />
                        <s:textfield label="Teléfono" name="telefono" />
                        <s:textfield label="Dirección" name="direccion" size="80"/>
                        <s:textfield label="Ciudad" id="ciudad" name="ciudad" size="80"/>
                        <s:password label="Contraseña" name="contrasenia" required="true" />
                        <s:password label="Repetir contraseña" name="rptContrasenia" required="true" />
                        <s:checkbox label="Suscribirse al Newsletter" name="suscriptor" value="true"  />
                        <s:submit id="btnEnviar" align="left" type="image" src="/images/boton-enviar.jpg" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnEnviar','','/images/boton-enviar-over.jpg',1)" />
                    </s:form>
                </div>
            </div>
        </div>
    </body>
</html>