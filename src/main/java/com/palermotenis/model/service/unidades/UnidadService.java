package com.palermotenis.model.service.unidades;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Unidad;

public interface UnidadService {

    Unidad getUnidadByNombre(String nombre) throws NoResultException;

    List<Unidad> getAllUnidades();

}
