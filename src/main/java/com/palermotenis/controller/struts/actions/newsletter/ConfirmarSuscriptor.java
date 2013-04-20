/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.newsletter;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;

/**
 * 
 * @author Poly
 */
public class ConfirmarSuscriptor extends ActionSupport {

    private static final long serialVersionUID = -1618035149132038538L;

    private int spk;

    private String rstr;

    @Autowired
    private GenericDao<Suscriptor, Integer> suscriptorDao;

    @Override
    public String execute() {
        try {
            Suscriptor s = suscriptorDao.find(spk);
            if (s.getRandomStr().equals(rstr)) {
                s.setActivo(true);
                suscriptorDao.edit(s);
            } else {
                return ERROR;
            }
        } catch (Exception ex) {
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
