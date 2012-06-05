function verStock(){
    postData = {tipoProductoId:$("#tipoProductoId option:selected").val(),
        marcaId:$("#marcaId option:selected").val(), modeloId:$("#modeloId option:selected").val()};
    $(".ui-jqgrid-title").html($("#tipoProductoId option:selected").text()
        + " > " + $("#marcaId option:selected").text()
        + " > " + $("#modeloId option:selected").text());
    $("#tblResultados").jqGrid('setGridParam',{postData: postData, datatype: 'json', page:1}).trigger("reloadGrid");
}

function secondOptionCallback(){
    $.getJSON("/ListarModelos_listAll",{idTipoProducto:$("#tipoProductoId option:selected").val()},
    function(response){
        $("#modeloId").empty().append($("<option>").html("-- Todos --").attr('value', ''));

        var arr = addProductos(response, []);
        arr.sort( function(a, b){
            return (a.text == b.text) ? 0 : (a.text < b.text) ? -1 : 1;
        });
        $.each(arr, function(){
            $("#modeloId").append($("<option>").html(this.text).attr('value', this.id));
        });

        $("#modeloId").val(gup('modeloId')).trigger("reload");
        if($("#modeloId").val()){
            postData = {modeloId:$("#modeloId option:selected").val()};
            $(".ui-jqgrid-title").html($("#tipoProductoId option:selected").text() + " > " + $("#modeloId option:selected").text());
            $("#marcaId").attr('disabled','disabled');
            $("#tblResultados").jqGrid('setGridParam',{postData: postData, datatype: 'json', page:1}).trigger("reloadGrid");
        }
    });
}

function addProductos(productos, arr){
    if(productos && productos.length > 0){
        $.each(productos, function() {
            if(this.leaf) arr.push({id: this.id, text: this.text});
            return addProductos(this.children, arr);
        });
    }
    return arr;
}

function gup(name) {
  name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
  var regexS = "[\\?&]"+name+"=([^&#]*)";
  var regex = new RegExp( regexS );
  var results = regex.exec( window.location.href );
  return results != null ? results[1] : "";
}

$(document).ready( function(){
    $("#tipoProductoId").val(gup('tipoProductoId'));
    $(".ui-button").button();
    $(".ui-select").sb({
        animDuration: 200,
        //ddCtx: function() { return $(this).closest("form"); },
        arrowMarkup: '<span class="ui-selectmenu-icon ui-icon ui-icon-triangle-1-s"></span>'
    });
    $("#modeloId").change( function(){
            !$(this).val() ? $("#marcaId").removeAttr("disabled").trigger("reload")
        : $("#marcaId").attr("disabled", "disabled").trigger("reload");
    });
    $("#tipoProductoId").change(secondOptionCallback);
    $("#tblResultados").jqGrid({
        url:'VerStock2',
        datatype: "local",
        colNames:["Tipo de Producto","Marca","Producto","Valor Clasificatorio","Presentación","Sucursal","Stock"],
        colModel:[
            {name:'tipoProducto',index:'s.producto.tipoProducto.nombre'},
            {name:'marca',index:'s.producto.modelo.marca.nombre'},
            {name:'producto',index:'s.producto.modelo.nombre, s.valorClasificatorio.valor, s.presentacion.cantidad, s.sucursal.valor'},
            {name:'valorClasificatorio',index:'s.valorClasificatorio.valor, s.sucursal.nombre, s.presentacion.cantidad'},
            {name:'presentacion',index:'s.presentacion.cantidad, s.valorClasificatorio.valor, s.sucursal.nombre'},
            {name:'sucursal',index:'s.sucursal.nombre'},
            {name:'stock',index:'stock', sorttype:"int", align:"center", width:80, editable: true}],
        rowNum:20, rowList:[10,20,30],
        pager: '#pager',
        sortable: true,
        pgtext : "Página {0} de {1}",
        sortname: 's.stock',
        viewrecords: true,
        sortorder: "desc",
        loadonce: false,
        autowidth: true,
        shrinkToFit: true,
        cellEdit: true,
        cellurl: "/admin/crud/StockAction_edit",
        beforeSubmitCell: function(rowid, celname, value, iRow, iCol) {
            return {stockId:rowid, cantidad:value}
        },
        afterSubmitCell: function(serverresponse, rowid, cellname, value, iRow, iCol) {
            return (serverresponse.responseText=="OK") ? [true, ""] : [false, serverresponse.responseText];
        },
        height: "100%",
        caption: $("#tipoProductoId option:selected").text() + " > " + $("#marcaId option:selected").text(),
        postData: {
            tipoProductoId:$("#tipoProductoId option:selected").val(),
            marcaId:$("#marcaId option:selected").val(),
            modeloId:$("#modeloId option:selected").val()
        },
        subGrid: true,
        subGridUrl: "VerPrecioStock",
        subGridModel: [{name: ["Pago", "Cuotas", "Moneda", "Precio", "En Oferta", "Oferta"], width : [130,50,50,80,60,80]}]
    }).jqGrid('navGrid','#pager',{del:false,add:false,edit:false});
    $(".ui-pg-selbox").selectmenu();
    secondOptionCallback();
});