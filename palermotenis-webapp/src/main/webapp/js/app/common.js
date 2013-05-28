function trackHistory(event){
    var hash = $(event.target).closest("a").attr("href");
    hash = hash.replace(/^.*#/, '');
    $.historyLoad(hash);
    return false;
}

define(["jquery"], function($) {
	$(window).ready( function(){    
	    $("div.imagenDiv img").each( function(){
	        var div = $(this).closest("div");
	        div.addClass("loading");
	        if($(this)[0].complete){
	            div.removeClass("loading");        
	        } else {
	            $(this).load( function(){
	                div.removeClass("loading");            
	            });
	        }
	    });        
	});
});

define(["jquery"], function($) {
	$(function(){
	    _gaq.push(['_trackPageview', "/"+$.trim($("#pageview").val().replace(/ /g,"_"))]);
	    $("a[rel='history']").unbind('click').bind('click',trackHistory);
	});
});