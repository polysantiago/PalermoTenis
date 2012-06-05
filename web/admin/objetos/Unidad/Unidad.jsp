<%--
    Document   : ValorPosible
    Created on : 27/08/2009, 21:53:33
    Author     : Poly
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/unidad/unidad.pack.js"></script>
<style type="text/css">
    #uFieldset {width: 300px;}
    #uFieldset #uFunctions {float:right;cursor:pointer;}
    #uFieldset #uFunctions div {float:left;}
    #uFieldset #uFunctions div.addBtn {padding-top:3px;}
    #uFieldset #uFunctions div.addBtn:hover {background-position: 0px 3px; background-repeat: no-repeat; }
</style>
<fieldset id="uFieldset">
    <legend>Unidades</legend>
    <table cellspacing="7">
        <thead>
            <tr>
                <th></th>
                <th>Descripción</th>
                <th>Nombre</th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="unidades" var="u">
                <tr class="unidad" val="<s:property value="#u.id" />">
                    <td><s:checkbox cssClass="delete" name="borrar" theme="css_xhtml"/></td>
                    <td class="descripcion">
                        <span title="Click derecho para editar"><s:property value="#u.descripcion" /></span>
                        <s:textfield size="10" value="%{#u.descripcion}" cssStyle="display: none;"  theme="css_xhtml"/>
                    </td>
                    <td class="nombre">
                        <span title="Click derecho para editar"><s:property value="#u.nombre" /></span>
                        <s:textfield size="5" value="%{#u.nombre}" cssStyle="display: none;"  theme="css_xhtml"/>
                    </td>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <div id="uFunctions">
        <div class="addBtn" title="Agregar"></div>
        <div>
            <img id="btnBorrarUnidades" src="/admin/images/trash_can.gif" alt="Borrar" title="Borrar" />
        </div>
    </div>
</fieldset>