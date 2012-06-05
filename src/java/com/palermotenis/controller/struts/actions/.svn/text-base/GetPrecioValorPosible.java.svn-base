/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.precios.PrecioPresentacion;
import com.palermotenis.model.beans.precios.pks.PrecioPresentacionPK;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.beans.productos.Producto;
import java.util.Collection;
import java.util.Map;
import org.apache.log4j.Logger;



/**
 *
 * @author Poly
 */
public class GetPrecioValorPosible extends ActionSupport {

    private GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService;
    private GenericDao<Producto, Integer> productoService;
    private GenericDao<Presentacion, Integer> presentacionService;
    private Integer presentacionId;
    private Integer productoId;
    private Collection<PrecioPresentacion> precios;

    private static final Logger logger = Logger.getLogger(GetPrecioValorPosible.class);

    @Override
    public String execute(){
        try {
            Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
                    .put("producto", productoService.find(productoId))
                    .put("presentacion", presentacionService.find(presentacionId))
                    .build();
            setPrecios(precioPresentacionService.queryBy("Producto,Presentacion", args));
        } catch (Exception e){
            logger.error("No se pudo obtener el precio por valor posible de Producto["+productoId+"], Presentacion["+presentacionId+"]", e);
        }
        return SUCCESS;
    }

    /**
     * @param presentacionId the presentacionId to set
     */
    public void setPresentacionId(Integer presentacionId) {
        this.presentacionId = presentacionId;
    }


    /**
     * @param productoId the productoId to set
     */
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    /**
     * @return the precio
     */
    public Collection<PrecioPresentacion> getPrecios() {
        return precios;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecios(Collection<PrecioPresentacion> precios) {
        this.precios = precios;
    }

    /**
     * @param precioPresentacionService the precioPresentacionService to set
     */
    public void setPrecioPresentacionService(GenericDao<PrecioPresentacion, PrecioPresentacionPK> precioPresentacionService) {
        this.precioPresentacionService = precioPresentacionService;
    }

    public void setPresentacionService(GenericDao<Presentacion, Integer> presentacionService) {
        this.presentacionService = presentacionService;
    }

    public void setProductoService(GenericDao<Producto, Integer> productoService) {
        this.productoService = productoService;
    }


}
