package com.palermotenis.commandline.setup;

import java.util.List;

import javax.persistence.NoResultException;

import com.google.common.collect.Lists;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.pagos.PagoDao;
import com.palermotenis.model.service.pagos.PagoService;

public class PagoMasterData implements MasterData {

    private static final Pago EFECTIVO = new Pago("Efectivo");
    private static final Pago TARJETA_CREDITO = new Pago("Tarj de Débito / Crédito");
    private static final Pago TARJETA_CREDITO_CUOTAS = new Pago("Tarjeta Crédito Cuotas");

    private static final List<Pago> ALL_PAGOS = Lists.newArrayList();

    static {
        ALL_PAGOS.add(EFECTIVO);
        ALL_PAGOS.add(TARJETA_CREDITO);
        ALL_PAGOS.add(TARJETA_CREDITO_CUOTAS);
    }

    private final PagoService pagoService;
    private final PagoDao pagoDao;

    public PagoMasterData(PagoService pagoService, PagoDao pagoDao) {
        this.pagoService = pagoService;
        this.pagoDao = pagoDao;
    }

    @Override
    public void createOrUpdate() {
        for (Pago pago : ALL_PAGOS) {
            try {
                pagoService.getPagoByNombre(pago.getNombre());
            } catch (NoResultException ex) {
                pagoDao.create(pago);
            }
        }
    }

}
