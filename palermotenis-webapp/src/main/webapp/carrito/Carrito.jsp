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

            #carrito {
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 166px;
            }

            #producto-carrito{
                border-collapse:separate;
                border-spacing:2px;
                empty-cells:show;
                font-size:11px;
                line-height:12px;
                margin-top:15px;
                padding:2px;
                text-indent:0;
            }

            #producto-carrito tr {border: 1px solid #CCCCCC;}
            #producto-carrito thead th {background-color: #cccccc;padding: 4px;text-align: center;font-weight: normal;}
            #producto-carrito tbody td {padding: 2px;}
            #producto-carrito tbody td.fndGris {background-color: #E5E5E5;}
            #producto-carrito .error {background-color: #FF0000;}
            #producto-carrito .precio{color: #FFFFFF;}
            #producto-carrito .eliminar{color: #FFFFFF;background-color: #FF6600;border-color: #000000;text-align: center;}
            #producto-carrito .eliminar a {color: #ffffff;text-decoration: none;}
            #producto-carrito .eliminar a:hover {color: #ea8633;}

            #producto-total{margin-top: 5px;font-size: 11px;border-width: 1px;border-color: #CCCCCC;border-style: solid;}

            .form-carrito{
                width: 710px;
                font-size: 11px;
                border-width: 1px;
                border-color: #CCCCCC;
                border-style: solid;
                background-color: #e5e5e5;
            }
            .form-carrito td {border-spacing: 0px;padding: 5px;}

            .titulo-carrito{font-size: 11px;}

            .precio {
                background-color: #FF6600;
                color:#FFFFFF;
                font-weight:normal;
                text-align: center;
            }

            #enviarPedido {
                margin-top: 35px;
                width: 712px;
            }

            #enviarPedido #titulo {
                background-color: #cccccc;
                font-size: 11px;
                border:1px solid #CCCCCC;
            }

            #carritoFunctions {margin-top: 10px;}

            a.detalles {color:#CC0000;font-size:11px;text-decoration:none;}
            a.detalles:active, a.detalles:visited {color: #CC0000;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="carrito">
                <img alt="Carrito de Compras" src="/images/carrito_titulo.jpg" />
                <table width="710" border="0" cellpadding="2" cellspacing="0" id="producto-carrito">
                    <thead>
                        <tr>
                            <th>Cant.</th>
                            <th>Producto</th>
                            <th>Precio unitario</th>
                            <th>Subtotal</th>
                            <th>Forma de pago</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:form action="CarritoAction_updateCantidades" theme="css_xhtml" id="carritoForm" >
                            <s:iterator value="#session['scopedTarget.carrito'].contenido" var="m">
                                <s:set var="i" value="#m.value" />
                                <s:set var="s" value="#m.key" />
                                <tr val="stock-<s:property value="#s.id" />">
                                    <td class="fndGris" width="43" align="center">
                                        <s:textfield name='cantidad[%{#s.id}]' value="%{#i.cantidad}" size="2" theme="css_xhtml" cssClass="cantidad" cssStyle="text-align: center;" />
                                    </td>
                                    <td class="fndGris" width="355">
                                        <strong>
                                            <s:property value="#s.producto.tipoProducto.nombre" />
                                            <s:property value="#s.producto.modelo.marca.nombre" />
                                        </strong>
                                        <s:iterator value="#s.producto.modelo.padres" var="m">
                                            <s:property value="#m.nombre + ' '" />
                                        </s:iterator>
                                        <s:property value="#s.producto.modelo.nombre" />
                                        <s:if test="#s.presentacion != null">
                                            <s:property value="' - ' + #s.presentacion.tipoPresentacion.nombre + ' ' + #s.presentacion.nombre" />
                                        </s:if>
                                        <s:if test="#s.valorClasificatorio != null">
                                            <s:property value="' - ' + #s.valorClasificatorio.tipoAtributo.nombre + ': ' + #s.valorClasificatorio.nombre" />
                                        </s:if>
                                        <s:url action="index" namespace="/" anchor="marca:%{#s.producto.modelo.marca.id}_tipoProducto:%{#s.producto.tipoProducto.id}_modelo:%{#s.producto.modelo.id}"  var="detalle"/>
                                        <s:a cssClass="detalles" href="%{#detalle}">[Detalles]</s:a>
                                    </td>
                                    <td width="80" class="precio unitario" val="<s:property value="#i.precioUnitario" />">
                                        <span>
                                            <s:text name="format.currency">
                                                <s:param value="#i.precioUnitario" />
                                            </s:text>
                                        </span>
                                    </td>
                                    <td width="80" class="precio subtotal" val="<s:property value="#i.subtotal" />">
                                        <span>
                                            <s:text name="format.currency">
                                                <s:param value="#i.subtotal" />
                                            </s:text>
                                        </span>
                                    </td>
                                    <td class="precio pago">
                                        <span>
                                            <s:property value="#session['scopedTarget.carrito'].pago.nombre" />
                                        </span>
                                    </td>
                                    <td class="eliminar">
                                        <s:url action="CarritoAction_remove" var="deleteUrl">
                                            <s:param name="stockId" value="%{#s.id}" />
                                        </s:url>
                                        <s:a href="%{#deleteUrl}">
                                            <img border="0" alt="Eliminar" src="/images/boton-eliminar.png" />
                                        </s:a>
                                    </td>
                                </tr>
                            </s:iterator>
                        </s:form>
                    </tbody>
                </table>
                <table width="710" border="0" cellpadding="2" cellspacing="0" id="producto-total">
                    <tr>
                        <td align="right" style="padding-right: 3px;" bgcolor="#FFFFCC">
                            <strong>TOTAL</strong>
                        </td>
                        <td style="background-color: #FFFF00; color: #000000;" width="303" class="precio">
                            <strong>
                                <s:text name="format.currency">
                                    <s:param value="#session['scopedTarget.carrito'].total" />
                                </s:text>
                            </strong>
                        </td>
                    </tr>
                </table>

                <div id="carritoFunctions">
                    <s:a href="javascript: validate();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image3','','/images/boton-actualizarCant-over.jpg',1)">
                        <img src="/images/boton-actualizarCant.jpg" name="Image3" width="154" height="19" border="0" id="Image3" alt="Actualizar" />
                    </s:a>
                    <s:a action="CarritoAction_empty" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image4','','/images/boton-vaciar-over.jpg',1)">
                        <img src="/images/boton-vaciar.jpg" name="Image4" width="109" height="19" border="0" id="Image4" alt="Vaciar" />
                    </s:a>
                    <s:a action="index" namespace="/" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','/images/boton-seguir-over.jpg',1)">
                        <img src="/images/boton-seguir.jpg" name="Image5" width="127" height="19" border="0" id="Image5" alt="Seguir" />
                    </s:a>
                    <s:if test="#session['scopedTarget.carrito'].cantidadItems > 0">
                        <s:a action="Checkout" namespace="/carrito/checkout" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image6','','/images/boton-realizar-over.jpg',1)">
                            <img src="/images/boton-realizar.jpg" name="Image6" width="113" height="19" border="0" id="Image6" alt="Realizar compra" />
                        </s:a>
                    </s:if>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            try {
                var pageTracker = _gat._getTracker("UA-13165598-1");
                pageTracker._trackPageview();
            } catch(err) {}
        </script>
    </body>
</html>
