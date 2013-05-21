package com.palermotenis.model.service.geograficos;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.geograficos.Ciudad;
import com.palermotenis.model.beans.geograficos.Pais;
import com.palermotenis.model.beans.geograficos.Provincia;

public interface GeographicService {

    Provincia getProvinciaByNombre(String nombre) throws NoResultException;

    Pais getPaisByCodigo(String codigo) throws NoResultException;

    List<Ciudad> getCiudadedByNombre(String nombre);

}
