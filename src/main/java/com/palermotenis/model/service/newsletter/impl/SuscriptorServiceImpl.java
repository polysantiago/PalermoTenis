package com.palermotenis.model.service.newsletter.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.palermotenis.controller.struts.actions.exceptions.AlreadySuscribedException;
import com.palermotenis.controller.struts.actions.exceptions.InvalidTokenException;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.suscriptores.SuscriptorDao;
import com.palermotenis.model.service.newsletter.NewsletterService;
import com.palermotenis.model.service.newsletter.SuscriptorService;

@Service("suscriptorService")
public class SuscriptorServiceImpl implements SuscriptorService {

    private static final String ALREADY_SUSCRIBED = "Usted ya está suscripto";

    @Autowired
    private SuscriptorDao suscriptorDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private NewsletterService newsletterService;

    @Override
    @Transactional
    public void add(String email) throws AlreadySuscribedException {
        List<Suscriptor> suscriptores = getSuscriptoresByEmail(email);
        Suscriptor suscriptor = null;
        if (CollectionUtils.isNotEmpty(suscriptores)) {
            suscriptor = suscriptores.get(0);

            if (suscriptor.isActivo()) {
                throw new AlreadySuscribedException(ALREADY_SUSCRIBED);
            }

            if (StringUtils.isEmpty(suscriptor.getRandomStr())) { // algunos suscriptores fueron ingresados manualmente
                suscriptor.setRandomStr(RandomStringUtils.randomAlphanumeric(9));
            }
        } else {
            create(email, false);
        }
        newsletterService.confirmSuscription(suscriptor);
    }

    @Override
    @Transactional
    public void confirm(Integer suscriptorId, String token) throws InvalidTokenException {
        Suscriptor suscriptor = suscriptorDao.find(suscriptorId);
        if (suscriptor == null || !suscriptor.getRandomStr().equals(token)) {
            throw new InvalidTokenException();
        }
        suscriptor.setActivo(true);
        suscriptorDao.edit(suscriptor);
    }

    @Override
    @Transactional
    public void create(String email, boolean active) {
        List<Suscriptor> suscriptores = getSuscriptoresByEmail(email);
        if (CollectionUtils.isEmpty(suscriptores)) {
            Suscriptor suscriptor = new Suscriptor(email, RandomStringUtils.randomAlphanumeric(9), active);
            suscriptorDao.create(suscriptor);
        }
    }

    @Override
    public void removeSuscriptor(String email) throws NoResultException {
        Suscriptor suscriptor = suscriptorDao.findBy("Email", "email", email);
        if (suscriptor.isActivo()) {
            suscriptor.setActivo(false);
            suscriptorDao.edit(suscriptor);
        }
    }

    @Override
    public Suscriptor getSuscriptorByEmail(String email) {
        return suscriptorDao.findBy("Email", "email", email);
    }

    @Override
    public List<Suscriptor> getActiveSuscriptores() {
        return suscriptorDao.query("Active");
    }

    @Override
    public List<Suscriptor> getSuscriptoresByEmail(String email) {
        return suscriptorDao.queryBy("Email", "email", email);
    }

    @Override
    public List<Suscriptor> getAllSuscriptores() {
        return suscriptorDao.findAll();
    }

    @Override
    public List<Suscriptor> getAllSuscriptores(int maxResults, int firstResult) {
        return suscriptorDao.findAll(maxResults, firstResult);
    }

    @Override
    public int getSuscriptoresCount() {
        return suscriptorDao.count();
    }

}
