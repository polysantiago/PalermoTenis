<%-- 
    Document   : EnviarPedidoOk
    Created on : 24/09/2009, 18:02:27
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <s:include value="/WEB-INF/jspf/main_head.jspf" />
        <script type="text/javascript">
            $(document).ready(function(){
                regex = /([A-Z\.\u00D1\s]+)\-([\w\s\.\u00E1\u00E9\u00ED\u00F3\u00FA\u00F1\u00E7]+)(\s?\([0-9]+\))?(,)(\s[A-Z]+)/g;
                ciudad = '<s:property value="pedido.cliente.direccion.ciudad" />';
                provincia = '';
                pais = '';
                if(ciudad && (arrMatch = regex.exec(ciudad))){                    
                    ciudad = arrMatch[1];
                    provincia = arrMatch[2];
                    pais = arrMatch[5];
                }                
                _gaq.push(['_addTrans',
                    '<s:property value="pedido.id" />',
                    '', //Sucursal
                    '<s:property value="pedido.total" />',
                    '', //Impuesto
                    '', //Costo de Envío
                    $.trim(ciudad),
                    $.trim(provincia), //Provincia
                    $.trim(pais) //País
                ]);
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
                    _gaq.push(['_addItem',
                        '<s:property value="pedido.id" />',
                        '<s:property value="#pp.id.stock.id" />',
                        $.trim('<s:property value="#nombre" />'.replace(/ /g,"_")),
                        $.trim('<s:property value="#categoria" />'.replace(/ /g,"_")),
                        '<s:property value="#pp.subtotal / #pp.cantidad" />',
                        '<s:property value="#pp.cantidad" />'
                    ]);
                </s:iterator>
                _gaq.push(['_trackTrans']);
            });
        </script>
        <style type="text/css">
            #okMsg {
                width: 725px;
                float: left;
                padding-left: 15px;
                padding-top: 15px;
                display: block;
                position: absolute;
                top: 175px;
                font-size: 11px;
            }
            #okMsg a,#okMsg a:visited,#okMsg a:active {text-decoration: none;color: #F86402;}
            #okMsg a:hover {text-decoration: underline;}
            #okMsg span.msgGracias{font-size: 18px;font-weight: bold;}
            #okMsg span.texto-activo{color: #000000;}
            #okMsg .naranja{color: #E45B00;}
            #okMsg .naranja a{color: #F86402;}
        </style>
    </head>
    <body>
        <div id="todo">
            <s:include value="/WEB-INF/jspf/header.jspf" />
            <div id="okMsg">
                <span class="msgGracias">¡Gracias por su compra!</span><br />
                <span class="texto-activo">Hemos enviado un email con los detalles de su pedido.</span><br />
                <span class="texto-activo">Si no encuentra nuestro correo, verifique la bandeja de correo no deseado o spam.</span><br />
                <img src="/images/carrito_titulo2.jpg" width="104" height="125" alt="Carrito"/><br />
                <strong>Lo esperamos en <a href="/Ubicacion.jsp">nuestro local</a> para retirar los productos.</strong><br />
                <span class="naranja">Si desea puede enviarnos su <a href="/clientes_web/Consulta">consulta</a> o enviarnos un correo a <a href="mailto:consultas@palermotenis.com.ar">consultas@palermotenis.com.ar</a>.</span>
            </div>
        </div>
    </body>
</html>
