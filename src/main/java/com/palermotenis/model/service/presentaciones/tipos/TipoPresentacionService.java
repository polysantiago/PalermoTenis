package com.palermotenis.model.service.presentaciones.tipos;

import java.util.Collection;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;

public interface TipoPresentacionService {

    TipoPresentacion getTipoPresentacionById(Integer tipoPresentacionId);

    Collection<TipoPresentacion> getTiposPresentacionByTipoProducto(Integer tipoProductoId);

}
