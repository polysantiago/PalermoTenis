package com.palermotenis.controller.struts.actions.admin.crud;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.service.newsletter.SuscriptorService;

public class SuscriptorAction extends ActionSupport {

    private static final long serialVersionUID = 3824524208833901840L;

    private final String LIST = "list";

    private Collection<Suscriptor> suscriptores;

    private int maxResults = 30;
    private int firstResult;

    @Autowired
    private SuscriptorService suscriptorService;

    public String list() {
        suscriptores = suscriptorService.getAllSuscriptores(getMaxResults(), getFirstResult());
        return LIST;
    }

    public Collection<Suscriptor> getSuscriptores() {
        return suscriptores;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return (firstResult == 0) ? 1 : firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getTotal() {
        return suscriptorService.getSuscriptoresCount();
    }
}
