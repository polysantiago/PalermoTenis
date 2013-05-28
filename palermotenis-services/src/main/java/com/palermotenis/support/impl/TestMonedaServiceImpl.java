package com.palermotenis.support.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.support.TestMonedaService;

@Service("testMonedaService")
public class TestMonedaServiceImpl implements TestMonedaService {

    private static final String DOLARES = "USD";
    private static final String PESOS = "ARS";
    @Autowired
    private MonedaService monedaService;

    @Override
    public Moneda refresh(Moneda moneda) {
        if (moneda != null) {
            return monedaService.getMonedaById(moneda.getId());
        }
        return moneda;
    }

    @Override
    public Moneda create() {
        Integer contrarioId = getAny().getId();
        String nombre = "TestMoneda";
        String simbolo = "£";
        String codigo = "TST";
        monedaService.createMoneda(contrarioId, nombre, simbolo, codigo);
        return monedaService.getMonedaByCodigo(codigo);
    }

    @Override
    public Moneda getAny() {
        return getPesos();
    }

    @Override
    public Moneda getPesos() {
        return monedaService.getMonedaByCodigo(PESOS);
    }

    @Override
    public Moneda getDolares() {
        return monedaService.getMonedaByCodigo(DOLARES);
    }

}
