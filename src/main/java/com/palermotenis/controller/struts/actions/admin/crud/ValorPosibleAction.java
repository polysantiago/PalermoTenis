package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.valores.ValorPosible;
import com.palermotenis.model.service.valores.ValorService;

public class ValorPosibleAction extends JsonActionSupport {

    private static final long serialVersionUID = -7754759545053197071L;

    private Collection<ValorPosible> valoresPosibles;

    private Integer tipoAtributoTipadoId;
    private Integer valorPosibleId;

    private String unidad;

    @Autowired
    private ValorService valorService;

    public String show() {
        return SHOW;
    }

    public String create() {
        try {
            valorService.createNewValor(tipoAtributoTipadoId, unidad);
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String edit() {
        try {
            valorService.updateValor(valorPosibleId, unidad);
            success();
        } catch (Exception ex) {
            failure(ex);
        }
        return JSON;
    }

    public String destroy() {
        try {
            valorService.deleteValor(valorPosibleId);
            success();
        } catch (HibernateException ex) {
            failure(ex);
        }
        return JSON;
    }

    public Collection<ValorPosible> getValoresPosibles() {
        if (valoresPosibles == null) {
            valoresPosibles = valorService.getValoresPosiblesByTipo(tipoAtributoTipadoId);
        }
        return valoresPosibles;
    }

    public void setTipoAtributoId(Integer tipoAtributoId) {
        this.tipoAtributoTipadoId = tipoAtributoId;
    }

    public void setValorPosibleId(Integer valorPosibleId) {
        this.valorPosibleId = valorPosibleId;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
