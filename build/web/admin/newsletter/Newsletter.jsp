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
        <script type="text/javascript">
            $(document).ready( function(){
                $("#aEnvNews").click( function(e){
                    e.preventDefault();
                    if(confirm("Esta acción enviará el newsletter a todos los usuarios registrados.\n¿Desea continuar?"))
                        do {
                            title = prompt("Por favor, ingrese el asunto del mensaje que se le enviará a los suscriptores del newsletter")
                        } while (!title && title=="");                    
                        if(title) window.location = this.href + "?subject="+title;
                });
            });
        </script>
    </head>
    <body>
        <div id="main">
            <div id="contenido">
                <p><s:a href="/newsletter/Newsletter.jpg">Ver newsletter</s:a></p>
                <p><s:a href="/admin/newsletter/EnviarNewsletter" id="aEnvNews">Enviar newsletter</s:a></p>
                <p><s:a href="/admin/crud/SuscriptorAction_list">Ver suscriptores</s:a></p>              
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>