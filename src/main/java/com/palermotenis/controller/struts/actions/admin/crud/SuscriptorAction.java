package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class SuscriptorAction extends ActionSupport {

    private static final long serialVersionUID = 3824524208833901840L;

    private final String LIST = "list";

    private Collection<Suscriptor> suscriptores;

    private int maxResults = 30;
    private int firstResult;

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    public String list() {
        suscriptores = suscriptorDao.findAll(getMaxResults(), getFirstResult());
        return LIST;
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
     * @param maxResults
     *            the maxResults to set
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
     * @param firstResult
     *            the firstResult to set
     */
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getTotal() {
        return suscriptorDao.count();
    }
}
