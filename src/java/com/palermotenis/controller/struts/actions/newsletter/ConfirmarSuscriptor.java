/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.controller.struts.actions.newsletter;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;

/**
 *
 * @author Poly
 */
public class ConfirmarSuscriptor extends ActionSupport {

    private int spk;
    private String rstr;
    private GenericDao<Suscriptor, Integer> suscriptorService;

    @Override
    public String execute(){
        try {
            Suscriptor s = suscriptorService.find(spk);
            if(s.getRandomStr().equals(rstr)){
                s.setActivo(true);
                suscriptorService.edit(s);
            } else {
                return ERROR;
            }
        } catch (Exception ex){
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @param spk the spk to set
     */
    public void setSpk(int spk) {
        this.spk = spk;
    }

    /**
     * @param rstr the rstr to set
     */
    public void setRstr(String rstr) {
        this.rstr = rstr;
    }

    /**
     * @param suscriptorService the suscriptoresService to set
     */
    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

}
