package com.palermotenis.model.service.pagos;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Pago;

public interface PagoService {

    Pago getPagoById(Integer pagoId);

    Pago getPagoByNombre(String nombre) throws NoResultException;

    Pago getEfectivo();

    List<Pago> getAllPagos();

}
