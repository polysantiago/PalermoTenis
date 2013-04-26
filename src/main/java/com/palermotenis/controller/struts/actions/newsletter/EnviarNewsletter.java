package com.palermotenis.controller.struts.actions.newsletter;

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
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.common.collect.ImmutableMap;
import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import com.palermotenis.model.dao.Dao;

/**
 * 
 * @author Poly
 */
public class EnviarNewsletter extends ActionSupport implements ApplicationContextAware {

    private static final long serialVersionUID = 2209795006071659250L;

    private static final Logger logger = Logger.getLogger(EnviarNewsletter.class);

    private ApplicationContext applicationContext;

    private String subject;

    @Autowired
    private Dao<Suscriptor, Integer> suscriptorDao;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public String execute() {
        for (Suscriptor s : suscriptorDao.query("Active")) {
            taskExecutor.execute(new EnviarNewsletterTask(s.getEmail()));
        }
        return SUCCESS;
    }

    private class EnviarNewsletterTask implements Runnable {

        private final String to;

        public EnviarNewsletterTask(String to) {
            this.to = to;
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

                    String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        "com/palermotenis/templates/newsletter/newsletter.vm", "ISO-8859-1", model);

                    message.setText(text, true);
                }
            };
            try {
                mailSender.send(preparator);
            } catch (MailException ex) {
                logger.error(ex.getLocalizedMessage(), ex);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
