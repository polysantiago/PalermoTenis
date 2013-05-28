package com.palermotenis.controller.struts.actions.admin.crud;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.service.precios.PrecioService;
import com.palermotenis.support.TestMonedaService;
import com.palermotenis.support.TestPagoService;
import com.palermotenis.support.TestProductoService;

public class PrecioActionTest extends BaseSpringStrutsTestCase<PrecioAction> {

    @Autowired
    private TestProductoService testProductoService;

    @Autowired
    private TestPagoService testPagoService;

    @Autowired
    private TestMonedaService testMonedaService;

    @Autowired
    private PrecioService precioService;

    @Test
    @Transactional
    public void testCreatePrecioUnidad() throws UnsupportedEncodingException, ServletException {
        Producto producto = testProductoService.create();
        Pago pago = testPagoService.getAny();
        Moneda moneda = testMonedaService.getAny();

        request.setParameter("productoId", producto.getId().toString());
        request.setParameter("pagoId", pago.getId().toString());
        request.setParameter("monedaId", moneda.getId().toString());
        request.setParameter("valor", String.valueOf(123.4));

        String result = executeAction("/admin/crud/PrecioAction_create");
        assertEquals("OK", result);

        List<? extends Precio> precios = precioService.getPrecios(producto.getId());
        assertNotNull(precios);
        assertThat(precios, is(not(empty())));
    }

    @Test
    @Transactional
    public void testEdit() {
        fail("Not yet implemented");
    }

    @Test
    @Transactional
    public void testDestroy() {
        fail("Not yet implemented");
    }

    @Test
    @Transactional
    public void testMove() {
        fail("Not yet implemented");
    }

    @Override
    protected String getActionName() {
        return "PrecioAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
