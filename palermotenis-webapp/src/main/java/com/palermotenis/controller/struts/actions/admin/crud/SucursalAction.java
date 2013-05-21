package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.beans.Sucursal;
import com.palermotenis.model.service.sucursales.SucursalService;

public class SucursalAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -6943240819837303040L;

    private static final String DEL = "del";
    private static final String ADD = "add";
    private static final String EDIT = "edit";

    private String id; // No es int porque puede ser '_empty'
    private String nombre;
    private String direccion;
    private String telefono;
    private Integer rows;
    private Integer page;
    private String oper;
    private Collection<Sucursal> sucursales;

    @Autowired
    private SucursalService sucursalService;

    public String crud() {
        if (oper.equalsIgnoreCase(EDIT)) {
            return edit();
        } else if (oper.equalsIgnoreCase(ADD)) {
            return create();
        } else if (oper.equalsIgnoreCase(DEL)) {
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
                Sucursal sucursal = (Sucursal) bean;
                JSONObject row = new JSONObject();
                row.element("id", sucursal.getId());

                JSONArray cell = new JSONArray();
                cell.add(sucursal.getNombre());
                cell.add(sucursal.getDireccion());
                cell.add(sucursal.getTelefono());

                row.element("cell", cell);

                return row;
            }
        });

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
        try {
            sucursalService.createNewSucursal(nombre, direccion, telefono);
            success();
        } catch (HibernateException ex) {
            try {
                failure(ex);
            } catch (Exception e) {
                failure(ex);
            }
        }
        return JSON;
    }

    public String edit() {
        try {
            sucursalService.updateSucursal(Integer.parseInt(id), nombre, direccion, telefono);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            sucursalService.deleteSucursal(Integer.parseInt(id));
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public Collection<Sucursal> getSucursales() {
        if (sucursales == null) {
            sucursales = sucursalService.getAllSucursales();
        }
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
