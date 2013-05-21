package com.palermotenis.controller.struts.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.atributos.tipos.TipoAtributoClasificatorio;
import com.palermotenis.model.service.atributos.tipos.TipoAtributoService;

public class GetClasificaciones extends ActionSupport {

    private static final long serialVersionUID = -879847503471376338L;

    @Autowired
    private TipoAtributoService tipoAtributoService;

    public List<TipoAtributoClasificatorio> getTipoAtributoClasifs() {
        return tipoAtributoService.getAllTiposAtributosClasificatorios();
    }
}
