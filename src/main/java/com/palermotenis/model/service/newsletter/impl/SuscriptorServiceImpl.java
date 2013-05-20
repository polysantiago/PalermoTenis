package com.palermotenis.model.service.newsletter.impl;

import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.struts.actions.newsletter.exceptions.AlreadySuscribedException;
import com.palermotenis.controller.struts.actions.newsletter.exceptions.InvalidTokenException;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.suscriptores.SuscriptorDao;
import com.palermotenis.model.service.newsletter.SuscriptorService;

@Service("suscriptorService")
public class SuscriptorServiceImpl implements SuscriptorService, ApplicationAware {

    private static final String CONFIRMACION_TEMPLATE = "templates/mail/confirmacion_newsletter.vm";
    private static final String ALREADY_SUSCRIBED = "Usted ya está suscripto";

    @Autowired
    private SuscriptorDao suscriptorDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    private Map<String, Object> application;

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
                suscriptor.setRandomStr(RandomStringUtils.randomAlphanumeric(10));
            }
        } else {
            suscriptor = new Suscriptor(email, RandomStringUtils.randomAlphanumeric(10), false);
            suscriptorDao.create(suscriptor);
        }
        sendConfirmationEmail(suscriptor);
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

    private void sendConfirmationEmail(final Suscriptor suscriptor) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setSubject("Suscripción al Newsletter de PalermoTenis.com.ar");
                message.setTo(suscriptor.getEmail());
                message.setFrom("Newsletter PalermoTenis.com.ar <noreply@palermotenis.com.ar>");

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("suscriptor", suscriptor)
                    .put("domain", application.get("domain"))
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, CONFIRMACION_TEMPLATE,
                    "ISO-8859-1", model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    @Override
    @Transactional
    public void createActiveSuscriptor(String email) {
        List<Suscriptor> suscriptores = getSuscriptoresByEmail(email);
        if (CollectionUtils.isEmpty(suscriptores)) {
            Suscriptor suscriptor = new Suscriptor(email, RandomStringUtils.randomAlphanumeric(10), true);
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

    @Override
    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }

}
