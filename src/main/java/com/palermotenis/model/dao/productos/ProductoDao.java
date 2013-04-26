package com.palermotenis.model.dao.productos;

import java.util.List;

import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

public interface ProductoDao extends Dao<Producto, Integer> {

    List<Producto> getProductosOnSale();

}
