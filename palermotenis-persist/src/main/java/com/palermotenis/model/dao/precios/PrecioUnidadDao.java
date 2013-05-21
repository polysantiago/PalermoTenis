package com.palermotenis.model.dao.precios;

import java.util.List;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioUnidad;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

public interface PrecioUnidadDao extends Dao<PrecioUnidad, PrecioPK> {

    List<PrecioUnidad> getPrecios(Producto producto);

    List<PrecioUnidad> getPrecios(Producto producto, Pago pago);

}
