package com.palermotenis.model.service.pagos;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Pago;

public interface PagoService {

    void createPago(String nombre);

    void updatePago(Integer pagoId, String nombre);

    void deletePago(Integer pagoId);

    Pago getPagoById(Integer pagoId);

    Pago getPagoByNombre(String nombre) throws NoResultException;

    Pago getEfectivo();

    List<Pago> getAllPagos();

}
