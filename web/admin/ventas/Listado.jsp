<%@ taglib prefix="s" uri="/struts-tags" %>
<table>
    <thead>
        <tr>
            <th>Cantidad</th>
            <th>Producto</th>
            <th>Precio Unitario</th>
            <th>Subtotal</th>
        </tr>
    </thead>
    <s:iterator value="listado.stocksListado" var="s">
        <s:set value="#s.stockListadoPK.stock" var="stock" />
        <s:set value="#stock.producto" var="producto" />
        <s:set value="#stock.presentacion" var="presentacion" />
        <s:set value="#stock.valorClasificatorio" var="valorClasificatorio" />
        <tr>
            <td style="text-align:center;">
                <s:if test="#parameters['edit'][0] == 'false'">
                    <span ><s:property value="#s.cantidad" /></span>
                </s:if>
                <s:else>
                    <s:textfield name='cantidades[%{#stock.id}]' value="%{#s.cantidad}" size="2" theme="css_xhtml" cssStyle="text-align: center;" />
                </s:else>
            </td>
            <td class="fndGris" width="355">
                <strong>
                    <s:property value="#producto.tipoProducto.nombre" />
                    <s:property value="#producto.modelo.marca.nombre" />
                </strong>
                <s:iterator value="#producto.modelo.padres" var="m">
                    <s:property value="#m.nombre + ' '" />
                </s:iterator>
                <s:property value="#producto.modelo.nombre" />
                <s:if test="#presentacion != null">
                    <s:property value="' - ' + #presentacion.tipoPresentacion.nombre + ' ' + #presentacion.nombre" />
                </s:if>
                <s:if test="#valorClasificatorio != null">
                    <s:property value="' - ' + #valorClasificatorio.tipoAtributo.nombre + ': ' + #valorClasificatorio.nombre" />
                </s:if>
            </td>
            <td style="text-align:center;">
                <s:if test="#parameters['edit'][0] == 'false'">
                    <span>
                        <s:text name="format.currency">
                            <s:param value="#s.precioUnitario" />
                        </s:text>
                    </span>
                </s:if>
                <s:else>
                    <s:textfield name='precios[%{#stock.id}]' value="%{#s.precioUnitario}" size="4" theme="css_xhtml" cssStyle="text-align: center;" />
                </s:else>
            </td>
            <td style="text-align:center;">
                <span>
                    <s:text name="format.currency">
                        <s:param value="#s.subtotal" />
                    </s:text>
                </span>
            </td>
        </tr>
    </s:iterator>
    <tr>
        <td></td>
        <td></td>
        <td>
            <strong>Total:</strong>
        </td>
        <td>
            <s:text name="format.currency">
                <s:param value="listado.total" />
            </s:text>
        </td>
    </tr>
</table>