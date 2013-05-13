package com.palermotenis.model.dao.costos;

import java.util.List;

import com.palermotenis.model.beans.compras.Costo;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.proveedores.Proveedor;
import com.palermotenis.model.dao.Dao;

public interface CostoDao extends Dao<Costo, Integer> {

    List<Costo> getCostosOfProducto(Producto producto, Proveedor proveedor);

    List<Costo> getCostosOfProductoPresentable(Producto producto, Proveedor proveedor, Presentacion presentacion);

}
