/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.ventas;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.pedidos.Pedido;

/**
 * 
 * @author Poly
 */
public class BuscarPedido extends JsonActionSupport {

    private static final long serialVersionUID = -12350657411437334L;

    private static final JsonConfig CONFIG = new JsonConfig();

    private char filter;
    private String searchVal;

    @Autowired
    private GenericDao<Pedido, Integer> pedidoDao;

    static {
        CONFIG.registerJsonBeanProcessor(Pedido.class, new JsonBeanProcessor() {

            @Override
            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Pedido p = (Pedido) bean;
                JSONObject o = new JSONObject();
                JsonConfig clienteConfig = new JsonConfig();
                clienteConfig.setExcludes(new String[]
                    { "pedidos", "usuario", "ventas" });
                clienteConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

                o.element("id", p.getId());
                o.element("total", p.getTotal());
                o.element("fecha", p.getFecha());
                o.element("pago", JSONSerializer.toJSON(p.getPago()));
                o.element("cuotas", p.getCuotas());
                o.element("cliente", JSONSerializer.toJSON(p.getCliente(), clienteConfig));
                return o;
            }
        });
    }

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
        printResult(pedidoDao.queryBy("Email",
            new ImmutableMap.Builder<String, Object>().put("email", "%" + searchVal + "%").build()));
    }

    private void buscarXNombre() {
        List<Pedido> pedidos = new ArrayList<Pedido>();
        for (String s : searchVal.trim().split(" ")) {
            pedidos.addAll(pedidoDao.queryBy("Nombre",
                new ImmutableMap.Builder<String, Object>().put("nombre", "%" + s + "%").build()));
        }
        printResult(pedidos);
    }

    private void buscarTodos() {
        printResult(pedidoDao.findAll());
    }

    private void printResult(List<Pedido> pedidos) {
        writeResponse(JSONSerializer.toJSON(pedidos, CONFIG).toString());
    }

    /**
     * @param filter
     *            the filter to set
     */
    public void setFilter(char filter) {
        this.filter = filter;
    }

    /**
     * @param searchVal
     *            the searchVal to set
     */
    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    /**
     * @param pedidoService
     *            the pedidosService to set
     */
    public void setPedidoService(GenericDao<Pedido, Integer> pedidoService) {
        this.pedidoDao = pedidoService;
    }
}
