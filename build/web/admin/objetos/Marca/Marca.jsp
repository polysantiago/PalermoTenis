<%-- 
    Document   : Marca
    Created on : 02/09/2009, 22:05:24
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
        <link rel="stylesheet" type="text/css" href="/admin/css/min/edit-style.min.css" />
        <script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/marca/marca.pack.js"></script>
        <style type="text/css">
            #izq{float:left;}
            fieldset {width: 150px;}
            #imgWrapper {float:left; width:600px;margin-left: 30px;}
            .imagenMarca {float:left;margin: 10px;}
            .imagenMarca img { width: 150px;height: 100px;}
            div.addBtn {width: 16px; height: 16px; background: url(/admin/images/add-result.png) bottom no-repeat; cursor: pointer;}
            div.addBtn:hover{background-position: top;}
        </style>
    </head>
    <body>
        <div id="main">
            <div id="izq">
                <fieldset>
                    <legend>Marcas</legend>
                    <table align="center" cellspacing="10" id="marcas">
                        <s:iterator value="marcas" var="m">
                            <tr class="marca" val="<s:property value="#m.id" />">
                                <td class="nombre">
                                    <span><s:property value="#m.nombre" /></span>
                                    <s:textfield size="20" value="%{#m.nombre}" cssStyle="display: none;"  theme="css_xhtml"/>
                                </td>
                                <td>
                                    <a href="#" onclick="borrarMarca(<s:property value="#m.id" />);">
                                        <img border="0" src="/admin/images/delete_small.gif" alt="Borrar" title="Borrar" />
                                    </a>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </fieldset>
                <fieldset style="text-align: center;">
                    <legend>Funciones</legend>
                    <button id="btnAgregar">Agregar</button>
                </fieldset>
            </div>
            <div id="imgWrapper">
                <s:iterator value="marcas" var="m">
                    <div class="imagenMarca">
                        <img src="MarcaAction_showImage?marcaId=<s:property value="#m.id" />" alt="Marca" />
                    </div>
                </s:iterator>
            </div>
            <s:include value="/WEB-INF/jspf/admin_nav.jspf" />
        </div>
    </body>
</html>
