<%@page contentType="text/html" pageEncoding="iso-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Panel de Control - PalermoTenis.com.ar</title>

        <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
        <style>
            body { padding-top: 60px; }
        </style>
        <link href="/css/bootstrap/bootstrap-responsive.min.css" rel="stylesheet">
        <!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
    </head>
    <body>

        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="brand" href="#">Palermo Tenis</a>
                    <div class="nav-collapse collapse">
                        <ul class="nav">
                            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPERVISOR')">
                                <li class="active"><a href="/admin/objetos/Modelo/Modelo.jsp">Catálogo</a></li>
                                <li><a href="/admin/compras/NuevaCompra">Compras</a></li>
                                <li><a href="/admin/ventas/Ventas.jsp">Ventas</a></li>
                                <li><a href="/admin/estadisticas/Stock">Stock</a></li>
                                <li><a href="#">Usuarios</a></li>
                                <li><a href="/admin/newsletter/Newsletter.jsp">Newsletter</a></li>
                            </sec:authorize>
                            <li><a href="/admin/usuarios/MiUsuario">Perfil</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="/js/bootstrap/src/bootstrap.js"></script>
    </body>
</html>
