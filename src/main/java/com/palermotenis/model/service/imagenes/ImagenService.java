package com.palermotenis.model.service.imagenes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.palermotenis.model.beans.imagenes.Imagen;
import com.palermotenis.model.beans.imagenes.ImagenEscalada;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;

public interface ImagenService {

    Imagen create(Integer modeloId, File imagenFile, String fileName, String contentType, String categoria)
            throws IOException;

    void upload(Integer modeloId, File imagenFile, String fileName, String contentType) throws IOException;

    void destroy(Integer imagenId);

    Imagen getImagenByHashKey(String hashKey);

    ImagenEscalada getImagenEscalada(String hashKey, Character tipoImagenId) throws IOException;

    TipoImagen getTipoImagen(Character tipo) throws EntityNotFoundException;

    List<Imagen> getImagenesByModelo(Integer modeloId);

}
