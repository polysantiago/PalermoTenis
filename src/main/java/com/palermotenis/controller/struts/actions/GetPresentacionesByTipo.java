package com.palermotenis.controller.struts.actions;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.service.presentaciones.PresentacionService;

public class GetPresentacionesByTipo extends JsonActionSupport {

    private static final long serialVersionUID = 3583987664940616484L;

    @Autowired
    private PresentacionService presentacionService;

    private Integer tipoPresentacionId;

    @Override
    public String execute() {

        Collection<Presentacion> presentaciones = presentacionService.getPresentacionesByTipo(tipoPresentacionId);

        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]
            { "tipoPresentacion", "productos", "stocks", "precios" });

        JSONArray jObject = (JSONArray) JSONSerializer.toJSON(presentaciones, config);
        writeResponse(jObject);
        return SUCCESS;
    }

    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

}
