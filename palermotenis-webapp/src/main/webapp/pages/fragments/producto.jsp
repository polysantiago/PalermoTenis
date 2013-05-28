<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" charset="ISO-8859-1" >
	require(["app/producto"]);
</script>

<link href="/css/app/producto.styles.css" type="text/css" />
<!--[if lte IE 6]>
<style type="text/css">
    * html body > #imagenes{margin-left:15px;}
</style>
<![endif]-->
<!--[if lt IE 8]>
<style>
    #foto-producto-normal span {display: inline-block;height: 100%;}
</style>
<![endif]-->
<div id="producto-info">

    <div id="producto-ruta">
        <div id="producto-ruta-tipo">
            <s:property value="producto.tipoProducto.nombre + ' > '" />
        </div>
        <div id="producto-ruta-marca">
            <s:property value="producto.modelo.marca.nombre" />
        </div>
        <div id="producto-ruta-modelo">
            <s:set var="p" value="''"/>
            <s:iterator value="producto.modelo.padres" var="m">
                <s:set value="#p + '/' + #m.nombre" var="p" />
                <s:property value="#m.nombre + ' > '" />
            </s:iterator>
            <s:property value="producto.modelo.nombre" />
        </div>
    </div>
    <s:hidden id="pageview" value="%{producto.tipoProducto.nombre}/%{producto.modelo.marca.nombre}%{#p}/%{producto.modelo.nombre}" />
    <div id="producto-caract">
        <div id="tit">
            <a id="especif" class="link selected">Especificaciones técnicas</a> / <a id="desc"class="link">Descripción</a>
        </div>
        <div id="producto-desc" style="display: none;">
            <s:if test="producto.descripcion == null || producto.descripcion.empty">
                Producto sin descripción
            </s:if>
            <s:else>
                <s:property value="producto.descripcion" />
            </s:else>
        </div>
        <div id="producto-especif">
            <s:form id="producto">
                <s:hidden value="%{producto.id}" id="producto_id" />

                <!-- Atributos Simples -->
                <s:iterator value="producto.atributosSimples" var="a">
                    <s:div cssClass="caract">
                        <s:if test="#a.tipoAtributo.clase.name == 'java.lang.Boolean'">
                            <s:checkbox label="%{#a.tipoAtributo.nombre}" name="bool" disabled="true" labelposition="left" value="#a.valor.unidad" fieldValue="true"/>
                        </s:if>
                        <s:else>
                            <s:label label="%{#a.tipoAtributo.nombre}" value="%{#a.valor.nombre}" />
                        </s:else>
                    </s:div>
                </s:iterator>

                <!-- Atributos Tipados -->
                <s:iterator value="producto.atributosTipados" var="a">
                    <s:div cssClass="caract">
                        <s:label label="%{#a.tipoAtributo.nombre}" value="%{#a.valorPosible.nombre}" />
                    </s:div>
                </s:iterator>

                <!-- Atributos con múltiples valores -->
                <s:iterator value="multiplesMap.keys" var="t">
                    <s:iterator value="multiplesMap[#t]" var="valor">
                        <s:div cssClass="caract">
                            <s:label label="%{#t.nombre}" value="%{#valor}" />
                        </s:div>
                    </s:iterator>
                </s:iterator>

            </s:form>
        </div>
    </div>

    <!-- Productos cuyo precio no está determinado por una presentación -->
    <div id="precios">
        <s:if test="!producto.tipoProducto.presentable">
            <s:iterator value="producto.precios" var="p">
                <div class="producto-precio">
                    <div class="cotLoader" style="display: none" >
                        <img alt="Loading..." src="images/ajax-loader-cotizacion.gif" />
                    </div>
                    <s:if test="#p.valor == null || #p.valor == ''">
                        <div class="valor">
                            <span>Consultar</span>
                        </div>
                    </s:if>
                    <s:else>
                        <s:hidden cssClass="moneda" value="%{#p.id.moneda.id}" />
                        <div class="valor">
                            <s:if test="!#p.enOferta">
                                <span>
                                    <s:text name="format.currency">
                                        <s:param value="#p.valor" />
                                    </s:text>
                                </span>
                                <s:hidden value="%{#p.valor}" cssClass="valor-hidden" />
                            </s:if>
                            <s:else>
                                <span class="oldValor">
                                    <s:text name="format.currency">
                                        <s:param value="#p.valor" />
                                    </s:text>
                                </span>
                                <span class="oferta">
                                    <s:text name="format.currency">
                                        <s:param value="#p.valorOferta" />
                                    </s:text>
                                </span>
                                <s:hidden value="%{#p.valor}" cssClass="valor-hidden" />
                                <s:hidden value="%{#p.valorOferta}" cssClass="valor-hidden" />
                            </s:else>
                        </div>
                    </s:else>
                    <div class="condicion">
                        <s:if test="#p.id.pago.nombre == 'Efectivo'">En</s:if>
                        <s:else>Con</s:else>
                        <s:property value="#p.id.pago.nombre" />
                        <s:if test="#p.id.cuotas != 1">en <s:property value="#p.id.cuotas" /> cuotas</s:if>
                    </div>
                    <s:if test="#p.valor != null && #p.valor != ''">
                        <div class="link">
                            <a class="convertir">
                                Convertir moneda a<strong> <s:property value="#p.id.moneda.contrario.nombre" /> <s:property value="#p.id.moneda.contrario.simbolo" /></strong>
                            </a>
                        </div>
                    </s:if>
                </div>
            </s:iterator>
        </s:if>              
    </div>


    <s:form action="CarritoAction_add" namespace="/carrito" onsubmit="return validate();" theme="css_xhtml">
        <s:hidden name="productoId" value="%{producto.id}" />
        <div id="agregar">
            <!-- Presentación -->
            <s:if test="producto.tipoProducto.presentable">
                <s:if test="producto.presentaciones != null && !producto.presentaciones.empty">
                    <s:div cssClass="caract">
                        <s:select label="Presentación" list="producto.presentaciones" listKey="id" name="presentacionId"
                                  listValue="tipoPresentacion.nombre + ' - ' + nombre" cssClass="cotizable" theme="xhtml" />
                        <!-- Productos cuyo precio sí está determinado por una presentación -->
                        <script type="text/javascript">
	                    	require(["app/producto"], function() {
	                            $(function(){
	                                $(".contenedorCotizable").change(function(){setearPrecio($(this).next().next().get(0));});
	                                $(".cotizable").change(function(){setearPrecio(this);});
	                                $("#producto-info").addClass("loading");
	                                setearPrecio($(".cotizable").get(0));
	                            });
	                    	});
                        </script>
                    </s:div>
                </s:if>
                <s:else>
                    <span class="error">
                        Error: por favor, verifique que las presentaciones en stock de <s:property value="producto.modelo.nombre" /> tengan precio
                    </span>
                </s:else>
            </s:if>

            <!-- Atributos clasificatorios -->
            <s:if test="producto.clasificable">
                <s:if test="!producto.presentable">
                    <s:div cssClass="caract">
                        <s:iterator value="producto.tipoProducto.tiposAtributoClasificatorios" var="t">
                            <s:select cssClass="attrCl" label="%{#t.nombre}" list="producto.stocks.{?#this.stock > 0 && #this.valorClasificatorio.tipoAtributo == #t}.{valorClasificatorio}"
                                      headerKey="-1" headerValue="-- Seleccionar --" listKey="id" listValue="nombre" theme="xhtml" name="valorClasificatorioId" />
                        </s:iterator>
                    </s:div>
                </s:if>
                <s:elseif test="producto.presentaciones != null && !producto.presentaciones.empty">
                    <s:div cssClass="caract">
                        <s:iterator value="producto.tipoProducto.tiposAtributoClasificatorios" var="t">
                            <s:select cssClass="attrCl" label="%{#t.nombre}" list="{}" headerKey="-1" headerValue="-- Seleccionar --" theme="xhtml" name="valorClasificatorioId" />
                            <script type="text/javascript">
                            	require(["app/producto"], function() {
                            		$(function(){
                                        fillValoresClasificatorios();
                                        $("select[name='presentacionId']").bind('change',fillValoresClasificatorios);
                                    });                            		
                            	})
                            </script>
                        </s:iterator>
                    </s:div>
                </s:elseif>
            </s:if>
            <s:submit align="left" type="image" src="images/agregar.gif" id="imgAgregar" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('imgAgregar','','images/agregar-over.gif',1)" />            
            <a href="/Envios.jsp" target="_blank" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image5','','images/envio-over.gif',1)">
                <img src="images/envio.gif" name="Image5" width="108" height="18" border="0" id="Image5" alt="Envio"/>
            </a>
        </div>
    </s:form>
