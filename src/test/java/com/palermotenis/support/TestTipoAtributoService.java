package com.palermotenis.support;

import com.palermotenis.model.beans.atributos.tipos.TipoAtributo;

public interface TestTipoAtributoService extends TestService<TipoAtributo> {

    String TIPO_ATRIBUTO_MULTIPLE_VALORES = "TipoAtributo_MultipleValores";
    String TIPO_ATRIBUTO_PRESENTABLE_CLASIFICATORIO = "TipoAtributo_Presentable_Clasificatorio";
    String TIPO_ATRIBUTO_CLASIFICATORIO = "TipoAtributo_Clasificatorio";
    String TIPO_ATRIBUTO_TIPADO = "TipoAtributo_Tipado";
    String TIPO_ATRIBUTO_SIMPLE = "TipoAtributo_Simple";

    TipoAtributo getTipoAtributoSimple();

    TipoAtributo getTipoAtributoClasificatorio();

    TipoAtributo getTipoAtributoPresentableAndClasificable();

    TipoAtributo getTipoAtributoTipado();

    TipoAtributo getTipoAtributoMultipleValores();

}
