/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.Collection;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *
 * @author Poly
 */
public class GetPrecioPresentacionOptions extends ActionSupport {

    private GenericDao<Pago, Integer> pagoService;
    private GenericDao<Moneda, Integer> monedaService;
    private GenericDao<TipoPresentacion, Integer> tipoPresentacionService;
    private GenericDao<TipoProducto, Integer> tipoProductoService;

    private Integer tipoProductoId;

    private InputStream inputStream;

    @Override
    public String execute() {

        Collection<Pago> pagos = pagoService.findAll();
        Collection<Moneda> monedas = monedaService.findAll();
        TipoProducto tipoProducto = tipoProductoService.find(tipoProductoId);
        Collection<TipoPresentacion> tiposPresentacion = tipoPresentacionService.queryBy("TipoProducto",
                new ImmutableMap.Builder<String, Object>().put("tipoProducto", tipoProducto).build());

        JSONObject jsonObject = new JSONObject();

        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]{"nombre","contrario","paises","formatter","locale"});

        JsonConfig jsonConfigTiposPresentacion = new JsonConfig();
        jsonConfigTiposPresentacion.setExcludes(new String[]{"tipoProducto","presentaciones","presentacionesByProd"});

        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("tiposPresentacion", tiposPresentacion, jsonConfigTiposPresentacion);
        jsonObject.element("pagos", pagos);

        inputStream = StringUtility.getInputString(jsonObject.toString());

        return SUCCESS;
    }

    /**
     * @param pagoService the pagosService to set
     */
    public void setPagoService(GenericDao<Pago, Integer> pagoService) {
        this.pagoService = pagoService;
    }

    /**
     * @param monedaService the monedasService to set
     */
    public void setMonedaService(GenericDao<Moneda, Integer> monedaService) {
        this.monedaService = monedaService;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param tipoPresentacionService the tipoPresentacionService to set
     */
    public void setTipoPresentacionService(GenericDao<TipoPresentacion, Integer> tipoPresentacionService) {
        this.tipoPresentacionService = tipoPresentacionService;
    }

    /**
     * @param tiposProductoService the tiposProductoService to set
     */
    public void setTipoProductoService(GenericDao<TipoProducto, Integer> tipoProductoService) {
        this.tipoProductoService = tipoProductoService;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }
}
