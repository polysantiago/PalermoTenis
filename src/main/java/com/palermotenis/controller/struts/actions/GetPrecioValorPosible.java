package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.service.precios.impl.PrecioService;

public class GetPrecioValorPosible extends ActionSupport {

    private static final Logger logger = Logger.getLogger(GetPrecioValorPosible.class);

    private static final long serialVersionUID = 2108253535821338518L;

    private Integer productoId;
    private Integer presentacionId;
    private Collection<PrecioPresentacion> precios;

    @Autowired
    private PrecioService precioService;

    @Override
    public String execute() {
        setPrecios(precioService.getPrecios(productoId, presentacionId));
        if (CollectionUtils.isEmpty(getPrecios())) {
            logger.warn("No se pudo obtener el precio por valor posible de Producto[" + productoId + "], Presentacion["
                    + presentacionId + "]");
        }
        return SUCCESS;
    }

    public Collection<PrecioPresentacion> getPrecios() {
        return precios;
    }

    public void setPrecios(Collection<PrecioPresentacion> precios) {
        this.precios = precios;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
