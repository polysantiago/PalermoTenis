package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.presentaciones.Presentacion;
import com.palermotenis.model.service.presentaciones.PresentacionService;

public class GetPresentacionesByTipo extends ActionSupport {

    private static final long serialVersionUID = 3583987664940616484L;

    @Autowired
    private PresentacionService presentacionService;

    private Integer tipoPresentacionId;

    public void setTipoPresentacionId(Integer tipoPresentacionId) {
        this.tipoPresentacionId = tipoPresentacionId;
    }

    public List<Presentacion> getPresentaciones() {
        return presentacionService.getPresentacionesByTipo(tipoPresentacionId);
    }
}
