<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
var pedido = {
	id: '<s:property value="pedido.id" />',
	total: '<s:property value="pedido.total" />',
	ciudad: '<s:property value="pedido.cliente.direccion.ciudad" />',
	productos: [
		<s:iterator value="pedido.pedidosProductos" var="pp">
			<s:set var="p" value="#pp.id.stock.producto" />
			<s:set var="pr" value="#pp.id.stock.presentacion" />
			<s:set var="vc" value="#pp.id.stock.valorClasificatorio" />
			
			<s:set var="categoria" value="#p.tipoProducto.nombre + '/' + #p.modelo.marca.nombre" />
			<s:iterator value="#p.modelo.padres" var="m">
			    <s:set var="categoria" value="#categoria + '/' + #m.nombre" />
			</s:iterator>
			<s:set var="nombre" value="#p.modelo.nombre" />
			<s:if test="#pr != null">
			    <s:set var="nombre" value="#nombre + '/' + #pr.tipoPresentacion.nombre + ':' + #pr.nombre" />
			</s:if>
			<s:if test="#vc != null">
			    <s:set var="nombre" value="#nombre + '/' + #vc.tipoAtributo.nombre + ':' + #vc.nombre" />
			</s:if>
			{
				id: '<s:property value="#pp.id.stock.id" />',
				nombre: '<s:property value="#nombre" />',
				categoria: '<s:property value="#categoria" />',
				precio: '<s:property value="#pp.subtotal / #pp.cantidad" />',
				cantidad: '<s:property value="#pp.subtotal / #pp.cantidad" />'
			},
       </s:iterator>
        ]
};
</script>
<div id="okMsg">
    <span class="msgGracias">¡Gracias por su compra!</span><br />
    <span class="texto-activo">Hemos enviado un email con los detalles de su pedido.</span><br />
    <span class="texto-activo">Si no encuentra nuestro correo, verifique la bandeja de correo no deseado o spam.</span><br />
    <img src="/images/carrito_titulo2.jpg" width="104" height="125" alt="Carrito"/><br />
    <strong>Lo esperamos en <a href="/Ubicacion">nuestro local</a> para retirar los productos.</strong><br />
    <span class="naranja">Si desea puede enviarnos su <a href="/clientes_web/Consulta">consulta</a> o enviarnos un correo a <a href="mailto:consultas@palermotenis.com.ar">consultas@palermotenis.com.ar</a>.</span>
</div>