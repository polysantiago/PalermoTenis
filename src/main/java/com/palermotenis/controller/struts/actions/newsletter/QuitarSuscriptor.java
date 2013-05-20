package com.palermotenis.controller.struts.actions.newsletter;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.service.newsletter.SuscriptorService;

public class QuitarSuscriptor extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(QuitarSuscriptor.class);

    private static final long serialVersionUID = 631665704438465151L;

    private String email;

    @Autowired
    private SuscriptorService suscriptorService;

    @Override
    public String execute() {
        try {
            suscriptorService.removeSuscriptor(email);
        } catch (Exception ex) {
            LOGGER.error("Error while removing suscriptor", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        try {
            suscriptorService.getSuscriptorByEmail(email);
        } catch (NoResultException ex) {
            addActionError("La casilla de correo ingresada no se encuentra registrada en nuestra base de datos");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
