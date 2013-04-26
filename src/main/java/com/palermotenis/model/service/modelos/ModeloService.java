package com.palermotenis.model.service.modelos;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Modelo;

public interface ModeloService {

    void updateModelo(Modelo modelo, String nombre, Collection<Categoria> categorias);

    Modelo getModeloById(Integer modeloId);

    List<Modelo> getAllModelos();

    List<Modelo> getModelosByTipoProducto(int tipoProductoId);

    List<Modelo> getModelosByMarcaAndTipoProducto(int marcaId, int tipoProductoId);

    List<Modelo> getModelosByMarcaAndActiveTipoProducto(int marcaId, int tipoProductoId);

    List<Modelo> getModelosByActiveParent(Modelo parent);

}
