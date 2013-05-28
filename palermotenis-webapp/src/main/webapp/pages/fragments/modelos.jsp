<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1">
	require(["app/common"]);
</script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<script type="text/javascript" charset="ISO-8859-1">
		require(["app/admin.modelos"]);
	</script>
</sec:authorize>
<link href="/css/app/modelos.styles.css" type="text/css" rel="stylesheet"/>
<s:hidden id="pageview" value="%{modelos[0].tipoProducto.nombre}/%{modelos[0].marca.nombre}" />
<div id="modelos">
    <s:iterator value="modelos" var="m">
        <div class="modelo">
            <s:set value="#m.producto" var="p" />
            <s:set value="'#marca:' + #m.marca.id + '_tipoProducto:' + #m.tipoProducto.id + '_modelo:' + #m.id" var="history"/>
            <s:hidden value="%{#m.id}" cssClass="modeloId" />
            <s:hidden value="%{#m.orden}" cssClass="modeloOrden" />
            <div class="content">
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