function getVO(tr){
    var vo = {};

    $(".globalElement").each( function(){
        vo[$(this).attr("id")] = $(this).val();
    });

    if(tr.attr("name") && tr.attr("val")){
        vo[tr.attr("name")] = tr.attr("val");
    } else if(tr.attr("name") && !tr.attr("val")){
        vo[tr.attr("name")] = -1;
    }

    tr.find("td").each( function(){
        
        if($(this).attr("val") && $(this).attr("name")){
            vo[$(this).attr("name")] = $(this).attr("val");
        }

        $(this).find("input").each( function(){
            if($(this).attr("type") == "checkbox") {
                vo[$(this).attr("name")] = this.checked;
            } else {
                vo[$(this).attr("name")] = $(this).val();
            }
        });

        $(this).find("select").each( function(){
            vo[$(this).attr("name")] = $(this).val();
        });
    });   

    return vo;
}

function editar(event){    
    $("#loader").show();
    var tr = $(event.target).closest("tr");
    var vo = getVO(tr);
    $(event.target).removeClass("ui-state-focus ui-state-hover");
    if(!fieldsAreValid(vo)) return false;
    if(typeof beforeUpdate == 'function')beforeUpdate(tr,vo);
    $.ajax({
        type: "POST",
        url: event.data.url,
        data: vo,
        cache: false,
        success: function(response){
            $("#loader").hide();
            if(response != 'OK') {
                error("Ha ocurrido un error: \n" + response);
            } else {
                tr.animate({
                    backgroundColor: "#66FF66"
                },500).animate({
                    backgroundColor: "white"
                },500);
                if(typeof afterUpdate == 'function')afterUpdate(tr,vo);
            }
        }
    });
    return true;
}

function borrar(event){    
    $(event.target).removeClass("ui-state-focus ui-state-hover");
    confirmar({
        text: "\u00BFEst\u00E1 seguro que quiere borrar este elemento?",
        confirm: function(){
            $("#loader").show();
            var tr = $(event.target).closest("tr");
            var vo = getVO(tr);
            $.ajax({
                type: "POST",
                url: event.data.url,
                data: vo,
                cache: false,
                success: function(response){
                    $("#loader").hide();
                    if(response != 'OK') {
                        error("Ha ocurrido un error: \n" + response);
                    } else {
                        tr.remove();
                        if(typeof afterDelete == 'function')afterDelete(tr,vo);
                    }
                }
            });
            $(this).dialog('close');
        },
        cancel: function(){
            $(this).dialog('close');
        }
    });
}

function agregar(elements){
    var tr = $("<tr>");
    
    $.each(elements, function(){
        var el = this.element;
        if(this.element.is("select")){
            $("<option>").html("-- Seleccionar --").val(-1).appendTo(el);
            if(this.data && this.data.length > 0){
                $.each(this.data, function(){                    
                    $("<option>").html(this.value).val(this.id).appendTo(el);
                });
            }
        }
        if(this.attr && this.attr.length > 0){
            $.each(this.attr, function(){
                el.attr(this.key, this.value);
            });
        }
        tr.append($("<td>").append(this.element));
    });
    
    var agrBtn = $("<button>").html("Agregar");
    var cnlBtn = $("<button>").html("Cancelar").bind("click",cancelar);

    agrBtn.bind("click",{
        url: createURL
    },agregarNuevo);    

    $("table tbody").append(tr.append($("<td>").append(agrBtn)).append($("<td>").append(cnlBtn)));

    agrBtn.button();
    cnlBtn.button();
    applyFormatting();
    applySelectMenuFormating(tr.find("select"));
    if(typeof resize == 'function')resize();
}

function cancelar(event){
    $(event.target).closest("tr").remove();
    if(typeof resize == 'function')resize();
}

function agregarNuevo(event){
    $("#loader").show();
    $(event.target).removeClass("ui-state-focus ui-state-hover");
    var tr = $(event.target).closest("tr");
    var vo = getVO(tr);
    if(!fieldsAreValid(vo)) return false;

    $.ajax({
        type: "POST",
        url: event.data.url,
        data: vo,
        cache: false,
        success: function(response){
            $("#loader").hide();
            if(response != 'OK'){
                error("Ha ocurrido un error: \n" + response);
            } else{
                window.location.href = window.location.href;
            }
        }
    });
    return true;
}

function fetchList(url,data){
    var list = [];
    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        data: data,
        success: function(data) {
            list=data
        },
        async: false
    });
    return list;
}

function applySelectMenuFormating(el){
    el.selectmenu({
        style:'dropdown'
    });
}

function applyFormatting(){    
    $("table.list input[type='text']").addClass("ui-widget ui-state-default ui-corner-all");
    $("table.list input[type='checkbox']").checkbox({
        empty:'/css/images/empty.png',
        cls:'jquery-safari-checkbox'
    });
}

function createLoader(){
    $("#loader").remove();
    $("#main").prepend(
        $("<div>").attr("id","loader").css("display", "none").css("float","right").append(
            $("<img>").attr("src","/images/ajax-loader.gif")));
}

$(document).ready( function(){

    $(".btnEditar").bind("click",{
        url: editURL
    },editar).button();
    
    $(".btnBorrar").bind("click",{
        url: destroyURL
    },borrar).button();
    
    $("#functions button").button();
    applyFormatting();
    applySelectMenuFormating($("table.list select"));

    createLoader();
});