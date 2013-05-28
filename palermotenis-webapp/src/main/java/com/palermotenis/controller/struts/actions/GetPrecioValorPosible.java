package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.precios.Precio;
import com.palermotenis.model.service.precios.PrecioService;

public class GetPrecioValorPosible extends ActionSupport {

    private static final Logger logger = Logger.getLogger(GetPrecioValorPosible.class);

    private static final long serialVersionUID = 2108253535821338518L;

    private Integer productoId;
    private Integer presentacionId;
    private List<? extends Precio> precios;

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

    public List<? extends Precio> getPrecios() {
        return precios;
    }

    public void setPrecios(List<? extends Precio> precios) {
        this.precios = precios;
    }

    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
