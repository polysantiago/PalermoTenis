function getResponse(response, postdata){
    return (response.responseText=="OK") ? [true, "", postdata.id] : [false, response.responseText, postdata.id];
}

var prmEdit = {
    width: 500,
    closeOnEscape: true,
    closeAfterEdit: true,
    afterSubmit: getResponse
};
var prmAdd = {
    width: 500,
    closeOnEscape: true,
    closeAfterAdd: true,
    afterSubmit: getResponse
};
var prmDel = {
    width: 500,
    afterSubmit: getResponse
};
$(document).ready( function(){
    $("#tblResultados").jqGrid({
        url: "SucursalAction_showJson",
        datatype: "json",
        colNames:["Nombre","Dirección","Teléfono"],
        colModel:[
        {
            name: "nombre",
            index: "nombre",
            editable:true
        },

        {
            name: "direccion",
            index: "direccion",
            editable:true
        },

        {
            name: "telefono",
            index: "telefono",
            editable:true
        }
        ],
        pgtext : "Página {0} de {1}",
        pager: "#pager",
        autowidth: true,
        height: "auto",
        caption: "Sucursales",
        editurl: "SucursalAction_crud"
    }).jqGrid('navGrid',"#pager",
    {
        add: true,
        view: false,
        del: true,
        edit: true,
        search: false
    },
    prmEdit,
    prmAdd, /*add options*/
    prmDel, /*delete options*/
    {
        multipleSearch:false
    },

    {
        closeOnEscape:true
    }
    );
});