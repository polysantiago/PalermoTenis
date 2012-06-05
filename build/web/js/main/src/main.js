var xhr;

function principalCallback(){
    $("#menu-principal .loader").hide();
    $("#principal li.selectable").click( function(i){
        $(this).addClass("selected");
        var idMarca = $(this).attr("id");
        var idTipoProducto = $(this).parent().parent().attr("id");
        var hash = "marca:"+idMarca+"_tipoProducto:"+idTipoProducto;
        hash = hash.replace(/^.*#/, '');
        $.historyLoad(hash);
        return false;
    });
}

function createTreeView(idMarca, idTipoProducto){
    $("#menu-secundario").show("slow").find(".loader").show();
    $("#secundario").remove();
    $("#menu-secundario").append($("<ul id='secundario'></ul>").treeview({
        url: "ListarModelos_listActive",
        parameters: {
            idMarca: idMarca,
            idTipoProducto: idTipoProducto
        },
        treeType: "modelos",
        animated: "normal",
        unique: false,
        persist: "location",
        success: secundarioCallback
    }));
}

function secundarioCallback(){
    $("#menu-secundario .loader").hide();
    $("#secundario li.selectable").click(function(i){
        var idMarca = $("#principal li.active").attr("id");
        var idTipoProducto = $("#principal li.active").parents("li:first").attr("id");
        var hash = "marca:"+idMarca+"_tipoProducto:"+idTipoProducto+"_modelo:"+$(this).attr("id");
        $.historyLoad(hash);
        return false;
    });
}

function loadProducto(idModelo,idMarca,idTipoProducto){
    if($("#menu-secundario").is(":hidden"))
        createTreeView(idMarca, idTipoProducto);
    $("#contenidos .loader").show();

    if(xhr !== undefined) {
        xhr.abort();
    }
    xhr = $.ajax({
        url: "MostrarProducto",
        data: {
            id: idModelo
        },
        success: function(data){
            $("#contenidos .central").html(data);
            $("#contenidos .loader").hide();
            $(".producto-precio a").click(function(){
                cotizacion($(this).parent().parent());
            });
            activate(idTipoProducto,idMarca, idModelo);
            expand(idTipoProducto,idModelo);
        }
    });
}

function loadMiniaturas(idModelo){
    $("#contenidos .loader").show();
    $("#contenidos .central").empty();

    if(xhr !== undefined) {
        xhr.abort();
    }
    xhr = $.ajax({
        url: "MostrarMiniaturas_showByPadre",
        data: {
            modeloId: idModelo
        },
        success: function(data){
            $("#contenidos .central").html(data);
            $("#contenidos .loader").hide();
        }
    });
}

function loadMiniaturasByMarca(idTipoProducto,idMarca){
    $("#contenidos .loader").show();
    $("#contenidos .central").empty();

    if(xhr !== undefined) {
        xhr.abort();
    }
    xhr = $.ajax({
        url: "MostrarMiniaturas_showByMarca",
        data: {
            tipoProductoId: idTipoProducto,
            marcaId: idMarca
        },
        success: function(data){
            $("#contenidos .central").html(data);
            $("#contenidos .loader").hide();
        }
    });
}

function cotizacion(el){
    startTime = new Date();
    var loopTime=setInterval("currentTime()", 100);
    var monedas = {
        2:{
            symbol:'U$S',
            code:'USD',
            name: 'Dolares',
            contraryID:1,
            contrarySymbol:'$',
            contraryCode: 'ARS',
            contraryName: 'Pesos'
        }
        ,
        1:{
            symbol:'AR$',
            code:'ARS',
            name: 'Pesos',
            contraryID: 2,
            contrarySymbol:'U$D',
            contraryCode: 'USD',
            contraryName: 'Dolares'
        }
    };

    var moneda = el.find(".moneda");
    var valor = el.find(".valor-hidden");
    var valorField = el.find(".valor span");
    var convertir = el.find(".convertir strong");
    var from = monedas[trim(moneda.val())].code;
    var to = monedas[trim(moneda.val())].contraryCode;    

    var loader = el.find(".cotLoader").show();
    $.ajax({
        type: "GET",
        url: "GetCotizacion",
        data: "from="+from+"&to="+to,
        cache: false,
        success: function(response){
            if(isNaN(response)){
                alert("El servicio no se encuentra disponible en este momento.\nVuelva a intentar más tarde.");
                loader.hide();
            } else {
                _gaq.push(['_trackEvent', 'Moneda', 'Convertir', 'De ' + monedas[trim(moneda.val())].name + ' a ' + monedas[trim(moneda.val())].contraryName]);
                var newSymbol = monedas[trim(moneda.val())].contrarySymbol;

                $.each(valor, function(){
                    var valorDbl = parseFloat($(this).val());
                    valorDbl *= parseFloat(response);
                    $(this).val(valorDbl);                    
                });

                $.each(valorField, function(){                    
                    var valorDbl = parseFloat(trim($(this).text().replace(/[\\$|U\\$D]/gi,'')));
                    valorDbl *= parseFloat(response);
                    $(this).html(formatCurrency(valorDbl,newSymbol));
                });

                convertir.html(' ' + monedas[trim(moneda.val())].name + ' ' + monedas[trim(moneda.val())].symbol);
                moneda.val(monedas[trim(moneda.val())].contraryID);
                loader.hide();
            }
            clearTimeout(loopTime);            
        }
    });
}

function formatCurrency(num,code) {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
        num = "0";
    num = Math.floor(num*100+0.50000000001);
    var cents = num%100;
    num = Math.floor(num/100).toString();
    if(cents<10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        num = num.substring(0,num.length-(4*i+3))+','+
        num.substring(num.length-(4*i+3));
    return code + num + '.' + cents;
}

function ltrim(s){
    return s.replace(/^\s+/, "")
}
function rtrim(s){
    return s.replace(/\s+$/, "")
}
function trim(s) {
    return rtrim(ltrim(s))
}

function activate(idTipoProducto,idMarca,idModelo){
    $("#principal .active").removeClass("active");
    $("#principal").find("li#"+idTipoProducto).find("li#"+idMarca).addClass("active");
    if(idModelo){
        $("#secundario .active").removeClass("active");
        $("#secundario").find("li#"+idModelo).addClass("active");
    }
}

function expand(idTipoProducto,idModelo){
    var tp = $("#principal").find("li#"+idTipoProducto);
    if(tp.hasClass("expandable")) tp.find("div.hitarea").click();
    if(idModelo)expandNode($("#secundario").find("li#"+idModelo).parents("li:last").contents());  
}

function expandNode(nodes){
    nodes.each( function(){        
        if($(this).hasClass("expandable"))$(this).find("div.hitarea").click();
        else if($(this).parent().hasClass("expandable"))$(this).parent().find("div.hitarea").click();
        else if($(this).is("ul"))expandNode($(this).children());
    });
}

function pageload(hash){
    if(hash){
        var s = new String(hash);
        s = s.split("_");
        for(var i = 0; i < s.length; i++){
            var sb = s[i].split(":",2);
            if (sb[0] == "marca") var idMarca = sb[1];
            else if(sb[0] == "tipoProducto") var idTipoProducto = sb[1];
            else if(sb[0] == "modelo") var idModelo = sb[1];
        }

        if(isNaN(idMarca) || isNaN(idTipoProducto)){
            window.location.href = "/";
            return;
        }

        if(!idModelo){
            createTreeView(idMarca, idTipoProducto);            
            loadMiniaturasByMarca(idTipoProducto, idMarca);            
            activate(idTipoProducto,idMarca);
            expand(idTipoProducto);
        } else if(!isNaN(idModelo)) {
            $("#contenidos .central").empty();            
            loadProducto(idModelo, idMarca, idTipoProducto);            
        } else {
            window.location.href = "/";
            return;
        }
    } else {
        $("#menu-secundario").hide();
        $("#contenidos").addClass("loading");
        if(xhr !== undefined) {
            xhr.abort();
        }        
        xhr = $.ajax({
            url: "/MostrarOfertas",
            success: function(data){
                $("#contenidos .central").html(data);                
                $("#contenidos").removeClass("loading");
            }
        });
    }
}

$(document).ready(function(){
    $.historyInit(pageload, "index.jsp");
    $("#menu-principal .loader").show();
    $("#principal").treeview({
        url: "ListarTipoProductos",
        id: 0,
        treeType: "tiposProducto",
        animated: "normal",
        unique: true,
        persist: "location",
        success: principalCallback
    });
    var u = new String(window.location.href);
    if(u.indexOf("#")==-1){
        pageload("");
    }

    $("#buscar-flecha").click( function(){
        $("#news-cartel").hide();
        var email = $("#buscar-texto").val();
        if(!email || email=="" || email=="NEWSLETTER") return;
        if(!/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/.test(email)){
            $("#news-cartel").html("Por favor, asegurese que ha ingresado una dirección de correo válida.").show("slow");
            return;
        }
        $("#news-loader").show();
        $.getJSON("/newsletter/AgregarSuscriptor",{
            email: email
        }, function(data){
            $("#news-loader").hide();
            $("#news-cartel").html(data.msg).show("slow");
        });
    });

    $("#buscar-texto").keydown(function(event){
        if(event.keyCode == 13) $("#buscar-flecha").click();
    });
});
