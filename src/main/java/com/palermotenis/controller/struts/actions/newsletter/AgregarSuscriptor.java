package com.palermotenis.controller.struts.actions.newsletter;

import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.controller.struts.actions.JsonActionSupport;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.Dao;
import com.palermotenis.util.StringUtility;

/**
 * 
 * @author Poly
 */
public class AgregarSuscriptor extends JsonActionSupport implements ApplicationAware {

    private static final String CONFIRMACION_TEMPLATE = "templates/mail/confirmacionNewsletter.vm";

    private static final long serialVersionUID = 5127795631209333548L;

    private String email;

    private Map<String, Object> application;

    private final String ALREADY_SUSCRIBED = "Usted ya está suscripto";
    private final String NEED_CONFIRMATION = "Hemos enviado un email a la casilla indicada para que confirme su suscripción";

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public String execute() {
        List<Suscriptor> suscriptores = suscriptorDao.queryBy("Email",
            new ImmutableMap.Builder<String, Object>().put("email", email).build());

        if (suscriptores != null && !suscriptores.isEmpty()) {
            Suscriptor suscriptor = suscriptores.get(0);
            if (suscriptor.isActivo()) {
                createJSON(ERROR, ALREADY_SUSCRIBED);
                return JSON;
            } else {
                if (suscriptor.getRandomStr() == null || suscriptor.getRandomStr().isEmpty()) { // algunos suscriptores
                                                                                                // fueron ingresados
                                                                                                // manualmente
                    suscriptor.setRandomStr(StringUtility.buildRandomString());
                }
                confirm(suscriptor);
                return JSON;
            }
        } else {
            Suscriptor suscriptor = new Suscriptor(email, StringUtility.buildRandomString(), false);
            suscriptorDao.create(suscriptor);
            confirm(suscriptor);
            return JSON;
        }
    }

    private void confirm(Suscriptor suscriptor) {
        createJSON(SUCCESS, NEED_CONFIRMATION);
        sendConfirmationEmail(suscriptor);
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

    private void createJSON(String result, String msg) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.element("result", result);
        jSONObject.element("msg", msg);
        writeResponse(jSONObject);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }
}
