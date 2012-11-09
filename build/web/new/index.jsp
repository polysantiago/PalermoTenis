<%-- 
    Document   : index
    Created on : Aug 22, 2012, 2:39:40 PM
    Author     : Santiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link type="text/css" rel="stylesheet" href="assets/css/bootstrap.min.css" />
        <link type="text/css" rel="stylesheet" href="assets/css/nivo-slider.css" />
        <link type="text/css" rel="stylesheet" href="assets/css/nivo-slider-theme-dark.css" />
        <link type="text/css" rel="stylesheet" href="assets/css/style.css" />

        <link type="image/x-icon" rel="shortcut icon" href="/images/favicon.ico" />
        <link type="image/x-icon" rel="icon" href="/images/favicon.ico" />

        <script charset="iso-8859-1" type="text/javascript" src="/js/main/pack/AC_RunActiveContent.pack.js" ></script>
        <script charset="iso-8859-1" type="text/javascript" src="assets/js/jquery-1.8.0.min.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="assets/js/jquery.nivo.slider.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="assets/js/jquery.roundabout.min.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="assets/js/bootstrap.min.js"></script>
        <script charset="iso-8859-1" type="text/javascript" src="assets/js/script.js"></script>

        <title>Palermo Tenis</title>
    </head>
    <body>
        <div class="container">
            <div id="sidefeatures">
                <ul>
                    <li class="side_cart"><span class="icon">Carrito</span>
                        <div id="cart">
                            <div class="heading">
                                <h4>Carrito</h4>
                                <a><span id="cart_total">0 producto(s) - $0.00</span></a></div>
                            <div class="content"></div>
                        </div>
                    </li>
                    <li class="side_currency"><span class="icon">Moneda</span>
                        <form enctype="multipart/form-data" method="post" action="#">
                            <div id="currency" style="display: none;">
                                Moneda
                                <a title="Euro">€</a>
                                <a title="Pound Sterling">£</a>
                                <a title="US Dollar"><b>$</b></a>
                            </div>
                        </form>
                    </li>
                    <li class="side_lang"><span class="icon">Idioma</span>
                        <form enctype="multipart/form-data" method="post" action="#">
                            <div id="language">
                                Idioma
                                <img title="Inglés" alt="Inglés" src="assets/img/gb.png">
                            </div>
                        </form>
                    </li>
                    <li class="side_search"><span class="icon">Buscar</span>
                        <div id="search" style="display: none;">
                            <input type="text" onkeydown="this.style.color = '#000000';" onclick="this.value = '';" value="Buscar" name="filter_name">
                            <div class="button-search"></div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="navbar">
                <div class="navbar-inner">
                    <div class="container">
                        <div class="nav-collapse">
                            <ul class="nav">
                                <li class="active"><a href="#">Home</a></li>
                                <li><a href="#">Palermo Tenis</a></li>
                                <li><a href="#">Ingresar</a></li>
                                <li><a href="#">Registrarse</a></li>                                
                            </ul>
                            <div id="logo-container" class="span4">
                                <script type="text/javascript">
                                    AC_FL_RunContent( 'codebase','http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0','width','242','height','123','src','/flash/titulo-izq','quality','high','pluginspage','http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash','movie','/flash/titulo-izq' ); //end AC code
                                </script>
                                <noscript>
                                <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,28,0" width="242" height="123">
                                    <param name="movie" value="/flash/titulo-izq.swf" />
                                    <param name="quality" value="high" />
                                    <embed src="/flash/titulo-izq.swf" quality="high" pluginspage="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="450" height="123"></embed>
                                </object>
                                </noscript>
                            </div>
                            <ul class="nav">
                                <li><a href="#">Ubicación</a></li>
                                <li><a href="#">Envíos</a></li>
                                <li><a href="#">Contacto</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="navbar">
                <div class="navbar-inner pull-center">
                    <div class="container">
                        <div class="nav-collapse">
                            <ul class="nav" role="navigation">
                                
                                <s:action namespace="/" ignoreContextParams="true" executeResult="false" var="cat" name="GetCategorias" />
                                <h1><s:property value="#cat.categorias == null" /></h1>
                                <s:iterator value="#cat.categorias" var="c">
                                    <h1><s:property value="#c.nombre" /></h1>
                                </s:iterator>
                                
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Raquetas</a>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                        <li><a href="#">Tenis</a></li>
                                        <li><a href="#">Niños</a></li>
                                        <li><a href="#">Paddle</a></li>
                                        <li><a href="#">Squash</a></li>
                                    </ul>
                                </li>
                                <li class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Indumentaria</a>
                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                        <li class="dropdown">
                                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Calzado</a>
                                            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                                                <li><a href="#">Tenis</a></li>
                                                <li><a href="#">Running</a></li>
                                            </ul>
                                        </li>
                                        <li><a href="#">Chalecos, Buzos &AMP; Camperas</a></li>
                                        <li><a href="#">Calzado de Tenis</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Accesorios</a></li>
                                <li><a href="#">Tablas</a></li>
                                <li><a href="#">Rollers</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="span12">
                    <div class="slider-wrapper theme-dark">
                        <div id="slider" class="nivoSlider">
                            <img src="assets/img/slide1.png" alt="" />
                            <img src="assets/img/slide2.jpeg" alt="" />
                            <img src="assets/img/slide3.jpeg" alt="" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="well">
                <div class="box row">
                    <div class="span12">
                        <h2 class="heading-title"><span>Productos Destacados</span></h2>
                    </div>
                    <div class="row">
                        <div class="span12">
                            <ul id="featured-products">
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet1.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 1</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet2.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 2</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet3.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 3</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet4.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 4</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet5.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 5</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet6.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 6</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet7.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 7</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                                <li>
                                    <div class="product-holder">
                                        <div class="thumbnail">
                                            <a href="#"><img src="assets/img/racquet8.jpeg" /></a>
                                        </div>
                                        <h3>Raqueta 8</h3>
                                    </div>
                                    <span class="pricetag">$200</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="box row">
                    <div class="span12">
                        <h2 class="heading-title">
                            <span>Bienvenido a Palermo Tenis!</span>
                        </h2>
                        <div class="description">
                            <p>
                                En nuestro local podrás encontrar gran variedad de raquetas, encordados, accesorios. Sumado a nuestra excelente predisposición a la hora de asesorarte en tu decisión, brindarte un precio justo y facilidades de pago, para que no tengas que dar más vueltas. Muy cerca del Alto Palermo Shopping, vení a PALERMO TENIS. O navegá en nuestra página, elegí lo que quieras y recibilo en tu casa, en cualquier punto del país.
                            </p>
                        </div>
                    </div>
                </div>

                <div class="box row">
                    <div class="span12">
                        <h2 class="heading-title">
                            <span>Lo último</span>
                        </h2>
                    </div>
                    <div class="span12">
                        <div class="box-content">
                            <div class="box-product fixed container-fluid">
                                <div class="row-fluid">
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_1.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_2.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_3.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_4.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_5.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_6.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_7.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="price">$2000,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                    <div class="product-holder span3">
                                        <div class="image">
                                            <a href="#"><img class="thumbnail" src="assets/img/racquet180x180_8.jpeg" /></a>
                                        </div>
                                        <div class="pricetag-small">
                                            <span class="old-price">$2000,00</span>
                                            <span class="new-price">$1900,00</span>
                                        </div>
                                        <div class="info" style="visibility: hidden;">
                                            <h3>Aero Pro Drive Plus GT</h3>
                                            <p>La AeroPro Drive Plus GT se presenta con la potencia y los efectos de la versión habitual elevados a un...</p>
                                            <a class="btn btn-mini" class="add_to_cart_small">Agregar</a>
                                            <a class="btn btn-mini" class="compare_small">Comparar</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>                
            </div>
            <footer class="well">
                <div class="row">
                    <div class="span4">
                        <h3>Sobre Palermo Tenis</h3>
                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset...</p>
                    </div>
                    <div class="span4">
                        <h3>Servicio al Cliente</h3>
                        <ul class="unstyled">
                            <li><a href="#">Home</a></li>
                            <li><a href="#">Ingresar</a></li>
                            <li><a href="#">Registrarse</a></li>                            
                        </ul>
                    </div>
                    <div class="span3">
                        <h3>Información</h3>
                        <ul class="unstyled">
                            <li><a href="#">Sobre nosotros</a></li>
                            <li><a href="#">Envíos</a></li>
                            <li><a href="#">Política de Privacidad</a></li>
                            <li><a href="#">Términos y Condiciones</a></li>
                            <li><a href="#">Contacto</a></li>
                            <li><a href="#">Sitemap</a></li>
                        </ul>
                    </div>                    
                </div>
                <span class="pull-left">PalermoTenis.com &COPY; 2012</span>
                <span class="pull-right">Güemes 3372, Ciudad Autónoma de Buenos Aires</span>
            </footer>
        </div>
    </body>
</html>
