/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author Poly
 */
public class StringUtility {

    private final static String pool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static InputStream getInputString(String s) {
        try {
            return new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StringUtility.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String buildRandomString() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 9; i++) {
            sb.append(pool.charAt(r.nextInt(pool.length())));
        }
        return sb.toString();
    }

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

    public static String toCamelCase(String str) {
        StringBuilder result = new StringBuilder(str.length());
        String strl = str.toLowerCase();
        boolean bMustCapitalize = false;
        for (int i = 0; i < strl.length(); i++) {
            char c = strl.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (bMustCapitalize) {
                    result.append(strl.substring(i, i + 1).toUpperCase());
                    bMustCapitalize = false;
                } else {
                    result.append(c);
                }
            } else {
                bMustCapitalize = true;
            }
        }
        return result.toString();
    }
}
