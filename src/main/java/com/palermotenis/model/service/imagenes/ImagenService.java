package com.palermotenis.model.service.imagenes;

import javax.persistence.EntityNotFoundException;

import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;

public interface ImagenService {

    TipoImagen getTipoImagen(Character tipo) throws EntityNotFoundException;

}
