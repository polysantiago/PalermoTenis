package com.palermotenis.model.service.monedas;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.model.beans.Moneda;

public interface MonedaService {

    void createMoneda(Integer contrarioId, String nombre, String simbolo, String codigo);

    void updateMoneda(Integer monedaId, Integer contrarioId, String nombre, String simbolo, String codigo);

    void deleteMoneda(Integer monedaId);

    Moneda getMonedaById(Integer monedaId);

    Moneda getMonedaByCodigo(String codigo) throws NoResultException;

    List<Moneda> getAllMonedas();

}