</div>

<!-- Imagenes -->
<s:if test="producto.modelo.imagenes != null && !producto.modelo.imagenes.empty">
    <div id="imagenes">
        <div id="foto-producto-normal" class="pics imagenDiv">
            <span></span>
            <s:a href="" onclick="javascript: $('#miniaturas div.miniatura input[value=%{producto.modelo.imagenes[0].hashKey}] + a').click();">
                <img border="0" src="/images/modelos/resizes/<s:property value="producto.modelo.imagenes[0].hashKey" />_P.jpg" alt="<s:property value="producto.modelo.nombre" />" />
            </s:a>
        </div>
        <div id="miniaturas">
            <s:iterator value="producto.modelo.imagenes" var="i" >
                <div class="miniatura imagenDiv">
                    <span></span>
                    <s:url action="GetImagen" namespace="/" escapeAmp="false" var="src">
                        <s:param name="hash" value="#i.hashKey" />
                        <s:param name="tipoImagenId" value="'M'" />
                    </s:url>
                    <s:hidden value="%{#i.hashKey}" name="hashKey" />
                    <s:a cssClass="lightbox" href="/images/modelos/resizes/%{#i.hashKey}_C.jpg">
                        <img border="0" src="<s:property value="#src" />" alt="" />
                    </s:a>
                </div>
            </s:iterator>
        </div>
    </div>
