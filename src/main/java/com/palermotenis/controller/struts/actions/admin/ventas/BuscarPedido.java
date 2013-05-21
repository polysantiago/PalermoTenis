package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.service.carrito.PedidoService;

public class BuscarPedido extends InputStreamActionSupport {

    private static final long serialVersionUID = -12350657411437334L;

    private char filter;
    private String searchVal;

    @Autowired
    private PedidoService pedidoService;

    private List<Pedido> pedidos;

    @Override
    public String execute() {
        if (searchVal == null || searchVal.isEmpty()) {
            buscarTodos();
        } else {
            switch (filter) {
            case 'E':
                buscarXEmail();
                break;
            case 'N':
                buscarXNombre();
                break;
            default:
                return ERROR;
            }
        }
        return SUCCESS;
    }

    private void buscarXEmail() {
        pedidos = pedidoService.getPedidosByEmail("%" + searchVal + "%");
    }

    private void buscarXNombre() {
        pedidos = Lists.newArrayList();
        for (String nombreCliente : searchVal.trim().split(" ")) {
            pedidos.addAll(pedidoService.getPedidosByNombreCliente("%" + nombreCliente + "%"));
        }
    }

    private void buscarTodos() {
        pedidos = pedidoService.getAllPedidos();
    }

    public void setFilter(char filter) {
        this.filter = filter;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

}
