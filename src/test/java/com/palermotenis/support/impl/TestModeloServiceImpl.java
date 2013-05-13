package com.palermotenis.support.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Categoria;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.modelos.ModeloService;
import com.palermotenis.support.TestCategoriaService;
import com.palermotenis.support.TestMarcaService;
import com.palermotenis.support.TestModeloService;
import com.palermotenis.support.TestTipoProductoService;

@Service("testModeloService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestModeloServiceImpl implements TestModeloService {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Autowired
    private TestMarcaService testMarcaService;

    @Autowired
    private TestCategoriaService testCategoriaService;

    @Override
    public Modelo getModeloSimple() {
        return modeloService.getModeloByNombre(MODELO_SIMPLE_NAME);
    }

    @Override
    public Modelo getModeloClasificable() {
        return modeloService.getModeloByNombre(MODELO_CLASIFICABLE_NAME);
    }

    @Override
    public Modelo getModeloPresentable() {
        return modeloService.getModeloByNombre(MODELO_PRESENTABLE_NAME);
    }

    @Override
    public Modelo getModeloPresentableAndClasificable() {
        return modeloService.getModeloByNombre(MODELO_CLASIFICABLE_AND_PRESENTABLE_NAME);
    }

    @Override
    public Modelo refresh(Modelo modelo) {
        if (modelo != null) {
            return modeloService.getModeloById(modelo.getId());
        }
        return modelo;
    }

    @Override
    @Transactional(readOnly = false)
    public Modelo create() {
        TipoProducto tipoProducto = testTipoProductoService.getAny();
        Marca marca = testMarcaService.getAny();
        List<Categoria> categorias = Lists.newArrayList(testCategoriaService.getAny());
        String nombre = createName();
        return modeloService.createNewModelo(tipoProducto, marca, nombre, categorias);
    }

    @Override
    public Modelo getAny() {
        return getModeloSimple();
    }

    @Override
    @Transactional(readOnly = false)
    public Modelo createPadreWithChild() {
        Modelo padre = create();
        Integer padreId = padre.getId();
        Integer tipoProductoId = padre.getTipoProducto().getId();
        Integer marcaId = padre.getMarca().getId();
        String nombre = createName();

        Integer modeloId = modeloService.createNewModelo(padreId, tipoProductoId, marcaId, nombre);
        Modelo hijo = modeloService.getModeloById(modeloId);
        padre.addHijo(hijo);
        return padre;
    }

    private String createName() {
        return "Modelo." + RandomStringUtils.randomAlphabetic(5);
    }

}
