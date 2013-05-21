package com.palermotenis.controller.struts.actions;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;

public class GetPrecioUnidadOptions extends InputStreamActionSupport {

    private static final long serialVersionUID = -5154325699304286629L;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MonedaService monedaService;

    private final Map<String, Object> map = Maps.newLinkedHashMap();

    @Override
    public String execute() {
        map.put("monedas", getMonedas());
        map.put("pagos", getPagos());
        return SUCCESS;
    }

    private List<Moneda> getMonedas() {
        return monedaService.getAllMonedas();
    }

    private List<Pago> getPagos() {
        return pagoService.getAllPagos();
    }

    public Map<String, Object> getMap() {
        return map;
    }

}
