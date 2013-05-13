package com.palermotenis.model.service.presentaciones.tipos;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.presentaciones.tipos.TipoPresentacionDao;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

@Service("tipoPresentacionService")
@Transactional(propagation = Propagation.REQUIRED)
public class TipoPresentacionServiceImpl implements TipoPresentacionService {

    @Autowired
    private TipoPresentacionDao tipoPresentacionDao;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Override
    public TipoPresentacion getTipoPresentacionById(Integer tipoPresentacionId) {
        return tipoPresentacionDao.find(tipoPresentacionId);
    }

    @Override
    public Collection<TipoPresentacion> getTiposPresentacionByTipoProducto(Integer tipoProductoId) {
        TipoProducto tipoProducto = tipoProductoService.getTipoProductoById(tipoProductoId);
        return tipoPresentacionDao.queryBy("TipoProducto", "tipoProducto", tipoProducto);
    }

}
