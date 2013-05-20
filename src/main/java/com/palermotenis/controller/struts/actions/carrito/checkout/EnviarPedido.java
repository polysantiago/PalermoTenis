package com.palermotenis.controller.struts.actions.carrito.checkout;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.carrito.Carrito;
import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.controller.struts.actions.carrito.CarritoAction;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.service.carrito.PedidoService;

public class EnviarPedido extends InputStreamActionSupport {

    private static final long serialVersionUID = -8773400739393474328L;
    private static final Logger LOGGER = Logger.getLogger(CarritoAction.class);

    private int pagoId;
    private int cuotas;
    private Carrito carrito;
    private Pedido pedido;

    @Autowired
    private PedidoService pedidoService;

    @Override
    public String execute() {
        try {
            pedido = pedidoService.createNewPedido(pagoId, cuotas, carrito.getTotal());
            pedidoService.send(pedido, carrito);
        } catch (Exception ex) {
            LOGGER.error("Error sending pedido to customer", ex);
            return ERROR;
        }

        return SUCCESS;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
