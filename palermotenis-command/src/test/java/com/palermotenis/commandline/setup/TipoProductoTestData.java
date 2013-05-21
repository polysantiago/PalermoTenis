package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.service.productos.tipos.TipoProductoService;
import com.palermotenis.support.TestTipoProductoService;

public class TipoProductoTestData implements MasterData {

    private static final TipoProducto SIMPLE = new TipoProducto(TestTipoProductoService.TIPO_PRODUCTO_SIMPLE_NAME,
        false);
    private static final TipoProducto CLASIFICABLE = new TipoProducto(
        TestTipoProductoService.TIPO_PRODUCTO_CLASIFICABLE_NAME, false);
    private static final TipoProducto PRESENTABLE = new TipoProducto(
        TestTipoProductoService.TIPO_PRODUCTO_PRESENTABLE_NAME, true);
    private static final TipoProducto PRESENTABLE_CLASIFICABLE = new TipoProducto(
        TestTipoProductoService.TIPO_PRODUCTO_CLASIFICABLE_AND_PRESENTABLE_NAME, true);

    private static final List<TipoProducto> ALL_TIPOS_PRODUCTO = Lists.newArrayList();

    static {
        ALL_TIPOS_PRODUCTO.add(SIMPLE);
        ALL_TIPOS_PRODUCTO.add(CLASIFICABLE);
        ALL_TIPOS_PRODUCTO.add(PRESENTABLE);
        ALL_TIPOS_PRODUCTO.add(PRESENTABLE_CLASIFICABLE);
    }

    private final TipoProductoService tipoProductoService;

    public TipoProductoTestData(TipoProductoService tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    @Override
    public void createOrUpdate() {
        for (TipoProducto tipoProducto : ALL_TIPOS_PRODUCTO) {
            try {
                tipoProductoService.getTipoProductoByNombre(tipoProducto.getNombre());
            } catch (NoResultException ex) {
                tipoProductoService.createNewTipoProducto(tipoProducto.getNombre(), tipoProducto.isPresentable());
            }
        }
    }

}
