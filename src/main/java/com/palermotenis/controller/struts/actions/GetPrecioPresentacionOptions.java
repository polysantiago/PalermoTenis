package com.palermotenis.controller.struts.actions;

import java.util.Collection;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public String execute() {
        List<Pago> pagos = pagoService.getAllPagos();
        Collection<Moneda> monedas = monedaService.getAllMonedas();
        Collection<TipoPresentacion> tiposPresentacion = tipoPresentacionService
            .getTiposPresentacionByTipoProducto(tipoProductoId);

        JSONObject jsonObject = new JSONObject();
        JsonConfig jsonConfigMonedas = new JsonConfig();
        jsonConfigMonedas.setExcludes(new String[]
            { "nombre", "contrario", "paises", "formatter", "locale" });
        JsonConfig jsonConfigTiposPresentacion = new JsonConfig();
        jsonConfigTiposPresentacion.setExcludes(new String[]
            { "tipoProducto", "presentaciones", "presentacionesByProd" });
        jsonObject.element("monedas", monedas, jsonConfigMonedas);
        jsonObject.element("tiposPresentacion", tiposPresentacion, jsonConfigTiposPresentacion);
        jsonObject.element("pagos", pagos);

        writeResponse(jsonObject);
        return SUCCESS;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }
}
