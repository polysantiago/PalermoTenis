package com.palermotenis.model.service.costos;

import java.util.List;

import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

public interface CostoService {

    void createCosto(Double valor, Integer productoId, Integer monedaId, Integer proveedorId, Integer presentacionId);

    void updateCosto(Integer costoId, Double valor, Integer productoId, Integer monedaId, Integer proveedorId,
            Integer presentacionId);

    void deleteCosto(Integer costoId);

    Costo getCostoById(Integer costoId);

    List<Costo> getCostos(Producto producto, Integer proveedorId);

    List<Costo> getCostos(Producto producto, Integer proveedorId, Presentacion presentacion);

}
