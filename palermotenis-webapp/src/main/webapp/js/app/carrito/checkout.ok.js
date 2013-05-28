define(["jquery", "google-analytics"], function($){
	$(function(){
        regex = /([A-Z\.\u00D1\s]+)\-([\w\s\.\u00E1\u00E9\u00ED\u00F3\u00FA\u00F1\u00E7]+)(\s?\([0-9]+\))?(,)(\s[A-Z]+)/g;
        ciudad = pedido.ciudad;
        provincia = '';
        pais = '';
        if(ciudad && (arrMatch = regex.exec(ciudad))){                    
            ciudad = arrMatch[1];
            provincia = arrMatch[2];
            pais = arrMatch[5];
        }
        _gaq.push(['_addTrans',
            pedido.id,
            '', //Sucursal
            pedido.ciudad,
            '', //Impuesto
            '', //Costo de Envío
            $.trim(ciudad),
            $.trim(provincia), //Provincia
            $.trim(pais) //País
        ]);
        for (var producto in pedido.productos) {
	    	_gaq.push(['_addItem',
	                   pedido.id,
	                   producto.id,
	                   $.trim(producto.nombre.replace(/ /g,"_")),
	                   $.trim(producto.categoria.replace(/ /g,"_")),
	                   producto.precio,
	                   producto.cantidad
	               ]);
		}
        _gaq.push(['_trackTrans']);
    });
});