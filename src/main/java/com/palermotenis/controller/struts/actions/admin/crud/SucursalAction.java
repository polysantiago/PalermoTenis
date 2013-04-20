package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
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

/**
 * 
 * @author poly
 */
public class SucursalAction extends JsonActionSupport {

    private static final long serialVersionUID = -6943240819837303040L;

    private final static String SHOW = "show";

    private String id; // No es int porque puede ser '_empty'
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer rows;
    private Integer page;
    private String oper;
    private Collection<Sucursal> sucursales;

    @Autowired
    private GenericDao<Sucursal, Integer> sucursalDao;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    @Autowired
    private GenericDao<ValorClasificatorio, Integer> valorClasificatorioDao;

    @Autowired
    private GenericDao<Presentacion, Integer> presentacionDao;

    @Autowired
    private GenericDao<Stock, Integer> stockDao;

    public String crud() {
        if (oper.equalsIgnoreCase("edit")) {
            return edit();
        } else if (oper.equalsIgnoreCase("add")) {
            return create();
        } else if (oper.equalsIgnoreCase("del")) {
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

            @Override
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

        sucursales = sucursalDao.findAll();

        JSONObject rootObj = new JSONObject();
        JSONArray jrows = (JSONArray) JSONSerializer.toJSON(sucursales, jsonConfig);

        rootObj.element("rows", jrows);
        rootObj.element("records", sucursales.size());
        rootObj.element("total", Math.ceil((double) sucursales.size() / rows));
        rootObj.element("page", page);

        writeResponse(rootObj);
        return JSON;
    }

    public String create() {
        Sucursal sucursal = new Sucursal(nombre);
        sucursal.setDireccion(direccion);
        sucursal.setTelefono(telefono);
        try {
            sucursalDao.create(sucursal);
            List<Sucursal> ss = new ImmutableList.Builder<Sucursal>().add(sucursal).build();
            for (TipoProducto tp : tipoProductoDao.findAll()) {
                for (Producto p : tp.getProductos()) {
                    List<ValorClasificatorio> vc = null;
                    List<Presentacion> prs = null;

                    State state = new DefaultState(p, ss);

                    if (tp.isClasificable()) {
                        vc = valorClasificatorioDao.queryBy("TipoAtributoList", "tipoAtributoList",
                            tp.getTiposAtributoClasificatorios());
                    }

                    if (tp.isPresentable()) {
                        prs = presentacionDao.queryBy("TipoList", "tipoList", tp.getTiposPresentacion());
                    }

                    if (tp.isClasificable() && tp.isPresentable()) {
                        state = new PresentableAndClasificableState(p, ss, vc, prs);
                    } else if (tp.isClasificable()) {
                        state = new ClasificableState(p, ss, vc);
                    } else if (tp.isPresentable()) {
                        state = new PresentableState(p, ss, prs);
                    }
                    state.setStockDao(stockDao);
                    state.createStock();
                }
            }

            success();
        } catch (HibernateException ex) {
            try {
                sucursalDao.destroy(sucursal);
                failure(ex);
            } catch (Exception e) {
                failure(ex);
            }
        }
        return JSON;
    }

    public String edit() {
        Sucursal sucursal = sucursalDao.find(Integer.parseInt(id));
        sucursal.setNombre(nombre);
        if (direccion != null) {
            sucursal.setDireccion(direccion);
        }
        if (telefono != null) {
            sucursal.setTelefono(telefono);
        }
        try {
            sucursalDao.edit(sucursal);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            sucursalDao.destroy(sucursalDao.find(Integer.parseInt(id)));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
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

}
