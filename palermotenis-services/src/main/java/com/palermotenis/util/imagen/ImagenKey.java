/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.util.imagen;

import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;

/**
 *
 * @author Poly
 */
public class ImagenKey {

    private Imagen imagen;
    private TipoImagen tipoImagen;

    public ImagenKey(Imagen imagen, TipoImagen tipoImagen) {
        this.imagen = imagen;
        this.tipoImagen = tipoImagen;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public TipoImagen getTipoImagen() {
        return tipoImagen;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImagenKey other = (ImagenKey) obj;
        if (this.imagen != other.imagen && (this.imagen == null || !this.imagen.equals(other.imagen))) {
            return false;
        }
        if (this.tipoImagen != other.tipoImagen && (this.tipoImagen == null || !this.tipoImagen.equals(other.tipoImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.imagen != null ? this.imagen.hashCode() : 0);
        hash = 17 * hash + (this.tipoImagen != null ? this.tipoImagen.hashCode() : 0);
        return hash;
    }
    
}
