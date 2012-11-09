<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/common.pack.js"></script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/admin.pack.js"></script>
    <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/admin.modelos.pack.js"></script>
</sec:authorize>
<style type="text/css">
    #contenidos {width: auto;}
    .modelo {float: left;border-bottom: 1px solid #F8F6F6;width: 152px;margin: 10px 10px;height:300px;position:relative;}
    .content {width: 152px;}
    .imagenDiv {height: 120px; cursor:pointer;}
    .descWrapper {display: block;}
    .nombre,.descCorta {
        color:#000000;
        font-family:'Segoe UI','Lucida Sans Unicode','Lucida Grande',sans-serif;
        font-size:11px;
        line-height:14px;
        font-weight: normal;
    }
    .nombre {
        margin-top: 5px;
        text-decoration:none;
        text-transform:uppercase;
        font-weight: bold;
    }
    a,a:active,a:visited { text-decoration: none;}
    .content .comp {position: absolute; top: 0; right: 0;}
    .precio {margin: 10px 0; font-weight: bold;width: 152px; text-align: center;}
    .precio strike {font-size: 11px;}
    .precio div span.oferta {color: #F86402;}
    .precio span.consultar {font-size: 13px;}
</style>
<s:hidden id="pageview" value="%{modelos[0].tipoProducto.nombre}/%{modelos[0].marca.nombre}" />
<div id="modelos">
    <s:iterator value="modelos" var="m">
        <div class="modelo">
            <s:set value="#m.producto" var="p" />
            <s:set value="'#marca:' + #m.marca.id + '_tipoProducto:' + #m.tipoProducto.id + '_modelo:' + #m.id" var="history"/>
            <s:hidden value="%{#m.id}" cssClass="modeloId" />
            <s:hidden value="%{#m.orden}" cssClass="modeloOrden" />
            <div class="content">
                <!--<s:checkbox cssClass="comp" name="comp" theme="css_xhtml" />-->
                <s:a href="%{#history}" rel="history">
                    <s:if test="#m.imagenes.empty">
                        <div class="imagenDiv"><img border="0" src="images/sinimagen.jpg" /></div>
                        </s:if>
                        <s:else>
                        <div class="imagenDiv">
                            <img border="0" src="/images/modelos/resizes/<s:property value="#m.imagenes[0].hashKey" />_T.jpg" />
                        </div>
                    </s:else>
                    <span class="nombre"><s:property value="#m.nombre" /></span>
                </s:a>

                <!-- Precio -->
                <s:if test="#p.tipoProducto.presentable">
                    <s:if test="#p.presentaciones != null && !#p.presentaciones.empty">
                        <s:set value="#p.preciosCantidad[0]" var="precio" />
                    </s:if>
                </s:if>
                <s:else>
                    <s:set value="#p.precios[0]" var="precio" />
                </s:else>
                <div class="precio">

                    <s:if test="#precio.valor == null || #precio.valor == ''">
                        <span class="consultar">Consultar precio</span>
                    </s:if>
                    <s:elseif test="#precio.enOferta">
                        <span>
                            <strike>
                                <s:text name="format.currency">
                                    <s:param value="#precio.valor" />
                                </s:text>
                            </strike>
                        </span>
                        <div>
                            <span class="oferta">
                                <s:text name="format.currency">
                                    <s:param value="#precio.valorOferta" />
                                </s:text>
                            </span>
                        </div>
                    </s:elseif>
                    <s:else>
                        <span>
                            <s:text name="format.currency">
                                <s:param value="#precio.valor" />
                            </s:text>
                        </span>
                    </s:else>
                </div>

                <div class="descWrapper">
                    <span class="descCorta"><s:property value="#m.producto.descripcionCorta" /></span>
                </div>

            </div>
        </div>
    </s:iterator>
</div>