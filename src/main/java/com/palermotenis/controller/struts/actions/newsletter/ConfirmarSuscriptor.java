package com.palermotenis.controller.struts.actions.newsletter;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.struts.actions.newsletter.exceptions.InvalidTokenException;
import com.palermotenis.model.service.newsletter.SuscriptorService;

public class ConfirmarSuscriptor extends ActionSupport {

    private static final long serialVersionUID = -1618035149132038538L;

    private int spk;

    private String rstr;

    @Autowired
    private SuscriptorService suscriptorService;

    @Override
    public String execute() {
        try {
            suscriptorService.confirm(spk, rstr);
        } catch (InvalidTokenException ex) {
            return ERROR;
        }
        return SUCCESS;
    }

    public void setSpk(int spk) {
        this.spk = spk;
    }

    public void setRstr(String rstr) {
        this.rstr = rstr;
    }

}
