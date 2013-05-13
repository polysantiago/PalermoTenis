package com.palermotenis.support.impl;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.support.TestTipoAtributoService;
import com.palermotenis.support.TestTipoProductoService;

@Service("testTipoAtributoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TestTipoAtributoServiceImpl implements TestTipoAtributoService {

    @Autowired
    private TipoAtributoService tipoAtributoService;

    @Autowired
    private TipoAtributoDao tipoAtributoDao;

    @Autowired
    private TestTipoProductoService testTipoProductoService;

    @Override
    public TipoAtributo refresh(TipoAtributo tipoAtributo) {
        if (tipoAtributo != null) {
            return tipoAtributoService.getTipoAtributoById(tipoAtributo.getId());
        }
        return tipoAtributo;
    }

    @Override
    @Transactional(readOnly = false)
    public TipoAtributo create() {
        TipoAtributo tipoAtributo = new TipoAtributoSimple(TIPO_ATRIBUTO_SIMPLE, String.class,
            testTipoProductoService.getAny());
        tipoAtributoDao.create(tipoAtributo);
        return tipoAtributoService.getTipoAtributoById(tipoAtributo.getId());
    }

    @Override
    public TipoAtributo getAny() {
        return getTipoAtributoSimple();
    }

    @Override
    public TipoAtributo getTipoAtributoSimple() {
        return getByNombre(TIPO_ATRIBUTO_SIMPLE);
    }

    @Override
    public TipoAtributo getTipoAtributoClasificatorio() {
        return getByNombre(TIPO_ATRIBUTO_CLASIFICATORIO);
    }

    @Override
    public TipoAtributo getTipoAtributoPresentableAndClasificable() {
        return getByNombre(TIPO_ATRIBUTO_PRESENTABLE_CLASIFICATORIO);
    }

    @Override
    public TipoAtributo getTipoAtributoTipado() {
        return getByNombre(TIPO_ATRIBUTO_TIPADO);
    }

    @Override
    public TipoAtributo getTipoAtributoMultipleValores() {
        return getByNombre(TIPO_ATRIBUTO_MULTIPLE_VALORES);
    }

    private TipoAtributo getByNombre(String nombre) {
        return tipoAtributoService.getTipoAtributoByNombre(nombre);
    }

}
