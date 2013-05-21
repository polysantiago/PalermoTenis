package com.palermotenis.support.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.clientes.Cliente;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.dao.pedidos.PedidoDao;
import com.palermotenis.model.service.carrito.PedidoService;
import com.palermotenis.support.TestClienteService;
import com.palermotenis.support.TestPagoService;
import com.palermotenis.support.TestPedidoService;

@Service("testPedidoService")
public class TestPedidoServiceImpl implements TestPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private TestClienteService testClienteService;

    @Autowired
    private TestPagoService testPagoService;

    @Autowired
    private PedidoDao pedidoDao;

    @Override
    public Pedido refresh(Pedido pedido) {
        if (pedido != null) {
            return pedidoDao.find(pedido.getId());
        }
        return pedido;
    }

    @Override
    @Transactional
    public Pedido create() {
        Cliente cliente = testClienteService.create();
        Pago pago = testPagoService.getPagoEfectivo();
        Integer cuotas = 1;
        Double total = 123.4;
        return pedidoService.create(cliente, pago.getId(), cuotas, total);
    }

    @Override
    public Pedido getAny() {
        // TODO Auto-generated method stub
        return null;
    }

}
