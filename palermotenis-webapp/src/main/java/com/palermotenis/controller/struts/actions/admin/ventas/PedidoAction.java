package com.palermotenis.controller.struts.actions.admin.ventas;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.service.carrito.PedidoService;

public class PedidoAction extends InputStreamActionSupport {

    private static final long serialVersionUID = 5458521843286152938L;

    private Integer pedidoId;

    @Autowired
    private PedidoService pedidoService;

    public String destroy() {
        try {
            pedidoService.delete(pedidoId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

}
