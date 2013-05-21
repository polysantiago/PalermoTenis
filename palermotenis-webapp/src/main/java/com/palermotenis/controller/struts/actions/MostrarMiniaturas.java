package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Modelo;
import com.palermotenis.model.service.modelos.ModeloService;

public class MostrarMiniaturas extends ActionSupport {

    private static final long serialVersionUID = 4048064721829257474L;

    private Integer modeloId;
    private Integer tipoProductoId;
    private Integer marcaId;

    private List<Modelo> modelos;

    @Autowired
    private ModeloService modeloService;

    public String showByPadre() {
        modelos = modeloService.getModelosByPadre(modeloId);
        return SUCCESS;
    }

    public String showByMarca() {
        modelos = modeloService.getModelosWithLeafActiveProductos(marcaId, tipoProductoId);
        return SUCCESS;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

}
