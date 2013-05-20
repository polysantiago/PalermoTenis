package com.palermotenis.model.dao.precios;

import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.dao.Dao;

public interface PrecioDao extends Dao<Precio, PrecioPK> {

    void moveOffer(Integer productoId, Integer productoOrden, Integer productoRgtId, Integer productoRgtOrden);

}
