package com.palermotenis.model.service.newsletter;

import java.util.List;

import javax.persistence.NoResultException;

import com.palermotenis.controller.struts.actions.exceptions.AlreadySuscribedException;
import com.palermotenis.controller.struts.actions.exceptions.InvalidTokenException;
import com.palermotenis.model.beans.newsletter.Suscriptor;

public interface SuscriptorService {

    void add(String email) throws AlreadySuscribedException;

    void confirm(Integer suscriptorId, String token) throws InvalidTokenException;

    void create(String email, boolean active);

    void removeSuscriptor(String email) throws NoResultException;

    int getSuscriptoresCount();

    Suscriptor getSuscriptorByEmail(String email);

    List<Suscriptor> getSuscriptoresByEmail(String email);

    List<Suscriptor> getActiveSuscriptores();

    List<Suscriptor> getAllSuscriptores();

    List<Suscriptor> getAllSuscriptores(int maxResults, int firstResult);

}
