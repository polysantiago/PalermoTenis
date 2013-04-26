package com.palermotenis.model.dao.productos.tipos;

import java.util.List;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

public interface TipoProductoDao extends Dao<TipoProducto, Integer> {

    List<TipoProducto> getRootsTipoProducto();

}
