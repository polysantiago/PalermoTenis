package com.palermotenis.controller.struts.actions.admin.crud;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.InputStreamActionSupport;
import com.palermotenis.model.service.modelos.ModeloService;

public class ModeloAction extends InputStreamActionSupport {

    private static final long serialVersionUID = -3215895548120987791L;
    private static final Logger logger = Logger.getLogger(ModeloAction.class);

    private Integer padreId;
    private Integer marcaId;
    private Integer tipoProductoId;
    private Integer modeloId;
    private Integer modeloOrden;
    private Integer modeloRgtId;
    private Integer modeloRgtOrden;

    private String nombre;

    @Autowired
    private ModeloService modeloService;

    public String create() {
        Integer modeloId = modeloService.createNewModelo(padreId, tipoProductoId, marcaId, nombre);
        writeResponse(modeloId.toString());
        return JSON;
    }

    public String edit() {
        try {
            modeloService.updateModelo(getModeloId(), nombre);
            success();
        } catch (HibernateException ex) {
            logger.error("No existe el modelo a eliminar", ex);
            failure(ex);
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al editar el modelo " + getModeloId(), ex);
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            modeloService.deleteModelo(modeloId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String move() {
        try {
            modeloService.moveModelo(modeloId, modeloOrden, modeloRgtId, modeloRgtOrden);
            success();
        } catch (Exception ex) {
            logger.error("Ha ocurrido un error al mover el modelo " + modeloId, ex);
            failure(ex);
        }
        return JSON;
    }

    public void setPadreId(Integer padreId) {
        this.padreId = padreId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getModeloId() {
        return modeloId;
    }

    public void setModeloId(Integer modeloId) {
        this.modeloId = modeloId;
    }

    public void setModeloOrden(Integer modeloOrden) {
        this.modeloOrden = modeloOrden;
    }

    public void setModeloRgtId(Integer modeloRgtId) {
        this.modeloRgtId = modeloRgtId;
    }

    public void setModeloRgtOrden(Integer modeloRgtOrden) {
        this.modeloRgtOrden = modeloRgtOrden;
    }

}
