package com.palermotenis.support.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.precios.pks.PrecioProductoPK;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.precios.impl.PrecioService;
import com.palermotenis.support.TestPagoService;
import com.palermotenis.support.TestPrecioService;
import com.palermotenis.support.TestProductoService;

@Service("testPrecioService")
public class TestPrecioServiceImpl implements TestPrecioService {

    @Autowired
    private PrecioService precioService;

    @Autowired
    private TestPagoService testPagoService;

    @Autowired
    private TestProductoService testProductoService;

    @Autowired
    private MonedaService monedaService;

    @Override
    public Precio refresh(Precio precio) {
        if (precio != null) {
            return precioService.getPrecioById(precio.getId());
        }
        return precio;
    }

    @Override
    @Transactional
    public Precio create() {
        Producto producto = testProductoService.create();
        return create(producto);
    }

    @Override
    @Transactional
    public Precio create(Producto producto) {
        Integer productoId = producto.getId();

        Pago pago = testPagoService.getPagoEfectivo();
        Integer pagoId = pago.getId();

        Moneda moneda = monedaService.getMonedaByCodigo("ARS");
        Integer monedaId = moneda.getId();

        Integer presentacionId = null;

        Double valor = 123.4;

        boolean enOferta = false;
        Double valorOferta = null;

        Integer cuotas = null;

        precioService.create(productoId, pagoId, monedaId, presentacionId, valor, valorOferta, cuotas, enOferta);
        return precioService.getPrecioById(new PrecioProductoPK(producto, moneda, pago, cuotas));
    }

    @Override
    public Precio getAny() {
        // TODO Auto-generated method stub
        return null;
    }

}
