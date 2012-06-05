<%-- 
    Document   : PresentacionesList
    Created on : 23/08/2009, 20:24:32
    Author     : Poly
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/presentacion/presentacion.pack.js"></script>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/CRUDFunctions.pack.js" ></script>
<style type="text/css">#functions {width: auto;text-align:center;}</style>
<table class="list" align="center">
    <thead>
        <tr>
            <th colspan="5">Presentaciones</th>
        </tr>
    </thead>
    <tbody>
        <tr class="yellow">
            <td>Cantidad</td>
            <td>Unidad</td>
            <td>Nombre</td>
        </tr>
        <s:iterator value="presentaciones" var="p">
            <tr val="<s:property value="#p.id" />" name="presentacionId">
                <td class="cantidad">
                    <s:textfield name="cantidad" value="%{#p.cantidad}" theme="css_xhtml" size="4"/>
                </td>
                <td class="unidad">
                    <s:textfield name="unidad" value="%{#p.unidad}" theme="css_xhtml" />
                </td>
                <td class="nombre">
                    <s:textfield name="nombre" value="%{#p.nombre}" theme="css_xhtml" />
                </td>
                <td>
                    <button class="btnEditar">Editar</button>
                </td>
                <td>
                    <button class="btnBorrar">Borrar</button>
                </td>
            </tr>
        </s:iterator>
    </tbody>
</table>
<div id="functions">
    <button id="btnAgregarNuevo">Agregar Presentacion</button>
</div>
