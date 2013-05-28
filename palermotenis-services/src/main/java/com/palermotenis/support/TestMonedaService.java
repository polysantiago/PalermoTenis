package com.palermotenis.support;

import com.palermotenis.model.beans.Moneda;

public interface TestMonedaService extends TestService<Moneda> {

    Moneda getPesos();

    Moneda getDolares();

}
