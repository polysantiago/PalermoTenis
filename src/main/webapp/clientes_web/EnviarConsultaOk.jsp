<%-- 
    Document   : EnviarPedidoOk
    Created on : 24/09/2009, 18:02:27
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <script type="text/javascript">
            $(function(){_gaq.push(['_trackEvent', 'Consultas', 'Enviar']);});
        </script>
        <style type="text/css">
            #okMsg {width:725px;float:left;padding-left:15px;padding-top:15px;display:block;position:absolute;top:175px;}
            #okMsg a,#okMsg a:visited,#okMsg a:active {text-decoration: none;color: #F86402;}
            #okMsg a:hover {text-decoration: underline;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="okMsg">
                <h4>Su consulta ha sido enviada. En breve nos estaremos comunicando con usted.</h4>
                <s:a action="index" namespace="/" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','/images/boton-seguir-over.jpg',1)">
                    <img src="/images/boton-seguir.jpg" name="Image5" width="127" height="19" border="0" id="Image5" alt="Seguir" />
                </s:a>
            </div>
        </div>
    </body>
</html>
