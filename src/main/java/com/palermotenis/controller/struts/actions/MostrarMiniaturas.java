/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;

/**
 * 
 * @author Poly
 */
public class MostrarMiniaturas extends ActionSupport {

    private static final long serialVersionUID = 4048064721829257474L;

    private Integer modeloId;
    private Integer tipoProductoId;
    private Integer marcaId;

    private Collection<Modelo> modelos;

    @Autowired
    private GenericDao<Modelo, Integer> modeloDao;

    @Autowired
    private GenericDao<Marca, Integer> marcaDao;

    @Autowired
    private GenericDao<TipoProducto, Integer> tipoProductoDao;

    public String showByPadre() {
        Modelo m = modeloDao.find(modeloId);
        modelos = modeloDao.queryBy("Padre", new ImmutableMap.Builder<String, Object>().put("padre", m).build());
        return SUCCESS;
    }

    public String showByMarca() {
        modelos = modeloDao.queryBy(
            "Marca,TipoProducto-Leafs",
            new ImmutableMap.Builder<String, Object>()
                .put("marca", marcaDao.find(marcaId))
                .put("tipoProducto", tipoProductoDao.find(tipoProductoId))
                .build());
        return SUCCESS;
    }

    /**
     * @param modeloId
     *            the modeloId to set
     */
    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    /**
     * @return the modelos
     */
    public Collection<Modelo> getModelos() {
        return modelos;
    }

    /**
     * @param tipoProductoId
     *            the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param marcaId
     *            the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

}
