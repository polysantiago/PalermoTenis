package com.palermotenis.controller.struts.actions.admin.ventas;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class PedidoAction extends JsonActionSupport {

    private static final long serialVersionUID = 5458521843286152938L;

    private Integer pedidoId;

    @Autowired
    private Dao<Pedido, Integer> pedidoDao;

    public String destroy() {
        try {
            pedidoDao.destroy(pedidoDao.find(pedidoId));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    /**
     * @param pedidoId
     *            the pedidoId to set
     */
    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

}
