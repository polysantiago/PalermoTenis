package com.palermotenis.model.service.carrito;

import java.util.List;

import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.usuarios.Usuario;

public interface PedidoService {

    Pedido create(Cliente cliente, Integer pagoId, Integer cuotas, Double total);

    void send(Usuario usuario, Pedido pedido, Carrito carrito);

    List<Pedido> getAllPedidos();

    List<Pedido> getPedidosByEmail(String email);

    List<Pedido> getPedidosByNombreCliente(String nombreCliente);

}
