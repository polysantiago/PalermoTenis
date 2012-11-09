<%-- 
    Document   : Agregar_imagen
    Created on : 17/08/2009, 17:33:09
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subir Imagen</title>
        <s:include value="/WEB-INF/jspf/admin_header.jspf" />
        <link rel="stylesheet" type="text/css" href="/admin/css/min/popup-style.min.css" />
        <script type="text/javascript">
            function validate(){
                if(!$("#imagen").val()){
                    alert("No se ha seleccionado ninguna imagen!");
                    return false;
                } else {
                    var regex = /[.jpg|.jpeg|.JPG|.JPEG]+$/;
                    if(!regex.test($("#imagen").val().toLowerCase())){
                        alert("El archivo seleccionado no es una imagen valida!\nSolo se permiten .jpg por el momento");
                        return false;
                    }
                }
                return true;
            }

            $(document).ready( function(){
                $(opener.document).find("#imgWrapper").load("/GetImagenes",{modeloId:<s:property value="#parameters['modeloId']" />});
            });
        </script>
        <style>
            #main {
                background-color: #FFFFFF;
                width: 600px;
            }
        </style>
    </head>
    <body>
        <div id="main">
            <s:form action="ImagenAction_upload" namespace="/admin/crud" enctype="multipart/form-data" method="post" onsubmit="return validate();">
                <s:hidden value="%{#parameters['modeloId']}" name="modeloId" id="modelo" />
                <s:file size="72" accept="image/jpeg,image/jpg,image/gif,image/png" name="imagen" id="imagen" label="Imagen" />
                <s:submit value="Subir" />
            </s:form>
        </div>
    </body>
</html>
