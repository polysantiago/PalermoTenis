package com.palermotenis.model.service.productos;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.palermotenis.model.beans.productos.Producto;

public interface ProductoService {

    List<Producto> getProductosOnSale();

    void createNewProducto(int modeloId, int tipoProductoId, String descripcion, Collection<Integer> categoriasIds,
            Map<Integer, String> atributosSimples, Map<Integer, Integer> atributosTipados,
            Map<Integer, Collection<String>> atributosMultiples);

    void updateProducto(int modeloId, String nombre, String descripcion, boolean activo,
            Collection<Integer> categoriasIds, Map<Integer, String> atributosSimples,
            Map<Integer, Integer> atributosTipados, Map<Integer, Collection<String>> atributosMultiples);

    Producto getProductById(Integer productoId);

    void delete(Producto producto);

}
