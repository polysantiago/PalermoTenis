package com.palermotenis.model.service.ventas;

import java.util.Map;

import com.palermotenis.model.beans.usuarios.Usuario;
import com.palermotenis.model.beans.ventas.Listado;
import com.palermotenis.model.exceptions.NullPriceException;

public interface VentaService {

    void confirmOrder(Integer pedidoId, Usuario usuario);

    void confirmSale(String listadoId, Usuario usuario) throws NullPriceException;

    void onConfirmed(Listado listado);

    Listado authorizeListing(String username, String password, String listadoId);

    Listado registerNewPurchase(Integer pagoId, Integer cuotas, Integer clienteId, Map<Integer, Integer> stocks);

    Listado getListadoById(String listadoId);

    Listado updateListingQuantities(String listadoId, Map<Integer, Integer> quantities);

    Listado updateListingPrices(String listadoId, Map<Integer, Double> prices, boolean authorized);

}
