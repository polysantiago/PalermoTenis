package com.palermotenis.model.dao.precios;

import java.util.List;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.Dao;

public interface PrecioPresentacionDao extends Dao<PrecioPresentacion, PrecioPK> {

    List<PrecioPresentacion> getPrecios(Producto producto);

    List<PrecioPresentacion> getPrecios(Producto producto, Presentacion presentacion);

    List<PrecioPresentacion> getPrecios(Producto producto, Presentacion presentacion, Pago pago);

}
