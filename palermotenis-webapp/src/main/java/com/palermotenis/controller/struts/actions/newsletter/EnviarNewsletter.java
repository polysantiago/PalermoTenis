package com.palermotenis.controller.struts.actions.newsletter;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.palermotenis.model.service.newsletter.NewsletterService;

public class EnviarNewsletter extends ActionSupport {

    private static final long serialVersionUID = 2209795006071659250L;

    private String subject;

    @Autowired
    private NewsletterService newsletterService;

    @Override
    public String execute() {
        newsletterService.send(subject);
        return SUCCESS;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
