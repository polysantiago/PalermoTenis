package com.palermotenis.model.service.pagos.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.pagos.PagoDao;
import com.palermotenis.model.service.pagos.PagoService;

@Service("pagoService")
@Transactional(propagation = Propagation.REQUIRED)
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoDao pagoDao;

    @Override
    public void createPago(String nombre) {
        Pago pago = new Pago(nombre);
        pagoDao.create(pago);
    }

    @Override
    public void updatePago(Integer pagoId, String nombre) {
        Pago pago = getPagoById(pagoId);

        if (!StringUtils.equals(pago.getNombre(), nombre)) {
            pago.setNombre(nombre);
            pagoDao.edit(pago);
        }
    }

    @Override
    public void deletePago(Integer pagoId) {
        Pago pago = getPagoById(pagoId);
        pagoDao.destroy(pago);
    }

    @Override
    public List<Pago> getAllPagos() {
        return pagoDao.findAll();
    }

    @Override
    public Pago getPagoById(Integer pagoId) {
        return pagoDao.find(pagoId);
    }

    @Override
    public Pago getPagoByNombre(String nombre) throws NoResultException {
        return pagoDao.findBy("Nombre", "nombre", nombre);
    }

    @Override
    public Pago getEfectivo() {
        return getPagoByNombre("Efectivo");
    }

}
