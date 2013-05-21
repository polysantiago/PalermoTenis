<%--
    Document   : Palermo
    Created on : 23/11/2009, 18:52:24
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-9"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <s:include value="/WEB-INF/jspf/static_css.jspf" />
        <script type="text/javascript">$(function(){_gaq.push(['_trackPageview', '/Ubicacion']);});</script>
        <style type="text/css">div.local {float:left;}</style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div class="contenido" style="width: auto;">
                <table>
                    <tr>
                        <td>
                            <p class="title"><strong>LOCAL PALERMO</strong></p><br>

                            <strong>G&uuml;emes 3372</strong> / Capital Federal<br>
                            Tel. <strong>4821 3117 / 4823 0763</strong> <br>
                            <br>
                            <hr />
                            <div>
                                <strong>Horario de atención</strong>
                                <ul>
                                    <li>Lunes a viernes de 10 a 20 hs.</li>
                                    <li>Sabados de 9 a 15 hs.</li>
                                </ul>
                            </div>
                            <hr />
                            <div>
                                <span><strong>Subte &gt;</strong> Linea D</span>
                            </div>
                            <hr />
                            <div>
                                <strong>Colectivos</strong> &gt; 12 - 15 - 29 - 39 - 64 - 68 - 92 - 106 - 109 - 111 - 128 - 140<br />
                                152 - 160 - 188
                            </div>
                            <hr />
                            <br />
                            <div style="margin: auto; text-align: center;">
                                <img src="images/mapa_local_guemes.jpg" width="427" height="336" />
                            </div>
                        </td>
                        <td style="padding-left: 20px;">
                            <p class="title"><strong>LOCAL NUÑEZ</strong></p><br>

                            <strong>Av. Libertador 7532</strong> / Capital Federal<br>
                            Tel. <strong>4701 1985</strong> <br>
                            <br>
                            <hr />
                            <div>
                                <strong>Horario de atención</strong>
                                <ul>
                                    <li>Lunes a viernes de 10 a 20 hs.</li>
                                    <li>Sabados de 10 a 19 hs.</li>
                                </ul>
                            </div>
                            <hr />
                            <div>
                                <span><strong>&nbsp;</strong></span>
                            </div>
                            <hr />
                            <div>
                                <strong>Colectivos</strong> &gt; 15 - 28 - 29 - 42 - 130<br />
                                <br />
                            </div>
                            <hr />
                            <br />
                            <div style="margin: auto; text-align: center;">
                                <img src="images/mapa_local_libertador.jpg" width="427" height="336" />
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>