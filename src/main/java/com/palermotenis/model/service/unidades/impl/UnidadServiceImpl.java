package com.palermotenis.model.service.unidades.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Unidad;
import com.palermotenis.model.dao.unidades.UnidadDao;
import com.palermotenis.model.service.unidades.UnidadService;

@Service("unidadService")
@Transactional(propagation = Propagation.REQUIRED)
public class UnidadServiceImpl implements UnidadService {

    @Autowired
    private UnidadDao unidadDao;

    @Override
    public Unidad getUnidadByNombre(String nombre) throws NoResultException {
        return unidadDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public List<Unidad> getAllUnidades() {
        return unidadDao.findAll();
    }

}
