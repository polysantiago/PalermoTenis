package com.palermotenis.model.dao.modelos;

import java.util.List;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;

public interface ModeloDao extends Dao<Modelo, Integer> {

    List<Modelo> getModelosByTipoProducto(TipoProducto tipoProducto);

    List<Modelo> getModelosByMarcaAndTipoProducto(Marca marca, TipoProducto tipoProducto);

    List<Modelo> getModelosWithRootActiveProductos(Marca marca, TipoProducto tipoProducto);

    List<Modelo> getModelosWithLeafActiveProductos(Marca marca, TipoProducto tipoProducto);

    List<Modelo> getModelosByPadre(Modelo padre);

    List<Modelo> getModelosByActiveParent(Modelo parent);

    void moveModelo(Integer modeloId, Integer modeloOrden, Integer modeloRgtId, Integer modeloRgtOrden);

    void updateTree(Integer right);

}
