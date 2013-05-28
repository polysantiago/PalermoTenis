<%@ taglib prefix="s" uri="/struts-tags" %>
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