package com.palermotenis.model.service.carrito;

import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.model.beans.pedidos.Pedido;

public interface PedidoService {

    Pedido createNewPedido(Integer pagoId, Integer cuotas, Double total);

    void send(Pedido pedido, Carrito carrito);

}
