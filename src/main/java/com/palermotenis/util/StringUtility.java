package com.palermotenis.util;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

public class StringUtility {

    public static String buildNameFromStock(Stock s) {
        Producto producto = s.getProducto();
        Modelo modelo = producto.getModelo();
        Presentacion presentacion = s.getPresentacion();
        StringBuilder sb = new StringBuilder();
        sb.append(producto.getTipoProducto().getNombre()).append(' ');
        sb.append(modelo.getMarca().getNombre()).append(' ');
        for (Modelo m : modelo.getPadres()) {
            sb.append(m.getNombre()).append(" ");
        }
        sb.append(modelo.getNombre()).append(" - ");
        if (presentacion != null) {
            sb.append(presentacion.getTipoPresentacion().getNombre()).append(' ');
            sb.append(presentacion.getNombre()).append(' ');
        }
        if (s.getValorClasificatorio() != null) {
            sb.append(s.getValorClasificatorio().getTipoAtributo().getNombre()).append(':').append(' ');
            sb.append(s.getValorClasificatorio().getNombre()).append(' ');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
