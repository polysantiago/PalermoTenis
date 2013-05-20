package com.palermotenis.model.service.newsletter.impl;

import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.ImmutableMap;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.service.newsletter.NewsletterService;
import com.palermotenis.model.service.newsletter.SuscriptorService;

@Service("newsletterService")
public class NewsletterServiceImpl implements NewsletterService, ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(NewsletterServiceImpl.class);

    private static final String NEWSLETTER_TEMPLATE = "templates/newsletter/newsletter.vm";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private SuscriptorService suscriptorService;

    private ApplicationContext applicationContext;

    @Override
    public void send(String subject) {
        for (Suscriptor suscriptor : suscriptorService.getActiveSuscriptores()) {
            taskExecutor.execute(new EnviarNewsletterTask(suscriptor.getEmail(), subject));
        }
    }

    private class EnviarNewsletterTask implements Runnable {

        private final String to;
        private final String subject;

        public EnviarNewsletterTask(String to, String subject) {
            this.to = to;
            this.subject = subject;
        }

        @Override
        public void run() {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {

                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {

                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                    message.setSubject(subject);
                    message.setFrom("Newsletter PalermoTenis <newsletter@palermotenis.com.ar>");
                    message.setReplyTo("PalermoTenis <consultas@palermotenis.com.ar>");
                    message.setBcc(InternetAddress.parse(to));

                    Resource resource = applicationContext.getResource("newsletter/Newsletter.jpg");

                    Map<String, Object> model = new ImmutableMap.Builder<String, Object>().put("imagen",
                        ImageIO.read(resource.getFile())).build();

                    String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, NEWSLETTER_TEMPLATE,
                        "ISO-8859-1", model);

                    message.setText(text, true);
                }
            };
            try {
                mailSender.send(preparator);
            } catch (MailException ex) {
                LOGGER.error(ex.getLocalizedMessage(), ex);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
