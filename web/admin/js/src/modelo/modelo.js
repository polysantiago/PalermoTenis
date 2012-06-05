var options = {
    emptyOption: true,
    emptyValue: '-- Seleccionar --',
    emptyKey: 'nothing'
};

function enable(el){
    el.removeAttr("disabled");
}

function disable(el){
    el.attr("disabled","true");
}

function secondOptionCallback(){
    $("#loader").hide();
    $("select#marca").selectmenu('destroy').selectmenu({
        style:'dropdown'
    });
    $("#modelos").remove();
    $("#divNuevo").hide();
    if($("#marca option:selected").val()!= '-1'){
        $("#columnaDerecha").find(".loader").show();
        $("#columnaDerecha").append($("<ul id='modelos' class='filetree treeview-famfamfam'></ul>").treeview({
            url: "/ListarModelos_listAll",
            parameters: {
                idMarca: $("#marca option:selected").val(),
                idTipoProducto: $("#tipoProducto option:selected").val()
            },
            animated: "normal",
            root: true,
            unique: false,
            persist: "location",
            success: function(){                
                $("#divNuevo .ui-button").button('destroy').button();
                $("#columnaDerecha").find(".loader").hide();
                $("#divNuevo").show();
                $("#divNuevo .ui-button").blur();

                $("li.sortable span").inlineEdit({
                    saveButton: 'Guardar',
                    cancelButton: 'Cancelar',
                    save: function(event, ui) {
                        $("#loader").show();
                        $.ajax({
                            type: "POST",
                            url: "/admin/crud/ModeloAction_edit",
                            data: {
                                modeloId:$(event.target).closest("li").attr("id"),
                                nombre:ui.value
                            },
                            success: function(response){
                                $("#loader").hide();
                                if(response != "OK") error(response);
                            }
                        });
                    }
                }).addClass("ui-button-text");
                
                $("#modelos li").click( function(e){
                    $("#divNuevo .ui-button").blur();
                    e.stopPropagation();
                    $("#modelos li.ui-state-active").removeClass("ui-state-active");
                    $(this).addClass("ui-state-active");

                    !$(this).hasClass("producible") ? enable($("#btnNuevoModelo")) : disable($("#btnNuevoModelo"));

                    (!$(this).hasClass("producible") 
                        && $(this).attr("id") != -1
                        && $(this).hasClass("ui-button")) ?
                    enable($("#btnNuevoProducto")) : disable($("#btnNuevoProducto"));

                    $(this).attr("id") != -1 ? enable($("#btnEliminarModelo")) : disable($("#btnEliminarModelo"));

                    $("#divNuevo .ui-button").button('destroy').button();
                });

                $("#modelos li.producible").dblclick( function(i){
                    $("#content").remove();
                    $("#central .loader").show();
                    $("#modelos li.ui-state-active").removeClass("ui-state-active");
                    $(this).addClass("ui-state-active");
                    loadModeloEditable($(this).attr("id"));
                    $("#modelo").val($(this).attr("id"));                    
                });

                $("#modelos span").each( function(){
                    if (typeof this.onselectstart!="undefined") //IE route
                        this.onselectstart = function(){
                            return false;
                        };
                    else if (typeof this.style.MozUserSelect!="undefined") //Firefox route
                        this.style.MozUserSelect = "none";
                    else //All other route (ie: Opera)
                        this.onmousedown = function(){
                            return false;
                        };
                });
            },
            toggle: function(){
                var container = parseInt($("#columnaIzquierda").height()) + parseInt($("#columnaDerecha").height());
                var central = parseInt($("#cental").height());
                if(central < container)
                    $("#central").height(container+23);
            }
        }));
    }
}



function loadModeloEditable(modeloId){
    var marcaId = $("#marca option:selected").val();
    var tipoProductoId = $("#tipoProducto option:selected").val();

    $("#central").append("<iframe id='content' style='display: none;'>");
    $("#content").load( function(){
        $("#central .loader").hide();
        $(this).show();
        var heightForm = parseInt($(this.contentWindow.document.body).find("#frmEditarProducto table").height());        
        var heightNav = parseInt($(this.contentWindow.document.body).find("#navFunciones").height());
        var heightImg = parseInt($(this.contentWindow.document.body).find("#imagenes").height());
        var height = (isNaN(heightImg)) ? heightForm : (heightForm > heightImg) ? heightForm : heightImg;        
        $("#central").height(height + heightNav + 20);
        $("#content").attr("height",(height + heightNav + 20) + 'px');
        $(this.contentWindow.document.body).find("ul.ui-selectmenu-disabled").hide();
    });
    $("#content").attr("src","/admin/crud/ProductoAction_show?modeloId="+modeloId+"&marcaId="+marcaId+"&tipoProductoId="+tipoProductoId).hide();
}

