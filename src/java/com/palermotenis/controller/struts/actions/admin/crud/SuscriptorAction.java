/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.admin.crud;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import java.util.Collection;

/**
 *
 * @author Poly
 */
public class SuscriptorAction extends ActionSupport {

    private final String LIST = "list";
    private GenericDao<Suscriptor, Integer> suscriptorService;
    private Collection<Suscriptor> suscriptores;
    private int maxResults = 30;
    private int firstResult;

    public String list() {
        suscriptores = suscriptorService.findAll(getMaxResults(), getFirstResult());
        return LIST;
    }

    /**
     * @param suscriptorService the suscriptoresService to set
     */
    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

    /**
     * @return the suscriptores
     */
    public Collection<Suscriptor> getSuscriptores() {
        return suscriptores;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return the firstResult
     */
    public int getFirstResult() {
        return (firstResult == 0) ? 1 : firstResult;
    }

    /**
     * @param firstResult the firstResult to set
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getTotal() {
        return suscriptorService.count();
    }
}
