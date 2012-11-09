<%--
    Document   : PresentacionesList
    Created on : 23/08/2009, 20:24:32
    Author     : Poly
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/tipoPresentacion/tipo.presentacion.pack.js"></script>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/global/CRUDFunctions.pack.js"></script>
<table class="list" align="center">
    <thead>
        <tr>
            <th colspan="3">Tipos de Presentación</th>
        </tr>
    </thead>
    <tbody>
        <tr class="yellow">
            <td>Nombre</td>
        </tr>
        <s:iterator value="tiposPresentacion" var="t">
            <tr val="<s:property value="#t.id" />" name="tipoPresentacionId">
                <td class="nombre">
                    <s:textfield name="nombre" value="%{#t.nombre}" theme="css_xhtml" />
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
    <button id="btnAgregarNuevo">Agregar Tipo de Presentación</button>
</div>