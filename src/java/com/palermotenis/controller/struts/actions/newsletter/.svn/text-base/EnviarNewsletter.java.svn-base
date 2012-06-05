/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.newsletter;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.controller.daos.GenericDao;
import com.palermotenis.model.beans.newsletter.Suscriptor;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author Poly
 */
public class EnviarNewsletter extends ActionSupport implements ApplicationContextAware {

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private GenericDao<Suscriptor, Integer> suscriptorService;
    private ApplicationContext applicationContext;
    private TaskExecutor taskExecutor;

    private String subject;

    private static final Logger logger = Logger.getLogger(EnviarNewsletter.class);

    @Override
    public String execute() {

        for (Suscriptor s : suscriptorService.query("Active"))
           taskExecutor.execute(new EnviarNewsletterTask(s.getEmail()));
        return SUCCESS;
    }

    private class EnviarNewsletterTask implements Runnable {

        private String to;

        public EnviarNewsletterTask(String to) {
            this.to = to;
        }

        public void run() {
            MimeMessagePreparator preparator = new MimeMessagePreparator() {

                public void prepare(MimeMessage mimeMessage) throws Exception {

                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                    message.setSubject(subject);
                    message.setFrom("Newsletter PalermoTenis <newsletter@palermotenis.com.ar>");
                    message.setReplyTo("PalermoTenis <consultas@palermotenis.com.ar>");
                    message.setBcc(InternetAddress.parse(to));

                    Resource resource = applicationContext.getResource("newsletter/Newsletter.jpg");

                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("imagen", ImageIO.read(resource.getFile()));

                    String text = VelocityEngineUtils.mergeTemplateIntoString(
                            velocityEngine,
                            "com/palermotenis/templates/newsletter/newsletter.vm",
                            "ISO-8859-1",
                            model);

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

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @param velocityEngine the velocityEngine to set
     */
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * @param suscriptorService the suscriptoresService to set
     */
    public void setSuscriptorService(GenericDao<Suscriptor, Integer> suscriptorService) {
        this.suscriptorService = suscriptorService;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
