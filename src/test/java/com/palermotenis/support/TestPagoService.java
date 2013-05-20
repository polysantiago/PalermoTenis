package com.palermotenis.support;

import com.palermotenis.model.beans.Pago;

public interface TestPagoService extends TestService<Pago> {

    String TEST_PAGO = "TestPago";

    Pago getPagoEfectivo();

    Pago getPagoTarjetCredito();

    Pago getPagoTarjetaCreditoCuotas();

}
