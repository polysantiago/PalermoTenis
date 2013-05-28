package com.palermotenis.model.service.carrito;

import java.util.List;

import com.palermotenis.model.beans.carrito.Carrito;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.beans.pedidos.PedidoProducto;
import com.palermotenis.model.beans.usuarios.Usuario;

public interface PedidoService {

    void send(Usuario usuario, Pedido pedido, Carrito carrito);

    void delete(Integer pedidoId);

    void onConfirmed(Integer pedidoId);

    Pedido addItem(Integer pedidoId, Integer stockId);

    Pedido create(Cliente cliente, Integer pagoId, Integer cuotas, Double total);

    Pedido removeItem(Integer pedidoId, Integer stockId);

    Pedido getPedidoById(Integer pedidoId);

    List<Pedido> getAllPedidos();

    List<Pedido> getPedidosByEmail(String email);

    List<Pedido> getPedidosByNombreCliente(String nombreCliente);

    PedidoProducto updateQuantity(Integer pedidoId, Integer stockId, Integer cantidad);

    PedidoProducto updateStock(Integer pedidoId, Integer stockId, Integer newStockId);

    List<PedidoProducto> getDetails(Integer pedidoId);

}
