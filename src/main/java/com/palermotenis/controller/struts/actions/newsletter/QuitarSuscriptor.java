/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.newsletter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class QuitarSuscriptor extends ActionSupport {

    private static final long serialVersionUID = 631665704438465151L;

    private String email;

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    @Override
    public String execute() {
        Suscriptor suscriptor = suscriptorDao.findBy("Email", "email", email);
        if (suscriptor.isActivo()) {
            suscriptor.setActivo(false);
            try {
                suscriptorDao.edit(suscriptor);
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
        Suscriptor suscriptor = suscriptorDao.findBy("Email", "email", email);
        if (suscriptor == null) {
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
