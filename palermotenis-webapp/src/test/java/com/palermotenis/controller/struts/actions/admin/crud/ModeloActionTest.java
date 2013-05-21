package com.palermotenis.controller.struts.actions.admin.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.infrastructure.testsupport.base.BaseSpringStrutsTestCase;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.support.TestMarcaService;
import com.palermotenis.support.TestModeloService;
import com.palermotenis.support.TestTipoProductoService;

@TransactionConfiguration(defaultRollback = true)
public class ModeloActionTest extends BaseSpringStrutsTestCase<ModeloAction> {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TestModeloService testModeloService;

    @Autowired
    private TestMarcaService testMarcaService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Test
    @Transactional
    public void testCreate() throws UnsupportedEncodingException, ServletException {
        Marca testMarca = testMarcaService.getAny();
        TipoProducto testTipoProducto = testTipoProductoService.getTipoProductoSimple();

        request.setParameter("tipoProductoId", testTipoProducto.getId().toString());
        request.setParameter("marcaId", testMarca.getId().toString());
        request.setParameter("nombre", "testName");

        String result = executeAction("/admin/crud/ModeloAction_create");
        assertNotNull(result);
        assertNotEquals(StringUtils.EMPTY, result);

        Modelo modelo = modeloService.getModeloById(Integer.parseInt(result));

        assertNotNull(modelo);
        assertEquals(testMarca, modelo.getMarca());
        assertEquals(testTipoProducto, modelo.getTipoProducto());
        assertEquals("testName", modelo.getNombre());
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());
    }

    @Test
    @Transactional
    public void testEdit() throws UnsupportedEncodingException, ServletException {
        Modelo testModelo = testModeloService.getModeloSimple();
        Integer testModeloId = testModelo.getId();
        request.setParameter("modeloId", testModeloId.toString());
        request.setParameter("nombre", "testName");

        String result = executeAction("/admin/crud/ModeloAction_edit");
        assertEquals("OK", result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());

        testModelo = testModeloService.refresh(testModelo);
        assertEquals("testName", testModelo.getNombre());
    }

    @Test
    @Transactional
    public void testDestroy() throws UnsupportedEncodingException, ServletException {
        Modelo testModelo = testModeloService.getModeloSimple();
        request.setParameter("modeloId", testModelo.getId().toString());

        String result = executeAction("/admin/crud/ModeloAction_destroy");
        assertEquals("OK", result);
        assertEquals("text/plain;charset=ISO-8859-1", response.getContentType());

        testModelo = testModeloService.refresh(testModelo);
        assertNull(testModelo);
    }

    @Override
    protected String getActionName() {
        return "ModeloAction_*";
    }

    @Override
    protected String getActionNamespace() {
        return "/admin/crud";
    }

}
