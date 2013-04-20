$(function(){
                
    });
$(window).load(function() {
    $('#slider').nivoSlider({
        controlNav: false, 
        directionNav: false, 
        animSpeed: 1500
    });
    $('#featured-products').roundabout({
        autoplay: true, 
        autoplayDuration: 3000, 
        autoplayPauseOnHover: true
    });
    $("div.product-holder").hover(function () {
        $('.info', this).css({
            visibility: "visible", 
            display: "none"
        }).slideDown('normal');
    }, function () {
        $('.info', this).css({
            visibility: "hidden"
        });
    });
                
    $("li.side_cart").hover(function () {
        $('#cart', this).fadeIn(500);
        $('#cart').addClass('active');
        $('#cart').bind('mouseleave', function() {
            $(this).removeClass('active');
        });
    }, function () {
        $('#cart', this).fadeOut(200);
    });
    $("li.side_currency").hover(function () {
        $('#currency', this).fadeIn(500);
    }, function () {
        $('#currency', this).fadeOut(200);
    });
    $("li.side_lang").hover(function () {
        $('#language', this).fadeIn(500);
    }, function () {
        $('#language', this).fadeOut(200);
    });
    $("li.side_search").hover(function () {
        $('#search', this).fadeIn(500);
    }, function () {
        $('#search', this).fadeOut(200);
    });
    $(".main_menu li").hover(function () {
        $('.secondary', this).fadeIn(500);
    }, function () {
        $('.secondary', this).fadeOut(200);
    }); 
});