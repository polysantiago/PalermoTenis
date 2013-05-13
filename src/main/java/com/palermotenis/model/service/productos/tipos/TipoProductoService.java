package com.palermotenis.model.service.productos.tipos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

public interface TipoProductoService {

    void createNewTipoProducto(String nombre, boolean presentable);

    void updateTipoProducto(Integer tipoProductoId, String nombre, boolean presentable);

    void deleteTipoProducto(Integer tipoProductoId);

    TipoProducto getTipoProductoById(Integer tipoProductoId);

    TipoProducto getTipoProductoByNombre(String nombre) throws NoResultException;

    List<TipoProducto> getAllTipoProducto();

    List<TipoProducto> getAllTiposProductoPresentables();

    List<TipoProducto> getRootsTipoProducto();

    Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> getTiposProductoAndMarcasAndProductoCount();

}
