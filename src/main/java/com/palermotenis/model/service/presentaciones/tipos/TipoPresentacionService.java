package com.palermotenis.model.service.presentaciones.tipos;

import java.util.List;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;

public interface TipoPresentacionService {

    void createNewTipoPresentacion(Integer tipoProductoId, String nombre);

    void updateTipoPresentacion(Integer tipoPresentacionId, String nombre);

    void deleteTipoPresentacion(Integer tipoPresentacionId);

    TipoPresentacion getTipoPresentacionById(Integer tipoPresentacionId);

    List<TipoPresentacion> getAllTiposPresentacion();

    List<TipoPresentacion> getTiposPresentacionByTipoProducto(Integer tipoProductoId);

}
