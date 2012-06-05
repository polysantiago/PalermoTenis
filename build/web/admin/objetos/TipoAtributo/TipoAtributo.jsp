<%-- 
    Document   : TipoAtributo
    Created on : 27/08/2009, 19:54:22
    Author     : Poly
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/admin/js/pack/tipoAtributo/tipo.atributo.pack.js"></script>
<style type="text/css">
    #attrFieldset {width: 600px;}
    #attrFieldset tr.tipoAtributo:hover {background-color: #FFFF99;}
    #attrFieldset tr.tipoAtributo td a.tipado {cursor:pointer;}
    #attrFieldset tr.tipoAtributo td a.focus {outline:none;}
    #attrFieldset tr.tipoAtributo span.selected {font-weight: bolder;}
    #wrapper{float: left;}
</style>
<div id="wrapper">
    <fieldset id="attrFieldset">
        <legend>Tipos de Atributo</legend>
        <table align="center" width="100%" id="tipoAtributoTable">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Formato</th>
                    <th>Unidad</th>
                    <th class="help"><span>Tipo</span></th>
                </tr>
            </thead>
            <tbody>
                <s:iterator value="tiposAtributo" var="t">
                    <tr class="tipoAtributo" val="<s:property value="#t.id" />">
                        <td>
                            <s:if test="#t.tipo != 'S'">
                                <a class="tipado" title="Click izquierdo para ver los valores posibles<br />Click derecho para editar" onclick="getValoresPosibles(<s:property value="#t.id" />);return false;">
                                    <span class="nombre"><s:property value="#t.nombre" /></span>
                                </a>
                            </s:if>
                            <s:else>
                                <span class="nombre"><s:property value="#t.nombre" /></span>
                            </s:else>
                            <s:textfield size="20" value="%{#t.nombre}" cssStyle="display: none;"  theme="css_xhtml"/>
                        </td>
                        <td val="<s:property value="#t.clase.name" />">
                            <s:select cssClass="clases" list="clasesMap" value="#t.clase.name" theme="css_xhtml" />
                        </td>
                        <s:action name="UnidadAction_show" ignoreContextParams="true" namespace="/admin/crud" var="u" />
                        <td val="<s:property value="#t.unidad.id" />">
                            <s:select cssClass="unidad" list="#u.unidades"  listKey="id" listValue="descripcion" value="#t.unidad.id" emptyOption="true" theme="css_xhtml" />
                        </td>
                        <td val="<s:property value="#t.tipo" />">
                            <s:select cssClass="clasif" list="clasificaciones" listKey="clasif" listValue="nombre" value="#t.tipo" theme="css_xhtml" />
                        </td>
                        <td>
                            <a href="#" onclick="borrarTipoAtributo(<s:property value="#t.id" />);">
                                <img border="0" src="/admin/images/delete_small.gif" alt="Borrar" title="Borrar" />
                            </a>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
    </fieldset>
    <div id="valoresPosibles" style="display: none;"></div>
</div>

