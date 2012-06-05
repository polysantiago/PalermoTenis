<%-- 
    Document   : ValorPosible
    Created on : 27/08/2009, 21:53:33
    Author     : Poly
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/valorPosible/valor.posible.pack.js"></script>
<style type="text/css">
    #valoresPosibles fieldset {width: 300px;}
    #valoresPosibles fieldset table td.unidad span {cursor: default;}
    #valoresPosibles #vpFunctions {float:right;cursor:pointer;}
    #valoresPosibles #vpFunctions div {float: left;}
    #valoresPosibles #vpFunctions div.addBtn {float:left; padding-top:3px;}
    #valoresPosibles #vpFunctions div.addBtn:hover {background-position: 0px 3px; background-repeat: no-repeat; }
</style>
<fieldset>
    <legend>Valores Posibles para <s:property value="#parameters['tipoAtributoNombre']" /> en <s:property value="#parameters['tipoProductoNombre']" /></legend>
    <table cellspacing="4">
        <s:iterator value="valoresPosibles" var="v">
            <tr class="valorPosible" val="<s:property value="#v.id" />">
                <td><s:checkbox cssClass="delete" name="borrar" theme="css_xhtml"/></td>
                <td class="unidad">
                    <span title="Click derecho para editar"><s:property value="#v.unidad" /></span>
                    <s:textfield size="20" value="%{#v.unidad}" cssStyle="display: none;"  theme="css_xhtml"/>
                </td>
            </tr>
        </s:iterator>
    </table>
    <div id="vpFunctions">
        <div class="addBtn" title="Agregar"></div>
        <div>
            <img id="btnBorrarValoresPosibles" src="/admin/images/trash_can.gif" alt="Borrar" title="Borrar" />
        </div>
    </div>
</fieldset>

