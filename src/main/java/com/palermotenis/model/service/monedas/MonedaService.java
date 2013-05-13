package com.palermotenis.model.service.monedas;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Moneda;

public interface MonedaService {

    Moneda findByCodigo(String codigo) throws NoResultException;

    List<Moneda> getAllMonedas();

}
