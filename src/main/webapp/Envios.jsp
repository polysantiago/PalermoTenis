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
        <script type="text/javascript">$(function(){_gaq.push(['_trackPageview', '/Envios']);});</script>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div class="contenido">
                <table width="568" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="457" valign="top">
                            <p class="verde"><strong>Costos de envio</strong></p>
                            <p>
                                <strong>Envíos dentro de Ciudad de Buenos Aires</strong><br />
                                Se realizan por moto, el costo es de<span class="verde"> $28</span> en adelante, dependiendo del barrio.
                            </p>
                            <p>
                                <strong>Envíos a Gran Buenos Aires</strong><br />
                                Se realizan por moto, el costo es de <span class="verde">$35</span> en adelante, dependiendo de la localidad. Por favor, consultanos por mail el costo de envío a tu localidad.
                            </p>
                        </td>
                        <td width="195" align="right">
                            <span class="texto_negro">
                                <img src="images/envios/mapa_bsas.jpg" width="143" height="201" />
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" valign="top"><div class="separador"></div>
                        &nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="top"><p><strong>Envíos al interior del país</strong><br />
                            Se realizan por Omnibus, previo depósito o transferencia bancaria. El costo de envio lo abonos al recibir la encomienda, en la terminal de omnibus de tu ciudad.</p>
                            <p>Por ejemplo, a Cordoba por Chevallier sale <span class="verde">$27</span>, a Rosario <span class="verde">$21</span>, a La Rioja <span class="verde">$30</span>. Por Via Bariloche a Rio Gallegos <span class="verde">$38</span>, Bariloche <span class="verde">$30</span>, etc.</p>
                            <p><strong>Esos valores son estimados y pueden variar.</strong></p>
                            <p>IMPORTANTE: cuando realices el depósito o transferencia por el producto adquirido, agregale <span class="verde">$15</span> que es el costo de embalaje y envío desde nuestro local hasta la Terminal de Omnibus de Retiro, desde donde se despacha.</p>
                            <p>Los envíos al interior se despachan al siguiente día habil de acreditado el pago. Por razones ajenas a nuestra voluntad, no se realizan envíos a Tierra del Fuego.</p>
                        <p>NO ENVIAMOS AL INTERIOR DEL PAIS POR CORREO ARGENTINO NI POR CONTRARREEMBOLSO.</p></td>
                        <td align="right"><span class="texto_negro"><img src="images/envios/mapa_arg.jpg" width="91" height="203" /></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" valign="top"><div class="separador"></div></td>
                    </tr>
                    <tr>
                        <td valign="top"><p><strong>Envíos internacionales</strong><br />
                            se realizan por Correo Argentino, previo pago por Western Union.</p>
                            <p>Pronto agregaremos la opción de pago por PayPal.<br />
                        Te pedimos que nos consultes por mail, por el destino del envío, a fin de informarte el costo del mismo.</p></td>
                        <td align="right"><span class="texto_negro"><img src="images/envios/mapa_plan.jpg" width="163" height="101" /></span></td>
                    </tr>
                    <tr>
                        <td colspan="2" valign="top">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2"><table width="652" border="0" cellpadding="3" cellspacing="2" class="cuadro">
                                <tr>
                                    <td width="89" bgcolor="#D9D9D9"><strong>Grupo</strong></td>
                                    <td width="83" bgcolor="#D9D9D9"><strong>Limitrofes</strong></td>
                                    <td width="103" bgcolor="#D9D9D9"><strong>Resto de sudamérica</strong></td>
                                    <td width="111" bgcolor="#D9D9D9"><strong>Resto de America</strong></td>
                                    <td width="91" bgcolor="#D9D9D9"><strong>Europa</strong></td>
                                    <td width="123" bgcolor="#D9D9D9"><strong>Resto del mundo</strong></td>
                                </tr>
                                <tr>
                                    <td bgcolor="#F0F0F0">Hasta 1 kg</td>
                                    <td bgcolor="#F0F0F0">u$s 25</td>
                                    <td bgcolor="#F0F0F0">u$s 32</td>
                                    <td bgcolor="#F0F0F0">u$s 50</td>
                                    <td bgcolor="#F0F0F0">&euro; 30</td>
                                    <td bgcolor="#F0F0F0">u$s 25</td>
                                </tr>
                                <tr>
                                    <td bgcolor="#F0F0F0">Hasta 3 kg</td>
                                    <td bgcolor="#F0F0F0">u$s 50</td>
                                    <td bgcolor="#F0F0F0">u$s 60</td>
                                    <td bgcolor="#F0F0F0">u$s 75</td>
                                    <td bgcolor="#F0F0F0">&euro; 50</td>
                                    <td bgcolor="#F0F0F0">u$s 25</td>
                                </tr>
                                <tr>
                                    <td bgcolor="#F0F0F0">&nbsp;</td>
                                    <td bgcolor="#F0F0F0">hasta 7 días</td>
                                    <td bgcolor="#F0F0F0">hasta 10 días</td>
                                    <td bgcolor="#F0F0F0">hasta 10 días</td>
                                    <td bgcolor="#F0F0F0">hasta 12 días</td>
                                    <td bgcolor="#F0F0F0">hasta 15 días</td>
                                </tr>
                        </table></td>
                    </tr>
                </table>
                <p><br />
                    <br />
                </p>
            </div>
        </div>
    </body>
</html>