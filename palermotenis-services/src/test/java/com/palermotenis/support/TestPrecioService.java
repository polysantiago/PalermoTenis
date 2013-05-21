package com.palermotenis.support;

import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;

public interface TestPrecioService extends TestService<Precio> {

    Precio create(Producto producto);

}
