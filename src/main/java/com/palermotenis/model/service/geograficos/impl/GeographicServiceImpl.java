package com.palermotenis.model.service.geograficos.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.beans.geograficos.Provincia;
import com.palermotenis.model.dao.geograficos.CiudadDao;
import com.palermotenis.model.dao.geograficos.PaisDao;
import com.palermotenis.model.dao.geograficos.ProvinciaDao;
import com.palermotenis.model.service.geograficos.GeographicService;

@Service("geographicService")
@Transactional(propagation = Propagation.REQUIRED)
public class GeographicServiceImpl implements GeographicService {

    @Autowired
    private CiudadDao ciudadDao;

    @Autowired
    private ProvinciaDao provinciaDao;

    @Autowired
    private PaisDao paisDao;

    @Override
    public List<Ciudad> getCiudadedByNombre(String nombre) {
        return ciudadDao.queryBy("Nombre", "nombre", nombre);
    }

    @Override
    public Provincia getProvinciaByNombre(String nombre) throws NoResultException {
        return provinciaDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public Pais getPaisByCodigo(String codigo) throws NoResultException {
        return paisDao.findBy("Codigo", "codigo", codigo);
    }

}
