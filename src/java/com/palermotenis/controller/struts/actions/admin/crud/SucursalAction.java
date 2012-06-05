/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.google.common.collect.ImmutableList;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Stock;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import com.palermotenis.model.beans.productos.tipos.ClasificableState;
import com.palermotenis.model.beans.productos.tipos.DefaultState;
import com.palermotenis.model.beans.productos.tipos.PresentableAndClasificableState;
import com.palermotenis.model.beans.productos.tipos.PresentableState;
import com.palermotenis.model.beans.productos.tipos.State;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.valores.ValorClasificatorio;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import org.hibernate.HibernateException;

/**
 *
 * @author poly
 */
public class SucursalAction extends ActionSupport {

    private final static String CREATE = "create";
    private final static String EDIT = "edit";
    private final static String DESTROY = "destroy";
    private final static String SHOW = "show";
    private final static String JSON = "json";
    private GenericDao<Sucursal, Integer> sucursalService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private GenericDao<Stock, Integer> stockService;
    private String id; //No es int porque puede ser '_empty'
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer rows;
    private Integer page;
    private String oper;
    private Collection<Sucursal> sucursales;
    private InputStream inputStream;

    public String crud(){
        if(oper.equalsIgnoreCase("edit")){
            return edit();
        } else if(oper.equalsIgnoreCase("add")){
            return create();
        } else if(oper.equalsIgnoreCase("del")){
            return destroy();
        }
        return SHOW;
    }

    public String show() {
        return SHOW;
    }

    public String showJson() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonBeanProcessor(Sucursal.class, new JsonBeanProcessor() {

            public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
                Sucursal s = (Sucursal) bean;
                JSONObject row = new JSONObject();
                row.element("id", s.getId());

                JSONArray cell = new JSONArray();
                cell.add(s.getNombre());
                cell.add(s.getDireccion());
                cell.add(s.getTelefono());

                row.element("cell", cell);

                return row;
            }
        });

        sucursales = sucursalService.findAll();

        JSONObject rootObj = new JSONObject();
        JSONArray jrows = (JSONArray) JSONSerializer.toJSON(sucursales, jsonConfig);

        rootObj.element("rows", jrows);
        rootObj.element("records", sucursales.size());
        rootObj.element("total", Math.ceil((double) sucursales.size() / rows));
        rootObj.element("page", page);

        inputStream = StringUtility.getInputString(rootObj.toString());
        return JSON;
    }

    public String create() {
        Sucursal sucursal = new Sucursal(nombre);
        sucursal.setDireccion(direccion);
        sucursal.setTelefono(telefono);
        try {
            sucursalService.create(sucursal);
            List<Sucursal> ss = new ImmutableList.Builder<Sucursal>().add(sucursal).build();
            for (TipoProducto tp : tipoProductoService.findAll()) {
                for (Producto p : tp.getProductos()) {
                    List<ValorClasificatorio> vc = null;
                    List<Presentacion> prs = null;

                    State state = new DefaultState(p, ss);

                    if (tp.isClasificable()) {
                        vc = valorClasificatorioService.queryBy("TipoAtributoList","tipoAtributoList", tp.getTiposAtributoClasificatorios());
                    }

                    if (tp.isPresentable()) {
                        prs = presentacionService.queryBy("TipoList","tipoList", tp.getTiposPresentacion());
                    }

                    if (tp.isClasificable() && tp.isPresentable()) {
                        state = new PresentableAndClasificableState(p, ss, vc, prs);
                    } else if (tp.isClasificable()) {
                        state = new ClasificableState(p, ss, vc);
                    } else if (tp.isPresentable()) {
                        state = new PresentableState(p, ss, prs);
                    }
                    state.setStockService(stockService);
                    state.createStock();
                }
            }

            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            try {
                sucursalService.destroy(sucursal);
                inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
            } catch (Exception e){
                inputStream = StringUtility.getInputString(e.getLocalizedMessage());
            }
        }
        return JSON;
    }

    public String edit() {
        Sucursal sucursal = sucursalService.find(Integer.parseInt(id));
        sucursal.setNombre(nombre);
        if (direccion != null) {
            sucursal.setDireccion(direccion);
        }
        if (telefono != null) {
            sucursal.setTelefono(telefono);
        }
        try {
            sucursalService.edit(sucursal);
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public String destroy() {
        try {
            sucursalService.destroy(sucursalService.find(Integer.parseInt(id)));
            inputStream = StringUtility.getInputString("OK");
        } catch (HibernateException ex) {
            inputStream = StringUtility.getInputString(ex.getLocalizedMessage());
        }
        return JSON;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Collection<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSucursalService(GenericDao<Sucursal, Integer> sucursalService) {
        this.sucursalService = sucursalService;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    public void setStockService(GenericDao<Stock, Integer> stockService) {
        this.stockService = stockService;
    }

    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    public void setValorClasificatorioService(GenericDao<ValorClasificatorio, Integer> valorClasificatorioService) {
        this.valorClasificatorioService = valorClasificatorioService;
    }
}
