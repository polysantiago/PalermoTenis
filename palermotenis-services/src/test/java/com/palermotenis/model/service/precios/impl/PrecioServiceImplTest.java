package com.palermotenis.model.service.precios.impl;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringTestCase;
import com.palermotenis.model.dao.precios.PrecioDao;

public class PrecioServiceImplTest extends BaseSpringTestCase {

    @Autowired
    private PrecioService precioService;

    @Autowired
    private PrecioDao precioDao;

    @Test
    public void testCreate() {
        precioDao.findAll();
    }

    @Test
    public void testCalculateCotizacion() {
        double cotizacion = precioService.calculateCotizacion("ARS", "USD");
        assertNotEquals(1.00, cotizacion);
    }

}
