$(document).ready( function(){
    resize();
});

function resize(){
    
    window.resizeTo($("#main").width()+100, calculateHeight());
    
    if($("#main").width() < $("table.list").width()){
        $("#main").width(parseFloat($("table.list").width())*1.1);
        window.resizeTo($("#main").width() * 1.15,calculateHeight());
    }
}

function calculateHeight(){
    return ($("#main").height() > 1024) ? 1000 : $("#main").height() + 160;
}


