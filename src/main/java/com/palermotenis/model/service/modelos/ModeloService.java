package com.palermotenis.model.service.modelos;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

public interface ModeloService {

    Modelo createNewModelo(TipoProducto tipoProducto, Marca marca, String nombre, List<Categoria> categorias);

    Integer createNewModelo(Integer padreId, Integer tipoProductoId, Integer marcaId, String nombre);

    void updateModelo(Integer modeloId, String nombre);

    void updateModelo(Modelo modelo, String nombre, Collection<Categoria> categorias);

    void moveModelo(Integer modeloId, Integer modeloOrden, Integer modeloRgtId, Integer modeloRgtOrden);

    void deleteModelo(Integer modeloId);

    Modelo getModeloById(Integer modeloId);

    Modelo getModeloByNombre(String nombre);

    List<Modelo> getAllModelos();

    List<Modelo> getModelosByTipoProducto(int tipoProductoId);

    List<Modelo> getModelosByMarcaAndTipoProducto(int marcaId, int tipoProductoId);

    List<Modelo> getModelosByMarcaAndActiveTipoProducto(int marcaId, int tipoProductoId);

    List<Modelo> getModelosByActiveParent(Modelo parent);

}
