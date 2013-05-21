package com.palermotenis.model.service.precios.impl;

import java.util.Collection;
import java.util.List;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.pks.PrecioPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

public interface PrecioService {

    void create(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Double valor,
            Double valorOferta, Integer cuotas, boolean enOferta);

    void update(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Double valor,
            Double valorOferta, Integer cuotas, boolean enOferta, Integer newPagoId, Integer newMonedaId,
            Integer newPresentacionId, Integer newCuotas);

    void destroy(Integer productoId, Integer pagoId, Integer monedaId, Integer presentacionId, Integer cuotas);

    void moveOffer(Integer productoId, Integer productoOrden, Integer productoRgtId, Integer productoRgtOrden);

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

    Precio getPrecioById(PrecioPK precioPk);

    List<? extends Precio> getPrecios(Integer productoId, Integer presentacionId);
}
