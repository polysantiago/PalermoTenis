package com.palermotenis.controller.struts.actions.newsletter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.exceptions.AlreadySuscribedException;
import com.palermotenis.model.service.newsletter.SuscriptorService;

public class AgregarSuscriptor extends ActionSupport {

    private static final long serialVersionUID = 5127795631209333548L;

    private static final String NEED_CONFIRMATION = "Hemos enviado un email a la casilla indicada para que confirme su suscripción";

    private final Map<String, String> resultsMap = Maps.newLinkedHashMap();

    private String email;

    @Autowired
    private SuscriptorService suscriptorService;

    @Override
    public String execute() {
        try {
            suscriptorService.add(email);
            result(SUCCESS, NEED_CONFIRMATION);
        } catch (AlreadySuscribedException ex) {
            result(ERROR, ex.getMessage());
        }
        return SUCCESS;
    }

    private void result(String result, String msg) {
        resultsMap.put("result", result);
        resultsMap.put("msg", msg);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, String> getResultsMap() {
        return resultsMap;
    }
}
