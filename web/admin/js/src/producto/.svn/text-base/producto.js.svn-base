function openPopUpLargImg(imageHashKey){
    var link = '/images/modelos/resizes/'+imageHashKey+"_C.jpg";
    openNewWindow(link,'550','500');
}

function addImagePopUp(modeloId){
    var link = '/admin/objetos/Imagen/Imagen.jsp?modeloId='+modeloId;
    openNewWindow(link,'700','120');
}

function editStockPopUp(productoId,clasificable){
    var link = '/admin/crud/StockAction_show?productoId='+productoId+'&clasificable='+clasificable;
    if(clasificable) openNewWindow(link,'420','440');
    else openNewWindow(link,'300','150');
}

function editPrecios(productoId,presentable){
    var link = "";
    var width = "";
    if(!presentable){
        link = "/PrepararPrecios?productoId="+productoId+"&redirectPage=/admin/objetos/Precio/PrecioUnidad.jsp";
        width = "720";
    } else {
        link = "/PrepararPrecios?productoId="+productoId+"&redirectPage=/admin/objetos/Precio/PrecioPresentacion.jsp";
        width = "1024";
    }
    openNewWindow(link,width,'300');
}

function editCostos(productoId){
    var link = '/admin/crud/CostoAction_show?productoId='+productoId;
    openNewWindow(link,'700','120');
}

function openNewWindow(link,width,height){
    var new_window = window.open(link,'','width='+width+',height='+height+',scrollbars=1');
    if (new_window.opener == null) {
        new_window.opener = self;
    }
    new_window.focus();
}

function deleteImage(imagenId){
    if(confirm("¿Está seguro que quiere eliminar esta imagen?")){
        $.ajax({
            type: "POST",
            url: "/admin/crud/ImagenAction_destroy",
            data: "imagenId="+imagenId,
            success: function(response){
                if(response == "OK"){
                    $("#img"+imagenId).remove();
                } else {
                    alert("Ha ocurrido un error:\n"+response);
                }
            }
        });
    }
}

function resize(){
    $(".rsz").css("display","none");
    $("#frmEditarProducto_descripcion").effect("size",{
        to: {
            width: 200,
            height: 100
        },
        scale: 'box'
    }, 1000);
    $(".rsz").show().attr({
        src:"/admin/images/oie_unresize.gif",
        title: "Contraer"
    });
}

function unresize(){
    $(".rsz").css("display","none");
    $("#frmEditarProducto_descripcion").effect("size",{
        to: {
            width: 148,
            height: 50
        },
        scale: 'box'
    }, 1000);
    $(".rsz").show().attr({
        src:"/admin/images/oie_resize.gif",
        title: "Expandir"
    });
}

function applyFormatting(){
    $("#frmEditarProducto br").remove();
    applyUIFormat($("#frmEditarProducto input:text"));
    applyUIFormat($("#frmEditarProducto textarea"));
    $("#wrapper select.coolSelectMenu").selectmenu({
        width: 150
    });    
    $("#wrapper input.chkActivo").checkbox({
        empty:'/css/images/empty.png'
    });
    $("#wrapper input.chkBinario").checkbox({
        empty:'/css/images/empty.png',
        cls:'jquery-safari-checkbox'
    });
    $(".ui-button").button();
}

function applyUIFormat(element){
    element
    .mouseenter( function(){
        $(this).addClass("ui-state-hover");
    })
    .focus( function(){
        $(this).addClass("ui-state-focus");
    })
    .mouseleave( function(){
        $(this).removeClass("ui-state-hover ui-state-focus");
    })
    .mouseout( function(){
        $(this).removeClass("ui-state-hover ui-state-focus");
    })
    .blur( function(){
        $(this).removeClass("ui-state-hover ui-state-focus");
    }).addClass("ui-state-default");
}

$(document).ready( function(){
    applyFormatting();

    $("#frmEditarProducto").submit( function(form){
        form.preventDefault();
        $("pre.err").remove();
        var camposJson = "[";
        $(".value").each( function(){
            if($(this).val()) {
                var s = new String($(this).attr("name"));
                s = s.substring(s.indexOf("[")+1, (s.length - (s.length - s.indexOf("]"))));
                var tipoAtributoId = s;
                var valor = $(this).val();
                camposJson += "{tipoAtributoId: " + tipoAtributoId + ",valor: \"" + encodeURI(valor) + "\"},";
            }
        });
        camposJson = camposJson.replace(/,$/,"") + "]";
        var data = {camposJson: camposJson, productoId: $("#productoId").val(), activo: $("#chkActivo").is(":checked")};
        $.getJSON("/admin/crud/ProductoAction_validateFields",
            data,
            function(data){
                var hasErrors = false;
                $.each(data.camposJson, function(){
                    if(!this.valid){
                        $("input[name='atributosSimples["+this.id+"]']").parent().append($("<pre>").addClass("err").html("Este campo contiene un error"));
                        hasErrors = true;
                    }
                });
                if($("input[name='nombre']").val()==""){
                    $("input[name='nombre']").parent().append($("<pre>").addClass("err").html("Este campo contiene un error"));
                    hasErrors = true;
                }
                if(!data.activo.valid){
                    $("#chkActivo").parent().append($("<pre>").addClass("err").html("No puede activar un producto sin haberle puesto un precio"));
                    hasErrors = true;
                }
                if(!hasErrors){
                    var prt = $(parent.document).find("span.selected");
                    var chd = $("input[name='nombre']");
                    if(prt.html()!=chd.val()){
                        prt.html(chd.val());
                    }
                    $("#frmEditarProducto").unbind("submit").submit();
                }
            }
        );
    });

    $("#frmEditarProducto_descripcion").parent().append(
        $("<img>").addClass("rsz").attr(
        {
            src: "/admin/images/oie_resize.gif",
            title: "Expandir"
        }).toggle(resize, unresize));
});