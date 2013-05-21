package com.palermotenis.model.dao.productos;

import java.util.List;

import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

public interface ProductoDao extends Dao<Producto, Integer> {

    Producto getProductoByModelo(Modelo modelo);

    List<Producto> getProductosOnSale();

    List<Producto> getProductosInOferta(int maxResults);

    List<Producto> getProductosRelacionados(Producto producto);

}
