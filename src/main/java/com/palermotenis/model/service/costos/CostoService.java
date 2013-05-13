package com.palermotenis.model.service.costos;

import java.util.List;

import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

public interface CostoService {

    List<Costo> getCostos(Producto producto, Integer proveedorId);

    List<Costo> getCostos(Producto producto, Integer proveedorId, Presentacion presentacion);

}