function borrarModelo(idModelo){
    $("#loader").show();
    $.ajax({
        type: "GET",
        url: "/admin/crud/ModeloAction_destroy",
        data: "modeloId="+idModelo,
        cache: false,
        success: function(response){
            $("#loader").hide();
            if(response=="OK"){
                $("#"+idModelo).remove();
                $("#divNuevo .ui-button").blur();
                if($("#frmEditarProducto_modeloId").val() == idModelo) $("#content").empty();
            } else {
                error(response);
            }
        }
    });
}

function createLoader(){
    $("div.ctr").prepend(
        $("<div>").attr("id","loader").css("display","none").css("float","right").append(
            $("<img>").attr("src","/images/ajax-loader.gif")));
}

$(document).ready(function(){
    createLoader();
    $(".ui-button").button();
    $("#modelo").val("");
    $("#btnNuevoProducto,#btnEliminarModelo").attr("disabled","true");
    $("#columnaIzquierda select").selectmenu();
    $('.log').ajaxError(function() {
        $(this).text('Ha ocurrido un error!');
    });
    $("#dialog-error").dialog({
        autoOpen: false,
        buttons: {
            OK: function(){
                $(this).dialog('close');
            }
        }
    });

    $("#btnNuevoModelo").click(function(){
        $(this).removeClass("ui-state-focus ui-state-hover");
        crearFormulario({
            title: "Crear nuevo modelo",
            elements: [{
                id: "nombre",
                label: "Nombre:",
                field: $("<input>").attr("type","text").attr("id","nombre").attr("name","nombre")
            }],
            aceptarFunction: function(){
                var nombreEl = $(this).find("#nombre");
                nombreEl.removeClass("ui-state-error");
                
                if(checkLength($(this).find("#nombre"),"nombre",1,128)){
                    var idPadre = $("#modelos li.ui-state-active").attr("id");
                    var idMarca = $("#marca option:selected").val();
                    var idTipoProducto = $("#tipoProducto option:selected").val();
                    var nombre = nombreEl.val();

                    $("#nombre").val("").removeClass("ui-state-error");
                    if(!idPadre) idPadre = -1;

                    $.ajax({
                        type: "GET",
                        url: "/admin/crud/ModeloAction_create",
                        data: "padreId="+idPadre+"&marcaId="+idMarca+"&tipoProductoId="+idTipoProducto+"&nombre="+nombre,
                        cache: false,
                        success: secondOptionCallback
                    });
                    $(this).dialog("close");
                    $("#loader").show();
                }
            },
            cancelarFunction: function(){
                $(this).dialog("close").find("#nombre").val("").removeClass("ui-state-error");
            }
        });
    });

    $("#btnNuevoProducto").click( function(){
        var marcaId = $("#marca option:selected").val();
        var tipoProductoId = $("#tipoProducto option:selected").val();
        var modeloId = $("#modelos li.ui-state-active").attr("id");

        if(!modeloId){
            error("No hay ningún modelo seleccionado!");
            return false;
        }

        $("#content").load( function(){
            $("#central .loader").hide();
            $(this).show();
            var heightForm = parseInt($(this.contentWindow.document.body).find("#frmAgregarProducto").height());
            $("#central").height(heightForm);
            $("#content").attr("height",heightForm + 'px');
            $(this).removeClass("hidden");
        });
        $("#content").attr("src","/admin/crud/ProductoAction_prepare?modeloId="+modeloId+"&marcaId="+marcaId+"&tipoProductoId="+tipoProductoId).hide();
    });

    $("#dialog-confirm").dialog({
        autoOpen: false,
        modal: true
    });

    $("#btnEliminarModelo").click( function(i){
        $(this).removeClass("ui-state-focus ui-state-hover");
        var idModelo = $("#modelos li.ui-state-active").attr("id");
        if(!idModelo){
            error("No hay ningún modelo seleccionado!");
            return;
        } else {
            confirmar({
                text: "Se borrar\u00E1n sus descendientes y los productos asociados. \u00BFEst\u00E1 seguro?",
                confirm: function(){
                    $(this).dialog('close');
                    borrarModelo(idModelo);
                },
                cancel: function(){
                    $(this).dialog('close');
                }
            });
        }
    });

    $("#loader").show();
    $.getJSON("/ListarTipoProductoMarcas",{
        id:0
    },function(response){
        $("#tipoProducto").doubleSelect('marca', response, options);
        $("#marca").change(secondOptionCallback);
        $("#tipoProducto").change(secondOptionCallback);
        $("select#tipoProducto, select#marca").selectmenu('destroy').selectmenu({
            style:'dropdown'
        });
        $("#loader").hide();
    });
});