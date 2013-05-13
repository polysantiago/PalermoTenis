package com.palermotenis.model.service.imagenes.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.imagenes.tipos.TipoImagen;
import com.palermotenis.model.dao.imagenes.TipoImagenDao;
import com.palermotenis.model.service.imagenes.ImagenService;

@Service("imagenService")
@Transactional(propagation = Propagation.REQUIRED)
public class ImagenServiceImpl implements ImagenService {

    @Autowired
    private TipoImagenDao tipoImagenDao;

    @Override
    public TipoImagen getTipoImagen(Character tipo) throws EntityNotFoundException {
        return tipoImagenDao.load(tipo);
    }

}
