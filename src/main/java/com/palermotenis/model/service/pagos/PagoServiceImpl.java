package com.palermotenis.model.service.pagos;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.pagos.PagoDao;

@Service("pagoService")
@Transactional(propagation = Propagation.REQUIRED)
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoDao pagoDao;

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
