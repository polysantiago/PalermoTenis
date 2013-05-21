package com.palermotenis.model.dao.presentaciones;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.dao.Dao;

public interface PresentacionDao extends Dao<Presentacion, Integer> {

    List<Presentacion> getPresentaciones(Collection<TipoPresentacion> tiposPresentacion);

}
