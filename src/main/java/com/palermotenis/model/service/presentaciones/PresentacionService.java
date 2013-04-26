package com.palermotenis.model.service.presentaciones;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;

public interface PresentacionService {

    void createNewPresentacion(Integer tipoPresentacionId, Double cantidad, String unidad, String nombre);

    void updatePresentacion(Integer presentacionId, Double cantidad, String nombre, String unidad);

    void delete(Integer presentacionId);

    List<Presentacion> getPresentacionesByTipo(Integer tipoPresentacionId);

    List<Presentacion> getPresentacionesByTipo(TipoPresentacion tipoPresentacion);

    List<Presentacion> getPresentacionesByTipos(Collection<TipoPresentacion> tiposPresentacion);

}
