/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.newsletter;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

/**
 *
 * @author Poly
 */
public class QuitarSuscriptor extends ActionSupport {

    private String email;
    private GenericDao<Suscriptor, Integer> suscriptorService;

    @Override
    public String execute() {
        Suscriptor suscriptor = suscriptorService.findBy("Email", "email", email);
        if (suscriptor.isActivo()) {
            suscriptor.setActivo(false);
            try {
                suscriptorService.edit(suscriptor);
            } catch (HibernateException ex) {
                Logger.getLogger(QuitarSuscriptor.class.getName()).log(Level.SEVERE, null, ex);
                return ERROR;
            } catch (Exception ex) {
                Logger.getLogger(QuitarSuscriptor.class.getName()).log(Level.SEVERE, null, ex);
                return ERROR;
            }
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        Suscriptor suscriptor = suscriptorService.findBy("Email", "email", email);
        if (suscriptor == null) {
            addActionError("La casilla de correo ingresada no se encuentra registrada en nuestra base de datos");
        }
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param suscriptorService the suscriptoresService to set
     */
    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }
}
