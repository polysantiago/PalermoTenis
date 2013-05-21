package com.palermotenis.model.service.carrito;

import java.util.Map;

import com.palermotenis.model.beans.carrito.Carrito;

public interface CarritoService {

    void add(Carrito carrito, Integer productoId);

    void remove(Carrito carrito, Integer stockId);

    void empty(Carrito carrito);

    void edit(Carrito carrito, Integer cuotas, Integer pagoId);

    void updateCantidades(Carrito carrito, Map<Integer, Integer> stockCantidadMap);

}
