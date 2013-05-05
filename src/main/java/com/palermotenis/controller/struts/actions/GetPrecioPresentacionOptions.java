package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.palermotenis.model.beans.Moneda;
import com.palermotenis.model.beans.Pago;
import com.palermotenis.model.beans.presentaciones.tipos.TipoPresentacion;
import com.palermotenis.model.service.monedas.MonedaService;
import com.palermotenis.model.service.pagos.PagoService;
import com.palermotenis.model.service.presentaciones.tipos.TipoPresentacionService;

public class GetPrecioPresentacionOptions extends JsonActionSupport {

    private static final long serialVersionUID = 3609468539847111988L;

    private Integer tipoProductoId;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    private TipoPresentacionService tipoPresentacionService;

    private final Map<String, Object> map = Maps.newLinkedHashMap();

    @Override
    public String execute() {
        map.put("monedas", getMonedas());
        map.put("tiposPresentacion", getTiposPresentacion());
        map.put("pagos", getPagos());
        return SUCCESS;
    }

    private Collection<TipoPresentacion> getTiposPresentacion() {
        return tipoPresentacionService.getTiposPresentacionByTipoProducto(tipoProductoId);
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

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }
}
