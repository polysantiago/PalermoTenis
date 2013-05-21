<%@ taglib prefix="s" uri="/struts-tags" %>
<s:iterator value="precios" var="p">
    <s:div cssClass="producto-precio" id="atributo-precio-%{#p.id.presentacion.id}">
        <div class="cotLoader" style="display: none" >
            <img alt="Loading..." src="images/ajax-loader-cotizacion.gif" />
        </div>
        <s:if test="#p.valor == null || #p.valor == ''">
            <div class="valor"><span>Consultar</span></div>
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
    </s:div>
</s:iterator>