package com.palermotenis.support.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.support.TestTipoProductoService;

@Service("testTipoProductoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestTipoProductoServiceImpl implements TestTipoProductoService {

    @Autowired
    private TipoProductoService tipoProductoService;

    @Override
    public TipoProducto getTipoProductoSimple() {
        return getByNombre(TIPO_PRODUCTO_SIMPLE_NAME);
    }

    @Override
    public TipoProducto getTipoProductoClasificable() {
        return getByNombre(TIPO_PRODUCTO_CLASIFICABLE_NAME);
    }

    @Override
    public TipoProducto getTipoProductoPresentable() {
        return getByNombre(TIPO_PRODUCTO_PRESENTABLE_NAME);
    }

    @Override
    public TipoProducto getTipoProductoPresentableAndClasificable() {
        return getByNombre(TIPO_PRODUCTO_CLASIFICABLE_AND_PRESENTABLE_NAME);
    }

    private TipoProducto getByNombre(String nombre) {
        return tipoProductoService.getTipoProductoByNombre(nombre);
    }

    @Override
    @Transactional(readOnly = false)
    public TipoProducto create() {
        return create(false);
    }

    @Override
    @Transactional(readOnly = false)
    public TipoProducto createPresentable() {
        return create(true);
    }

    @Override
    public TipoProducto getAny() {
        return getTipoProductoSimple();
    }

    @Override
    public TipoProducto refresh(TipoProducto tipoProducto) {
        if (tipoProducto != null) {
            return tipoProductoService.getTipoProductoById(tipoProducto.getId());
        }
        return tipoProducto;
    }

    private TipoProducto create(boolean presentable) {
        String nombre = "TipoProducto." + RandomStringUtils.randomAlphabetic(5);
        tipoProductoService.createNewTipoProducto(nombre, presentable);
        return tipoProductoService.getTipoProductoByNombre(nombre);
    }

}
