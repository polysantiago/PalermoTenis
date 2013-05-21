package com.palermotenis.model.service.presentaciones.tipos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.presentaciones.tipos.TipoPresentacionDao;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

@Service("tipoPresentacionService")
@Transactional(propagation = Propagation.REQUIRED)
public class TipoPresentacionServiceImpl implements TipoPresentacionService {

    @Autowired
    private TipoPresentacionDao tipoPresentacionDao;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Override
    public void createNewTipoPresentacion(Integer tipoProductoId, String nombre) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        TipoPresentacion tipoPresentacion = new TipoPresentacion(nombre, tipoProducto);
        tipoPresentacionDao.create(tipoPresentacion);
    }

    @Override
    public void updateTipoPresentacion(Integer tipoPresentacionId, String nombre) {
        TipoPresentacion tipoPresentacion = getTipoPresentacionById(tipoPresentacionId);
        tipoPresentacion.setNombre(nombre);
        tipoPresentacionDao.edit(tipoPresentacion);
    }

    @Override
    public void deleteTipoPresentacion(Integer tipoPresentacionId) {
        TipoPresentacion tipoPresentacion = getTipoPresentacionById(tipoPresentacionId);
        tipoPresentacionDao.destroy(tipoPresentacion);
    }

    @Override
    public TipoPresentacion getTipoPresentacionById(Integer tipoPresentacionId) {
        return tipoPresentacionDao.find(tipoPresentacionId);
    }

    @Override
    public List<TipoPresentacion> getAllTiposPresentacion() {
        return tipoPresentacionDao.findAll();
    }

    @Override
    public List<TipoPresentacion> getTiposPresentacionByTipoProducto(Integer tipoProductoId) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        return tipoPresentacionDao.queryBy("TipoProducto", "tipoProducto", tipoProducto);
    }

}
