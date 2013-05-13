package com.palermotenis.model.service.productos.tipos.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.productos.tipos.TipoProductoDao;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;

@Service("tipoProductoService")
@Transactional(propagation = Propagation.REQUIRED, noRollbackFor =
    { NoResultException.class, EntityNotFoundException.class })
public class TipoProductoServiceImpl implements TipoProductoService {

    @Autowired
    private TipoProductoDao tipoProductoDao;

    @Override
    public void createNewTipoProducto(String nombre, boolean presentable) {
        Assert.notNull(nombre, "Nombre cannot be null");
        TipoProducto tipoProducto = new TipoProducto(nombre, presentable);
        tipoProductoDao.create(tipoProducto);
    }

    @Override
    public void updateTipoProducto(Integer tipoProductoId, String nombre, boolean presentable) {
        Assert.notNull(tipoProductoId, "tipoProductoId cannot be null");
        Assert.notNull(nombre, "Nombre cannot be null");

        TipoProducto tipoProducto = getTipoProductoById(tipoProductoId);
        if (!StringUtils.equals(nombre, tipoProducto.getNombre())) {
            tipoProducto.setNombre(nombre);
        }
        tipoProducto.setPresentable(presentable);
        tipoProductoDao.edit(tipoProducto);
    }

    @Override
    public void deleteTipoProducto(Integer tipoProductoId) {
        TipoProducto tipoProducto = getTipoProductoById(tipoProductoId);
        tipoProductoDao.destroy(tipoProducto);
    }

    @Override
    public TipoProducto getTipoProductoById(Integer tipoProductoId) {
        return tipoProductoDao.find(tipoProductoId);
    }

    @Override
    public TipoProducto getTipoProductoByNombre(String nombre) throws NoResultException {
        return tipoProductoDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public List<TipoProducto> getAllTipoProducto() {
        return tipoProductoDao.findAll();
    }

    @Override
    public List<TipoProducto> getAllTiposProductoPresentables() {
        return Lists.newArrayList(Iterables.filter(getAllTipoProducto(), new Predicate<TipoProducto>() {
            @Override
            public boolean apply(@Nullable TipoProducto tipoProducto) {
                return tipoProducto.isPresentable();
            }
        }));
    }

    @Override
    public List<TipoProducto> getRootsTipoProducto() {
        return tipoProductoDao.query("Childless");
    }

    @Override
    public Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> getTiposProductoAndMarcasAndProductoCount() {
        List<Object[]> resultList = tipoProductoDao.getTiposProductoAndMarcasAndProductoCount();

        Map<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>> resultMap = new LinkedHashMap<TipoProducto, ArrayList<LinkedHashMap<Marca, Long>>>();
        for (Object[] resultObject : resultList) {
            TipoProducto tipoProducto = (TipoProducto) resultObject[0];
            Marca marca = (Marca) resultObject[1];
            Long productoCount = (Long) resultObject[2];

            ArrayList<LinkedHashMap<Marca, Long>> arrayList;
            if (!resultMap.containsKey(tipoProducto)) {
                arrayList = new ArrayList<LinkedHashMap<Marca, Long>>();
            } else {
                arrayList = resultMap.get(tipoProducto);
            }

            LinkedHashMap<Marca, Long> hashMap = new LinkedHashMap<Marca, Long>();
            hashMap.put(marca, productoCount);
            arrayList.add(hashMap);

            if (!resultMap.containsKey(tipoProducto)) {
                resultMap.put(tipoProducto, arrayList);
            }
        }
        return resultMap;
    }

}
