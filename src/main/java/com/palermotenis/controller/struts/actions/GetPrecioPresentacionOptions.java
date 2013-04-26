package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.beans.productos.tipos.TipoProducto;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetPrecioPresentacionOptions extends ActionSupport {

	private static final long serialVersionUID = 3609468539847111988L;

	private Integer tipoProductoId;

    private InputStream inputStream;
    
    @Autowired
    private Dao<Pago, Integer> pagoDao;
    
    @Autowired
    private Dao<Moneda, Integer> monedaDao;
    
    @Autowired
    private Dao<TipoPresentacion, Integer> tipoPresentacionDao;
    
    @Autowired
    private Dao<TipoProducto, Integer> tipoProductoDao;

    @Override
    public String execute() {
        Collection<Pago> pagos = pagoDao.findAll();
        Collection<Moneda> monedas = monedaDao.findAll();
        TipoProducto tipoProducto = tipoProductoDao.find(tipoProductoId);
        Collection<TipoPresentacion> tiposPresentacion = tipoPresentacionDao.queryBy("TipoProducto",
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
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param tipoProductoId the tipoProductoId to set
     */
    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }
}
