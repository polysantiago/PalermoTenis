requirejs.config({
    baseUrl: "/js/lib",
    paths: {
      "app" : "../app",
      "clientes" : "../app/clientes_web",
      "carrito" : "../app/carrito",
      "flash" : "AC_RunActiveContent",
      "google-analytics" : "//www.google-analytics.com/ga", 
      "jquery" : "//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min"
    },
    shim: {
    	"jquery.history" : ["jquery"],
    	"jquery.treeview" : ["jquery"],
    	"jquery.treeview.async": ["jquery", "jquery.treeview"],
    	"jquery.autocomplete": ["jquery"],
    	"jquery.swfobject" : ["jquery"],
    	"jquery.carousel" : ["jquery"],
    	"jquery.lightbox" : ["jquery"]
    }
});

require(["jquery", "jquery.swfobject", "google-analytics"], function($) {
	var _gaq = _gaq || [];
	if (window) {
		window._gaq = _gaq; // export as global
	}
	_gaq.push(['_setAccount', "UA-13165598-1"]);
	
	$(function(){
		// Load flash
		$("#barra-sup1-izq").flash({
			swf: '/flash/titulo-izq.swf',
			width: 450,
			height: 123,
			quality: "high"
		});
		$("#barra-sup1-der").flash({
			swf: '/flash/promos.swf',
			width: 510,
			height: 122,
			quality: "high"
		});
	});
});