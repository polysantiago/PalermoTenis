package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;

/**
 * 
 * @author Poly
 */
public class GetPrecioValorPosible
    extends ActionSupport {

    private static final Logger logger = Logger.getLogger(GetPrecioValorPosible.class);

    private static final long serialVersionUID = 2108253535821338518L;
    private GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionDao;

    private Collection<PrecioPresentacion> precios;

    private GenericDao<Presentacion, Integer> presentacionDao;
    private Integer presentacionId;
    private GenericDao<Producto, Integer> productoDao;

    private Integer productoId;

    @Override
    public String execute() {
        try {
            Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
                .put("producto", productoDao.find(productoId))
                .put("presentacion", presentacionDao.find(presentacionId))
                .build();
            setPrecios(precioPresentacionDao.queryBy("Producto,Presentacion", args));
        } catch (Exception e) {
            logger.error("No se pudo obtener el precio por valor posible de Producto[" + productoId
                    + "], Presentacion[" + presentacionId + "]", e);
        }
        return SUCCESS;
    }

    /**
     * @return the precio
     */
    public Collection<PrecioPresentacion> getPrecios() {
        return precios;
    }

    /**
     * @param precio
     *            the precio to set
     */
    public void setPrecios(Collection<PrecioPresentacion> precios) {
        this.precios = precios;
    }

    /**
     * @param presentacionId
     *            the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }

    /**
     * @param productoId
     *            the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
