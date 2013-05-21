package com.palermotenis.model.service.newsletter;

import com.palermotenis.model.beans.newsletter.Suscriptor;

public interface NewsletterService {

    void send(String subject);

    void confirmSuscription(Suscriptor suscriptor);

}
