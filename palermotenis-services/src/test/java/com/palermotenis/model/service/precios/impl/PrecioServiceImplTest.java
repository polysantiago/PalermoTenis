package com.palermotenis.model.service.precios.impl;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringTestCase;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.dao.precios.PrecioDao;
import com.palermotenis.model.service.precios.PrecioService;
import com.palermotenis.support.TestMonedaService;
import com.palermotenis.support.TestPagoService;
import com.palermotenis.support.TestProductoService;

@TransactionConfiguration(defaultRollback = false)
public class PrecioServiceImplTest extends BaseSpringTestCase {

    @Autowired
    private TestProductoService testProductoService;

    @Autowired
    private TestPagoService testPagoService;

    @Autowired
    private TestMonedaService testMonedaService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private PrecioDao precioDao;

    @Test
    public void testCreate() {
        Producto producto = testProductoService.create();
        Pago pago = testPagoService.getAny();
        Moneda moneda = testMonedaService.getAny();

        precioService.create(producto.getId(), pago.getId(), moneda.getId(), null, 123.4, null, null, false);

        List<? extends Precio> precios = precioService.getPrecios(producto.getId());

        assertNotNull(precios);
        assertThat(precios, is(not(empty())));

        Precio precio = precios.get(0);
        assertEquals(producto, precio.getId().getProducto());
        assertEquals(pago, precio.getId().getPago());
        assertEquals(moneda, precio.getId().getMoneda());
        assertEquals(new Integer(1), precio.getId().getCuotas());
        assertEquals(new Double(123.4), precio.getValor());
    }

    @Test
    public void testCalculateCotizacion() {
        double cotizacion = precioService.calculateCotizacion("ARS", "USD");
        assertNotEquals(1.00, cotizacion);
    }

}