</s:if>
<!-- END Imagenes -->

<!-- Productos Relacionados -->
<s:action name="GetProductosRelacionados" namespace="/" ignoreContextParams="true" var="prodRel">
    <s:param name="productoId" value="producto.id" />
</s:action>

<s:if test="#prodRel.productos != null && !#prodRel.productos.empty" >
    <div id="wrapper">
        <div id="productos-relacionados">
            <div style="text-align: center;">
                <span style="font-size:11px;font-weight:bold;">Clientes que han comprado este producto también han comprado</span>
            </div>
            <s:iterator value="#prodRel.productos" var="p">
                <s:set var="m" value="#p.modelo" />
                <s:set value="'#marca:' + #m.marca.id + '_tipoProducto:' + #m.tipoProducto.id + '_modelo:' + #m.id" var="history"/>
                <s:if test="#p.tipoProducto.presentable">
                    <s:if test="#p.presentaciones != null && !#p.presentaciones.empty">
                        <s:set value="#p.preciosCantidad[0]" var="precio" />
                    </s:if>
                </s:if>
                <s:else>
                    <s:set value="#p.precios[0]" var="precio" />
                </s:else>
                <s:if test="#precio.valor == null || #precio.valor == ''">
                    <s:set value="0.00" var="precio" />
                </s:if>
                <s:elseif test="#precio.enOferta">
                    <s:set value="#precio.valorOferta" var="precio" />
                </s:elseif>
                <s:else>
                    <s:set value="#precio.valor" var="precio" />
                </s:else>
                <s:a href="%{#history}" rel="history">
                    <s:if test="#m.imagenes != null && !#m.imagenes.empty">
                        <s:set value="'/images/modelos/resizes/'+#m.imagenes[0].hashKey+'_T.jpg'" var="src" />
                    </s:if>
                    <s:else>
                        <s:set value="'images/sinimagen.jpg'" var="src" />
                    </s:else>                                        
                    <s:if test="#precio != 0.00">
                        <img class="cloudcarousel"
                             border="0"
                             src="<s:property value="#src" />"
                             alt="<s:text name="format.currency"><s:param value="#precio" /></s:text>"
                             title="<s:property value="#m.nombre" />" />
                    </s:if>
                    <s:else>
                        <img class="cloudcarousel"
                             border="0"
                             src="<s:property value="#src" />"
                             alt="Consultar precio"
                             title="<s:property value="#m.nombre" />" />
                    </s:else>
                </s:a>
            </s:iterator>

            <div id="left-but" class="flecha"></div>
            <div id="right-but" class="flecha"></div>

            <div id="texts">
                <div id="title-text"></div>
                <div id="alt-text"></div>
            </div>
        </div>
    </div>
</s:if>
