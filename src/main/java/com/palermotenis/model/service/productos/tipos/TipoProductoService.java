package com.palermotenis.model.service.productos.tipos;

import java.util.List;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;

public interface TipoProductoService {

    TipoProducto getTipoProductoById(Integer tipoProductoId);

    List<TipoProducto> getAllTipoProducto();

    List<TipoProducto> getRootsTipoProducto();

}
