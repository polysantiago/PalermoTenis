package com.palermotenis.support;

import com.palermotenis.model.beans.Modelo;

public interface TestModeloService extends TestService<Modelo> {

    String MODELO_SIMPLE_NAME = "Modelo_Simple";

    String MODELO_PRESENTABLE_NAME = "Modelo_Presentable";

    String MODELO_CLASIFICABLE_NAME = "Modelo_Clasificable";

    String MODELO_CLASIFICABLE_AND_PRESENTABLE_NAME = "Modelo_Presentable_Clasificable";

    Modelo getModeloSimple();

    Modelo getModeloClasificable();

    Modelo getModeloPresentable();

    Modelo getModeloPresentableAndClasificable();

    Modelo createPadreWithChild();

}
