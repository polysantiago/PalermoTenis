package com.palermotenis.model.service.unidades;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Unidad;

public interface UnidadService {

    void createNewUnidad(String nombre, String descripcion);

    void updateUnidad(Integer unidadId, String nombre, String descripcion);

    void deleteUnidad(Integer unidadId);

    Unidad getUnidadById(Integer unidadId);

    Unidad getUnidadByNombre(String nombre) throws NoResultException;

    List<Unidad> getAllUnidades();

}
