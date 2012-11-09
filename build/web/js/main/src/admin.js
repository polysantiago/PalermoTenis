function enableReorder(url) {
    $("a[rel='history']").unbind('click');
    $('#modelos').sortable({
        revert: true,
        intersect: 'pointer',        
        update: function(event, ui){
            $.ajax({
                type:"GET",
                url:"/CheckRol_check",
                data:"role=ROLE_ADMIN",
                async:false,
                success: function(response){
                    if(response == "NOT_AUTHENTICATED"){
                        alert("Su sesión ha expirado");
                        window.location = "/clientes_web/login.jsp";
                    } else if(response == "NOT_IN_ROLE"){
                        alert("Usted no está autorizado a realizar este tipo de cambios");
                    } else if(response == "OK") {
                        var data = getData(ui);
                        for(var o in data) if(!data[o]) delete data[o];
                        $.ajax({
                            type: "GET",
                            url: url,
                            data: data,
                            success: function(response){
                                if(response != 'OK') {
                                    alert("Ha ocurrido un error: \n" + response);
                                } else {
                                    $(ui.item).animate({
                                        backgroundColor:"#66FF66"
                                    },500).animate({
                                        backgroundColor:"white"
                                    },500);
                                }
                            }
                        });
                    } else {
                        alert(response);
                    }
                }
            });
        }
    });
}

$(document).ready( function(){
    $("#panel input.chkbox").checkbox({
        empty:'/css/images/empty.png',
        cls:'jquery-safari-checkbox'
    }).bind("uncheck",function(){
        $('#modelos').sortable("destroy");
        $("a[rel='history']").unbind('click').bind('click',trackHistory);
    });
});

