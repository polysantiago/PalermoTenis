<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" charset="ISO-8859-1">
	require(["app/common"]);
</script>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/admin.pack.js"></script>
    <script type="text/javascript" charset="ISO-8859-1" src="/js/main/pack/admin.ofertas.pack.js"></script>
</sec:authorize>
<style type="text/css">
    div.modelo {
        float: left;
        border-bottom-color:#CCCCCC;
        border-bottom-style:solid;
        border-bottom-width:1px;
        padding: 5px;
        width: 162px;
        height:225px;
        margin-top: 10px;
        position: relative;
        display:inline;
    }     
    .modelo .valor {margin-top: 10px;}
    .modelo .valor strike {font-size: 11px;}
    .modelo .valor div span.oferta {color: #F86402;}
    .modelo .imagenDiv {width: 103px;height: 120px;}    
    .modelo .imagenDiv img {margin: auto;cursor: pointer;}
    .modelo a, a:active, a:visited, a span {color: #CA5100;outline:none;text-decoration: none;}    
    .nombre-tp {font-weight: bold;}
    .nombre-marca, .nombre-prod {font-weight: normal;}
    .nombre-tp, .nombre-marca {margin-top: 5px;text-decoration:none;text-transform:uppercase;display: inline;}
    .nombre-tp, .nombre-marca, .nombre-prod {color:#000000;font-family:'Segoe UI','Lucida Sans Unicode','Lucida Grande',sans-serif;font-size:11px;line-height:14px;text-decoration: none;}
    #contenidos {left: 200px;}
</style>
<!--[if lte IE 6]>
<style type="text/css">
    * html body > div.modelo{height:240px;}
</style>
<![endif]-->
<s:hidden id="pageview" value="home" />
<div id="modelos">
    <s:iterator value="productos" var="p">
        <div class="modelo">

            <s:set value="#p.modelo" var="modelo" />
            <s:set value="#p.tipoProducto" var="tipoProducto" />
            <s:set value="#p.modelo.imagenes" var="imagenes" />
            <s:set value="#modelo.marca" var="marca" />
            <s:set value="null" var="presentacion" />
            <s:if test="#p.precios != null && !#p.precios.empty">
                <s:set value="#p.precios" var="precios" />
            </s:if>
            <s:else>
                <s:set value="#p.preciosCantidad" var="precios" />
                <s:set value="#precios.{?#this.enOferta}[0].id.presentacion" var="presentacion" />
            </s:else>

            <s:hidden value="%{#p.id}" cssClass="productoId" />
            <s:hidden value="%{#precios.{?#this.enOferta}[0].orden}" cssClass="productoOrden" />
            <s:set value="'#marca:' + #marca.id + '_tipoProducto:' + #tipoProducto.id + '_modelo:' + #modelo.id" var="history"/>

            <s:a href="%{#history}" rel="history">
                <s:if test="#imagenes.empty">
                    <s:div cssClass="imagenDiv">
                        <img border="0" src="/images/sinimagen.jpg" alt="No Image" />
                    </s:div>
                </s:if>
                <s:else>
                    <s:div cssClass="imagenWrapper">
                        <s:div cssClass="imagenDiv">
                            <img alt="<s:property value="#modelo.nombre" />" border="0" src="/images/modelos/resizes/<s:property value="#imagenes[0].hashKey" />_T.jpg" />
                        </s:div>
                    </s:div>
                </s:else>
                <div class="pres">
                    <span class="nombre-tp"><s:property value="#tipoProducto.nombre" /></span>
                    <span class="nombre-marca"><s:property value="#marca.nombre" /></span>
                </div>
                <span class="nombre-prod">
                    <s:iterator value="#modelo.padres" var="m">
                        <s:property value="#m.nombre + ' '" />
                    </s:iterator>
                    <s:property value="#modelo.nombre" />
                </span>
                <div>
                    <span class="nombre-prod">
                        <s:if test="#presentacion != null">
                            <s:property value="#presentacion.tipoPresentacion.nombre + ' ' + #presentacion.nombre" />
                        </s:if>
                    </span>
                </div>
            </s:a>
            <s:iterator value="#precios" var="pr" >
                <s:if test="#pr.valor == null || #pr.valor == ''">
                    <div class="valor">
                        <span>Consultar</span>
                    </div>
                </s:if>
                <s:else>
                    <div class="valor">
                        <s:if test="#pr.enOferta">
                            <span>
                                <strike>
                                    <s:text name="format.currency">
                                        <s:param value="#pr.valor" />
                                    </s:text>
                                </strike>
                            </span>
                            <div>
                                <span class="oferta">
                                    <s:text name="format.currency">
                                        <s:param value="#pr.valorOferta" />
                                    </s:text>
                                </span>
                            </div>
                        </s:if>
                    </div>
                </s:else>
            </s:iterator>
        </div>
    </s:iterator>
</div>