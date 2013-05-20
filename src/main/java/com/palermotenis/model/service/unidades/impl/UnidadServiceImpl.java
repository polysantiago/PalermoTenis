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
    public void createNewUnidad(String nombre, String descripcion) {
        Unidad unidad = new Unidad(nombre, descripcion);
        unidadDao.create(unidad);
    }

    @Override
    public void updateUnidad(Integer unidadId, String nombre, String descripcion) {
        Unidad unidad = getUnidadById(unidadId);
        unidad.setNombre(nombre);
        unidad.setDescripcion(descripcion);
        unidadDao.edit(unidad);
    }

    @Override
    public void deleteUnidad(Integer unidadId) {
        Unidad unidad = getUnidadById(unidadId);
        unidadDao.destroy(unidad);
    }

    @Override
    public Unidad getUnidadByNombre(String nombre) throws NoResultException {
        return unidadDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public List<Unidad> getAllUnidades() {
        return unidadDao.findAll();
    }

    @Override
    public Unidad getUnidadById(Integer unidadId) {
        return unidadDao.find(unidadId);
    }

}
