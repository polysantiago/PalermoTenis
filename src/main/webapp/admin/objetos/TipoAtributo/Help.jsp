<%--
    Document   : Help
    Created on : 22/11/2009, 22:11:02
    Author     : Poly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ayuda</title>
        <style type="text/css">
            thead tr th {background-color:#A4A5A5;color:#FFFFFF;}
            tabla {border-spacing: 10px;}
            tbody td{border: 1px solid;}
            table, th, td{border: 1px solid black; padding: 3px;}
        </style>
    </head>
    <body>
        <table id="tabla">
            <thead>
                <tr>
                    <th>Tipo</th>
                    <th>Descripción</th>
                    <th>Ejemplo</th>
                    <th>Valores Posibles</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Simple</td>
                    <td>Se utiliza para valores simples, como pueden ser números o texto variable (no son fijos ni repetitivos) y también para los valores binarios (verdadero o falso).</td>
                    <td>Largo de una raqueta, nombre de un cliente, si determinado bolso es térmico o no, etc.</td>
                    <td>No</td>
                </tr>
                <tr>
                    <td>Tipado</td>
                    <td>Se utiliza para valores que deben ser seleccionados de una lista. No puede seleccionarse otro valor que no esté contenido en la lista de valores posibles y sólo puede seleccionarse UN valor de la lista. También se puede interpretar como tipificado.</td>
                    <td>Velocidad de swing de una raqueta (alto/medio/bajo), estilo del calzado (fútbol 5, running, tenis, tiempo libre), Tamaño de un bolso (simple, doble, triple),etc.</td>
                    <td>Sí</td>
                </tr>
                <tr>
                    <td>Múltiple Valores</td>
                    <td>Son una extensión de tipado con la única diferencia que pueden seleccionarse más de un valor de la lista de valores posibles.</td>
                    <td>Forma de los antivibradores (fantasía, logo de la marca), virtud de los cubregrips (liso, muy finito, mayor agarre), etc.</td>
                    <td>Sí</td>
                </tr>
                <tr>
                    <td>Valores Clasificatorios</td>
                    <td>Son los más complejos. También una extensión de  tipado, se utilizan para diferenciar los productos entre sí aunque esto no quiera decir que cada diferenciación representa un nuevo producto (ergo, no tienen un precio diferente). Físicamente, cada producto con un valor clasificatorio diferente, significa un producto físico diferente y esto hace que sean diferenciados también al momento de contabilizar el stock de ese producto. El más claro ejemplo, es el talle en el calzado. Cada calzado tiene el mismo valor y las mismas características, pero si varía su talle, el stock para ese determinado talle debe contabilizarse aparte de otros calzados con diferente talle; a su vez, el cliente deberá elegir el talle de calzado que desea antes de efectuar una compra o un pedido. Los valores clasificatorios deben ser seleccionados de una lista de valores posibles.</td>
                    <td>Tamaño de grip de las raquetas, talle del calzado, calibre de los encordados, etc.</td>
                    <td>Sí</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>