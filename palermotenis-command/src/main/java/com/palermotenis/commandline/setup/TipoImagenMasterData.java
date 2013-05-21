package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.model.dao.imagenes.TipoImagenDao;
import com.palermotenis.model.service.imagenes.ImagenService;

public class TipoImagenMasterData implements MasterData {

    private static final TipoImagen COMPLETO = new TipoImagen('C', "Tamaño Completo", 600, 514);
    private static final TipoImagen MINIATURA = new TipoImagen('M', "Miniatura", 65, 56);
    private static final TipoImagen PRESENTACION = new TipoImagen('P', "Presentación", 350, 300);
    private static final TipoImagen THUMBNAIL = new TipoImagen('T', "Thumbnail", 120, 103);

    private static final List<TipoImagen> ALL_TIPOS_IMAGENES = Lists.newArrayList();

    static {
        ALL_TIPOS_IMAGENES.add(COMPLETO);
        ALL_TIPOS_IMAGENES.add(PRESENTACION);
        ALL_TIPOS_IMAGENES.add(MINIATURA);
        ALL_TIPOS_IMAGENES.add(THUMBNAIL);
    }

    private final TipoImagenDao tipoImagenDao;
    private final ImagenService imagenService;

    public TipoImagenMasterData(TipoImagenDao tipoImagenDao, ImagenService imagenService) {
        this.tipoImagenDao = tipoImagenDao;
        this.imagenService = imagenService;
    }

    @Override
    public void createOrUpdate() {
        for (TipoImagen tipoImagen : ALL_TIPOS_IMAGENES) {
            try {
                TipoImagen dbInstance = imagenService.getTipoImagen(tipoImagen.getTipo());
                dbInstance.setNombre(tipoImagen.getNombre());
                dbInstance.setHeight(tipoImagen.getHeight());
                dbInstance.setWidth(tipoImagen.getWidth());
                tipoImagenDao.edit(dbInstance);
            } catch (EntityNotFoundException ex) {
                tipoImagenDao.create(tipoImagen);
            }
        }
    }

}
