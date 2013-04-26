package com.palermotenis.controller.struts.actions;

import java.io.InputStream;
import java.util.Collection;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 *
 * @author Poly
 */
public class GetPrecioUnidadOptions extends ActionSupport {

	private static final long serialVersionUID = -5154325699304286629L;

	private InputStream inputStream;
    
    @Autowired
    private Dao<Pago, Integer> pagoDao;
    
    @Autowired
    private Dao<Moneda, Integer> monedaDao;

    @Override
    public String execute() {

        Collection<Pago> pagos = pagoDao.findAll();
        Collection<Moneda> monedas = monedaDao.findAll();

        JSONObject jsonObject = new JSONObject();

        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]{"nombre","contrario","paises","formatter","locale"});

        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("pagos", pagos);

        inputStream = StringUtility.getInputString(jsonObject.toString());

        return SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
