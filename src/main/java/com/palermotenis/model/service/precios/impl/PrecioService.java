package com.palermotenis.model.service.precios.impl;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

public interface PrecioService {

    double calculatePrecioUnitarioPesos(Precio precio);

    double calculatePrecioUnitarioDolares(Precio precio);

    double calculateSubtotalPesos(Precio precio, int cantidad);

    double calculateSubtotalDolares(Precio precio, int cantidad);

    double calculateCotizacion(String from, String to);

    double calculateTotalPesos(Collection<PedidoProducto> pps);

    double calculateTotalDolares(Collection<PedidoProducto> pps);

    Precio estimarPrecio(PedidoProducto pedidoProducto);

    Precio estimarPrecio(Stock stock, int cuotas);

    Precio estimarPrecio(Stock stock, Pago pago, int cuotas);

    Precio estimarPrecio(Presentacion presentacion, Producto producto, int cuotas, Pago pago);

    boolean hasPrecio(Integer productoId);

    boolean hasPrecio(Producto producto);

    List<PrecioPresentacion> getPrecios(Integer productoId, Integer presentacionId);
}
