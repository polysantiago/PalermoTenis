package com.palermotenis.support.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.support.TestPagoService;

@Service("testPagoService")
public class TestPagoServiceImpl implements TestPagoService {

    @Autowired
    private PagoService pagoService;

    @Override
    public Pago refresh(Pago pago) {
        if (pago != null) {
            return pagoService.getPagoById(pago.getId());
        }
        return pago;
    }

    @Override
    @Transactional
    public Pago create() {
        Pago pago = getByNombre(TEST_PAGO);
        if (pago == null) {
            pagoService.createPago(TEST_PAGO);
            return getByNombre(TEST_PAGO);
        }
        return pago;
    }

    @Override
    public Pago getAny() {
        return getPagoEfectivo();
    }

    @Override
    public Pago getPagoEfectivo() {
        return getByNombre("Efectivo");
    }

    @Override
    public Pago getPagoTarjetCredito() {
        return getByNombre("Tarj de Débito / Crédito");
    }

    @Override
    public Pago getPagoTarjetaCreditoCuotas() {
        return getByNombre("Tarjeta Crédito Cuotas");
    }

    private Pago getByNombre(String nombre) {
        return pagoService.getPagoByNombre(nombre);
    }

}
