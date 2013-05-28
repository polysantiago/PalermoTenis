function setearPrecio(el){
    $(".cotLoader").show();
    $("#precios").load("GetPrecioValorPosible",{
        presentacionId: $(el).val(),
        productoId: $("#producto_id").val()
    }, function(){
        $("#producto-info").removeClass("loading");        
        $(".producto-precio a").click(function(){
            cotizacion($(this).parent().parent());
        });
    });
}

function validate(){
    var valid = true;
    $("#CarritoAction_add select.attrCl").each( function(){
        if($(this).find("option:selected").val()== "-1"){
            $(this).after($("<span>").html("Por favor, seleccione un valor").addClass("error"));
            valid = false;
        }
    });
    if(valid){
        try {            
            _gaq.push(['_trackEvent', 'Pedidos', 'Agregar', $.trim($("#pageview").val().replace(/ /g,"_"))]);
        } catch(err) {}
    }
    return valid;
}

function fillValoresClasificatorios(){
    var vcSel = $("#CarritoAction_add select[name='valorClasificatorioId']").attr("disabled","true");
    var datos = {
        presentacionId : $("#CarritoAction_add select[name='presentacionId']").val(),
        productoId : $("#producto_id").val()
    };
    $.getJSON("/GetValoresClasificatoriosByPresentacion",datos,function(response){
        vcSel.empty();
        $("<option>").html("-- Seleccionar --").val(-1).appendTo(vcSel);
        $.each(response,function(){
            $("<option>").html(this.nombre).val(this.id).appendTo(vcSel);
        });
        vcSel.removeAttr("disabled");
    });
}

define(["jquery", "jquery.carousel", "jquery.lightbox", "app/common"], function($) {
	
	$.fn.exists = function() {
		return this.length > 0;
	};
	
	$(function(){       
	    $("#especif").click( function(){
	        $("#producto-desc").hide();
	        $("#producto-especif").show();
	        $("a.selected").removeClass("selected");
	        $(this).addClass("selected");
	    });
	    
	    $("#desc").click( function(){
	        $("#producto-especif").hide();
	        $("#producto-desc").show();
	        $("a.selected").removeClass("selected");
	        $(this).addClass("selected");
	    });

	    $("a.lightbox").lightBox({
	        imageLoading: '/images/ajax-loader-contenidos.gif',
	        imageBtnPrev: '/css/images/lightbox-btn-prev.gif',
	        imageBtnNext: '/css/images/lightbox-btn-next.gif',
	        imageBtnClose: '/css/images/lightbox-btn-close.gif',
	        imageBlank: '/css/images/lightbox-blank.gif',
	        txtImage: 'Imagen',
	        txtOf: 'de'
	    });
	    
	    if($("#miniaturas").exists()) {
	    	$("#miniaturas div.miniatura").mouseenter(function() {
	    		var hash = $(this).find("input[name='hashKey']").val();
	    		if($("#foto-producto-normal img").attr("src").toString().indexOf(hash) == -1){
	    			$("#miniaturas div.miniatura a").removeClass("hover");
	    			$(this).find("a").addClass("hover");
	    			$("#foto-producto-normal img").hide().attr("src", "/images/modelos/resizes/"+hash+"_P.jpg").load( function(){
	    				$(this).fadeIn().closest("div").removeClass("loading");
	    			}).closest("div").addClass("loading");
	    			$("#foto-producto-normal a").unbind("click").removeAttr("onclick").click( function() {
	    				$('#miniaturas input[value='+hash+'] + a').click();
	    			});
	    		}
	    	});
	    	$("#miniaturas div.miniatura a:first").addClass("hover");
	    }

	    if($("#productos-relacionados").exists()){
	    	$("#productos-relacionados").CloudCarousel({
	    		xPos: 228,
	    		yPos: 56,
	    		buttonLeft: $("#left-but"),
	    		buttonRight: $("#right-but"),
	    		altBox: $("#alt-text"),
	    		titleBox: $("#title-text")
	    	});
	    }
	});
});