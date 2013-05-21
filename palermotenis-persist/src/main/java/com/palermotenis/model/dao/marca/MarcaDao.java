package com.palermotenis.model.dao.marca;

import java.util.List;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

public interface MarcaDao extends Dao<Marca, Integer> {

    List<Marca> getActiveMarcasByTipoProducto(TipoProducto tipoProducto);

}
