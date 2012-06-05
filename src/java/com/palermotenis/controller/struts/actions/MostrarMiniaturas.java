/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Marca;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import java.util.Collection;

/**
 *
 * @author Poly
 */
public class MostrarMiniaturas extends ActionSupport {

    private GenericDao<Modelo, Integer> modeloService;
    private GenericDao<Marca, Integer> marcaService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;
    private Integer modeloId;
    private Integer tipoProductoId;
    private Integer marcaId;
    private Collection<Modelo> modelos;
    
    public String showByPadre(){
        Modelo m = modeloService.find(modeloId);
        modelos = modeloService.queryBy("Padre", new ImmutableMap.Builder<String, Object>().put("padre", m).build());
        return SUCCESS;
    }

    public String showByMarca(){
        modelos = modeloService.queryBy("Marca,TipoProducto-Leafs",
                new ImmutableMap.Builder<String, Object>()
                .put("marca", marcaService.find(marcaId))
                .put("tipoProducto", tipoProductoService.find(tipoProductoId))
                .build());
        return SUCCESS;
    }

    /**
     * @param modeloService the modelosService to set
     */
    public void setModeloService(GenericDao<Modelo, Integer> modeloService) {
        this.modeloService = modeloService;
    }

    public void setMarcaService(GenericDao<Marca, Integer> marcaService) {
        this.marcaService = marcaService;
    }

    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @param modeloId the modeloId to set
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
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    /**
     * @param marcaId the marcaId to set
     */
    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

}
