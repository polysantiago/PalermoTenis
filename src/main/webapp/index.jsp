<%-- 
    Document   : index
    Created on : 29/03/2009, 12:02:17
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <!--[if lte IE 6]>
        <style type="text/css">
            * {border: 0;padding: 0;margin: 0;}
            html{height:100%;overflow:hidden;width:100%;}
            #contenidos{
                height:70%;
                overflow-y:auto;
                overflow-x:hidden;
                top:154px;
                width:auto;
                right:-17px;
                bottom:20px
            }
            #contenidos .central{height:100%;position:relative;}
            .menu{
                min-height: 100% !important; /*For all browser except IE */
                height: auto !important;
                height: 100%; /*IE hack since IE doesn't understand min-height */
            }
        </style>
        <![endif]-->
        <style type="text/css">
            #buscar-texto {
                background-color: #F86402;
                border:medium none #FFFFFF;
                font-family:'Segoe UI','Lucida Sans Unicode','Lucida Grande','sans-serif';
                margin-right:0;
                margin-top:0;
                text-indent:0;
                width:140px;
            }
            #buscar-flecha{cursor: pointer;}
            #news-loader{text-align: center;}
            #contenidos.loading {background: url(/images/ajax-loader-contenidos.gif) no-repeat center center;}
        </style>
    </head>
    <body onload="MM_preloadImages('images/agregar-over.gif','images/envio-over.gif','/images/ajax-loader-cotizacion.gif')">

        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />

            <!-- Área de menús -->
            <div id="menu-principal" class="menu" >
                <div align="center" style="display: none;" class='loader'>
                    <img src='images/ajax-loader-primario.gif' alt='Loading...' />
                </div>
                <ul id="principal"></ul>

                <div id="buscador-container">
                    <div id="buscador">
                        <div class="buscar">
                            <table border="0" cellspacing="0" cellpadding="0" >
                                <tr>
                                    <td align="right" valign="top">
                                        <input type="text" id="buscar-texto" size="21" value="NEWSLETTER" onFocus="this.value='';" onblur="if(!this.value) this.value='NEWSLETTER';" />
                                    </td>
                                    <td align="left" valign="top">
                                        <img id="buscar-flecha" src="images/news-mas.jpg" />
                                    </td>
                                </tr>
                            </table>                            
                            <div id="news-loader" style="display:none;"><img alt="Cargando..." src="/images/indicator.gif" /></div>
                        </div>
                        <div id="news-cartel">Ingrese su dirección de correo para suscribirse a nuestro newsletter</div>
                    </div>
                </div>
            </div>


            <div style="display: none;" id="menu-secundario" class="menu" >
                <div align="center" style="display: none;" class='loader'>
                    <img src='images/ajax-loader-secundario.gif' alt='Loading...' />
                </div>
                <ul id="secundario"></ul>
            </div>

            <!-- Contenidos principales -->
            <div id="contenidos">
                <div align="center" style="display: none;" class='loader'>
                    <img src='images/ajax-loader-contenidos.gif' alt='Loading...' />
                </div>
                <div class="central"></div>
            </div>
        </div>
    </body>
</html>
