/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.newsletter;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.util.StringUtility;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author Poly
 */
public class AgregarSuscriptor extends ActionSupport implements ApplicationAware {

    private GenericDao<Suscriptor, Integer> suscriptorService;
    private String email;
    private InputStream inputStream;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private Map<String, Object> application;
    private final String ALREADY_SUSCRIBED = "Usted ya está suscripto";
    private final String NEED_CONFIRMATION = "Hemos enviado un email a la casilla indicada para que confirme su suscripción";
    private final String JSON = "json";

    @Override
    public String execute() {
        List<Suscriptor> suscriptores = suscriptorService.queryBy("Email",
                new ImmutableMap.Builder<String, Object>().put("email", email).build());

        if (suscriptores != null && !suscriptores.isEmpty()) {
            Suscriptor suscriptor = suscriptores.get(0);
            if (suscriptor.isActivo()) {
                createJSON(ERROR, ALREADY_SUSCRIBED);
                return JSON;
            } else {
                if(suscriptor.getRandomStr() == null || suscriptor.getRandomStr().isEmpty()){ //algunos suscriptores fueron ingresados manualmente
                    suscriptor.setRandomStr(StringUtility.buildRandomString());
                }
                confirm(suscriptor);
                return JSON;
            }
        } else {
            Suscriptor suscriptor = new Suscriptor(email, StringUtility.buildRandomString(), false);
            suscriptorService.create(suscriptor);
            confirm(suscriptor);
            return JSON;
        }
    }

    private void sendConfirmationEmail(final Suscriptor suscriptor) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                message.setSubject("Suscripción al Newsletter de PalermoTenis.com.ar");
                message.setTo(suscriptor.getEmail());
                message.setFrom("Newsletter PalermoTenis.com.ar <noreply@palermotenis.com.ar>");

                Map model = new HashMap();
                model.put("suscriptor", suscriptor);
                model.put("domain", application.get("domain"));

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/palermotenis/templates/mail/confirmacionNewsletter.vm", model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    private void createJSON(String result, String msg) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.element("result", result);
        jSONObject.element("msg", msg);
        inputStream = StringUtility.getInputString(jSONObject.toString());
    }

    private void confirm(Suscriptor suscriptor) {
        createJSON(SUCCESS, NEED_CONFIRMATION);
        sendConfirmationEmail(suscriptor);
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * @param suscriptorService the suscriptoresService to set
     */
    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }
}
