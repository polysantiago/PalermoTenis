/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.admin.ventas;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.pedidos.Pedido;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class PedidoAction extends ActionSupport {

    private Integer pedidoId;
    private GenericDao<Pedido, Integer> pedidoService;
    private InputStream inputStream;

    public String destroy(){
        try {
            pedidoService.destroy(pedidoService.find(pedidoId));
            createJSON(SUCCESS, "OK");
        } catch (HibernateException ex) {
            createJSON(ERROR, ex.getLocalizedMessage());
            Logger.getLogger(PedidoAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "json";
    }

    private void createJSON(String result, String msg){
        JSONObject jo = new JSONObject();
        jo.element("result", result);
        jo.element("msg", msg);
        inputStream = StringUtility.getInputString(jo.toString());
    }

    /**
     * @param pedidoId the pedidoId to set
     */
    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    /**
     * @param pedidoService the pedidoService to set
     */
    public void setPedidoService(GenericDao<Pedido, Integer> pedidoService) {
        this.pedidoService = pedidoService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

}
