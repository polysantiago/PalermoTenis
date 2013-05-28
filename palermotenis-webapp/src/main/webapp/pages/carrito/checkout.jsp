<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="carrito">
    <table width="710" border="0" cellpadding="2" cellspacing="0" id="producto-carrito">
        <thead>
            <tr>
                <th>Cant.</th>
                <th>Producto</th>
                <th>Precio unitario</th>
                <th>Subtotal</th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="#session['scopedTarget.carrito'].contenido" var="m">
                <s:set var="i" value="#m.value" />
                <s:set var="s" value="#m.key" />
                <tr val="stock-<s:property value="#s.id" />">
                    <td class="fndGris" width="43" align="center">
                        <s:property value="%{#i.cantidad}" />
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
                </tr>
            </s:iterator>
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
    <s:a action="CarritoAction_show" namespace="/carrito" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','/images/boton-modificar-over.jpg',1)">
        <img src="/images/boton-modificar.jpg" name="Image5" width="127" height="19" border="0" id="Image5" alt="Seguir" />
    </s:a>
    <div id="enviarPedido">
        <div id="titulo"><strong>Completa este formulario con tus datos para realizar el pedido</strong></div>
        <s:form cssClass="form-carrito" action="EnviarPedido" namespace="/carrito/checkout">
            <s:action name="PagoAction_show" ignoreContextParams="true" namespace="/admin/crud" var="p" />
            <s:select label="Pago Con" list="#p.pagos" listKey="id" listValue="nombre" value="#session['scopedTarget.carrito'].pago.id" name="pagoId" id="pagoId"  />
            <div class="loader" style="display:none;">
                <img alt="Cargando..." src="/images/indicator.gif">
            </div>
            <s:select label="Cuotas" list="{1,3,6}" value="carrito.cuotas" name="cuotas" id="cuotas" disabled="true" />
            <s:submit id="btnEnviar" align="left" type="image" src="/images/boton-realizar.jpg"  onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('btnEnviar','','/images/boton-realizar-over.jpg',1)" />
        </s:form>
    </div>
</div>