package com.palermotenis.support.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;
import com.palermotenis.support.TestTipoPresentacionService;
import com.palermotenis.support.TestTipoProductoService;

@Service("testTipoPresentacionService")
public class TestTipoPresentacionServiceImpl implements TestTipoPresentacionService {

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Override
    public TipoPresentacion refresh(TipoPresentacion tipoPresentacion) {
        if (tipoPresentacion != null) {
            return tipoPresentacionService.getTipoPresentacionById(tipoPresentacion.getId());
        }
        return tipoPresentacion;
    }

    @Override
    @Transactional
    public TipoPresentacion create() {
        TipoProducto tipoProducto = testTipoProductoService.getAny();

        List<TipoPresentacion> presentacionByTipoProducto = getByTipoProducto(tipoProducto);
        if (CollectionUtils.isNotEmpty(presentacionByTipoProducto)) {
            return presentacionByTipoProducto.get(0);
        }

        tipoPresentacionService.createNewTipoPresentacion(tipoProducto.getId(), TEST_TIPO_PRESENTACION);
        return getByTipoProducto(tipoProducto).get(0);
    }

    private List<TipoPresentacion> getByTipoProducto(TipoProducto tipoProducto) {
        return tipoPresentacionService.getTiposPresentacionByTipoProducto(tipoProducto.getId());
    }

    @Override
    public TipoPresentacion getAny() {
        return create();
    }

}
