package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.ImmutableList;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoMultipleValores;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoSimple;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoTipado;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.atributos.tipos.TipoAtributoDao;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;
import com.palermotenis.support.TestTipoAtributoService;
import com.palermotenis.support.TestTipoProductoService;

public class TipoAtributoTestData implements MasterData {

    private static final String TIPO_ATRIBUTO_MULTIPLE_VALORES = TestTipoAtributoService.TIPO_ATRIBUTO_MULTIPLE_VALORES;
    private static final String TIPO_ATRIBUTO_PRESENTABLE_CLASIFICATORIO = TestTipoAtributoService.TIPO_ATRIBUTO_PRESENTABLE_CLASIFICATORIO;
    private static final String TIPO_ATRIBUTO_CLASIFICATORIO = TestTipoAtributoService.TIPO_ATRIBUTO_CLASIFICATORIO;
    private static final String TIPO_ATRIBUTO_TIPADO = TestTipoAtributoService.TIPO_ATRIBUTO_TIPADO;
    private static final String TIPO_ATRIBUTO_SIMPLE = TestTipoAtributoService.TIPO_ATRIBUTO_SIMPLE;

    private final TipoAtributoDao tipoAtributoDao;
    private final TipoAtributoService tipoAtributoService;
    private final TestTipoProductoService testTipoProductoService;

    public TipoAtributoTestData(TipoAtributoDao tipoAtributoDao, TipoAtributoService tipoAtributoService,
            TestTipoProductoService testTipoProductoService) {
        this.tipoAtributoDao = tipoAtributoDao;
        this.tipoAtributoService = tipoAtributoService;
        this.testTipoProductoService = testTipoProductoService;
    }

    @Override
    public void createOrUpdate() {
        TipoProducto tipoProductoSimple = testTipoProductoService.getTipoProductoSimple();
        TipoProducto tipoProductoClasificable = testTipoProductoService.getTipoProductoClasificable();
        TipoProducto tipoProductoPresentableAndClasificable = testTipoProductoService
            .getTipoProductoPresentableAndClasificable();

        List<TipoAtributo> tiposAtributosList = new ImmutableList.Builder<TipoAtributo>()
            .add(new TipoAtributoSimple(TIPO_ATRIBUTO_SIMPLE, String.class, tipoProductoSimple))
            .add(new TipoAtributoTipado(TIPO_ATRIBUTO_TIPADO, String.class, tipoProductoSimple))
            .add(new TipoAtributoClasificatorio(TIPO_ATRIBUTO_CLASIFICATORIO, String.class, tipoProductoClasificable))
            .add(
                new TipoAtributoClasificatorio(TIPO_ATRIBUTO_PRESENTABLE_CLASIFICATORIO, String.class,
                    tipoProductoPresentableAndClasificable))
            .add(new TipoAtributoMultipleValores(TIPO_ATRIBUTO_MULTIPLE_VALORES, String.class, tipoProductoSimple))
            .build();

        for (TipoAtributo tipoAtributo : tiposAtributosList) {
            createOrUpdate(tipoAtributo);
        }

    }

    private void createOrUpdate(TipoAtributo tipoAtributo) {
        try {
            TipoAtributo dbInstance = tipoAtributoService.getTipoAtributoByNombre(tipoAtributo.getNombre());
            dbInstance.setTipoProducto(tipoAtributo.getTipoProducto());
            dbInstance.setClase(tipoAtributo.getClase());
            tipoAtributoDao.edit(tipoAtributo);
        } catch (NoResultException ex) {
            tipoAtributoDao.create(tipoAtributo);
        }
    }

}
