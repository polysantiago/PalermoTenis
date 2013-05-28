package com.palermotenis.support;

import com.palermotenis.model.beans.productos.tipos.TipoProducto;

public interface TestTipoProductoService extends TestService<TipoProducto> {

    String TIPO_PRODUCTO_SIMPLE_NAME = "TipoProducto_Simple";

    String TIPO_PRODUCTO_PRESENTABLE_NAME = "TipoProducto_Presentable";

    String TIPO_PRODUCTO_CLASIFICABLE_NAME = "TipoProducto_Clasificable";

    String TIPO_PRODUCTO_CLASIFICABLE_AND_PRESENTABLE_NAME = "TipoProducto_Presentable_Clasificable";

    TipoProducto createPresentable();

    TipoProducto getTipoProductoSimple();

    TipoProducto getTipoProductoClasificable();

    TipoProducto getTipoProductoPresentable();

    TipoProducto getTipoProductoPresentableAndClasificable();

}
