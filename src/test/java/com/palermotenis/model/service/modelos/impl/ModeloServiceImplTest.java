package com.palermotenis.model.service.modelos.impl;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.palermotenis.infrastructre.testsupport.base.BaseSpringTestCase;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.modelos.ModeloDao;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.support.TestCategoriaService;
import com.palermotenis.support.TestMarcaService;
import com.palermotenis.support.TestModeloService;
import com.palermotenis.support.TestTipoProductoService;

@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ModeloServiceImplTest extends BaseSpringTestCase {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ModeloDao modeloDao;

    @Autowired
    private TestModeloService testModeloService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Autowired
    private TestMarcaService testMarcaService;

    @Autowired
    private TestCategoriaService testCategoriaService;

    @Test
    public void testCreateNewModelo() {
        TipoProducto tipoProducto = testTipoProductoService.getAny();
        Marca marca = testMarcaService.getAny();
        List<Categoria> categorias = Lists.newArrayList(testCategoriaService.getAny());

        String nombre = createName();
        Modelo modelo = modeloService.createNewModelo(tipoProducto, marca, nombre, categorias);

        assertNotNull(modelo);
        assertEquals(tipoProducto, modelo.getTipoProducto());
        assertEquals(marca, modelo.getMarca());
        assertEquals(nombre, modelo.getNombre());
        assertTrue(modelo.hasCategoria(categorias.get(0)));
    }

    @Test
    public void testCreateNewModeloWithPadre() {
        Modelo padre = testModeloService.create();

        Integer padreId = padre.getId();
        Integer tipoProductoId = padre.getTipoProducto().getId();
        Integer marcaId = padre.getMarca().getId();
        String nombre = createName();

        Integer modeloId = modeloService.createNewModelo(padreId, tipoProductoId, marcaId, nombre);
        assertNotNull(modeloId);

        Modelo modelo = modeloService.getModeloById(modeloId);
        assertNotNull(modelo);
        assertEquals(padre, modelo.getPadre());
    }

    @Test
    public void testUpdateModelo() {
        Modelo modelo = testModeloService.create();
        assertNotNull(modelo);

        // first
        String newNombre = createName();
        modeloService.updateModelo(modelo.getId(), newNombre);

        modelo = testModeloService.refresh(modelo);
        assertNotNull(modelo);
        assertEquals(newNombre, modelo.getNombre());

        // second
        modelo = testModeloService.create();
        newNombre = createName();
        List<Categoria> newCategorias = Lists.newArrayList(testCategoriaService.create());
        modeloService.updateModelo(modelo, newNombre, newCategorias);

        modelo = testModeloService.refresh(modelo);
        assertNotNull(modelo);
        assertEquals(newNombre, modelo.getNombre());
        assertTrue(modelo.hasCategoria(newCategorias.get(0)));
    }

    @Test
    public void testDeleteModelo() {
        Modelo modelo = testModeloService.create();
        assertNotNull(modelo);

        modeloService.deleteModelo(modelo.getId());

        modelo = testModeloService.refresh(modelo);
        assertNull(modelo);
    }

    @Test
    public void testDeleteWithChildren() {
        Modelo padre = testModeloService.createPadreWithChild();
        Collection<Modelo> hijos = padre.getHijos();
        assertNotNull(padre);
        assertThat(hijos, is(not(empty())));

        modeloService.deleteModelo(padre.getId());

        padre = testModeloService.refresh(padre);
        assertNull(padre);
        for (Modelo hijo : hijos) {
            hijo = testModeloService.refresh(hijo);
            assertNull(hijo);
        }
    }

    private String createName() {
        return "Modelo." + RandomStringUtils.randomAlphanumeric(5);
    }

}
