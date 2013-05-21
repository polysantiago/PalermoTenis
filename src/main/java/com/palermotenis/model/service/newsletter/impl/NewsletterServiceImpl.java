package com.palermotenis.model.service.newsletter.impl;

import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String CONFIRMACION_TEMPLATE = "templates/mail/confirmacion_newsletter.vm";
    private static final String NEWSLETTER_TEMPLATE = "templates/newsletter/newsletter.vm";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private SuscriptorService suscriptorService;

    @Value("${templates.mail.newsletter.send.from}")
    private String sendFrom;

    @Value("${templates.mail.newsletter.send.replyto}")
    private String sendReplyTo;

    @Value("${templates.mail.newsletter.confirmation.from}")
    private String confirmationFrom;

    @Value("${templates.mail.newsletter.confirmation.subject}")
    private String confirmationSubject;

    @Value("${com.palermotenis.mail.mime.charset}")
    private String encoding;

    @Value("${templates.mail.domain}")
    private String domain;

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

                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage, encoding);

                    message.setSubject(subject);
                    message.setFrom(sendFrom);
                    message.setReplyTo(sendReplyTo);
                    message.setBcc(InternetAddress.parse(to));

                    Resource resource = applicationContext.getResource("newsletter/Newsletter.jpg");

                    Map<String, Object> model = new ImmutableMap.Builder<String, Object>().put("imagen",
                        ImageIO.read(resource.getFile())).build();

                    String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, NEWSLETTER_TEMPLATE,
                        encoding, model);

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
    public void confirmSuscription(final Suscriptor suscriptor) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, encoding);

                message.setSubject(confirmationSubject);
                message.setFrom(confirmationFrom);

                message.setTo(suscriptor.getEmail());

                Map<String, Object> model = new ImmutableMap.Builder<String, Object>()
                    .put("suscriptor", suscriptor)
                    .put("domain", domain)
                    .build();

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, CONFIRMACION_TEMPLATE,
                    encoding, model);

                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
