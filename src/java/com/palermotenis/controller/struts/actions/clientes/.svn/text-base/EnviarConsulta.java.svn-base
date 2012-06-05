/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.palermotenis.controller.struts.actions.clientes;

import com.opensymphony.xwork2.ActionSupport;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 *
 * @author Poly
 */
public class EnviarConsulta extends ActionSupport {

    private String nombre;
    private String email;
    private String telefono;
    private String consulta;

    private static final Logger logger = Logger.getLogger(EnviarConsulta.class);

    private JavaMailSender mailSender;

    @Override
    public String execute() {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("consultas@palermotenis.com.ar"));
                mimeMessage.setFrom(new InternetAddress(nombre + "<"+email+">"));
                mimeMessage.setReplyTo(InternetAddress.parse(nombre + "<"+email+">"));
                mimeMessage.setContent(new MimeMultipart("alternative"));
                mimeMessage.setSubject("Consulta desde PalermoTenis.com.ar","ISO_8859-1");
                mimeMessage.setText(
                        "Nombre : " + getNombre() + "\n"
                        + "Teléfono : " + getTelefono() + "\n"
                        + "Consulta : " + getConsulta(),
                        "ISO-8859-1");
            }
        };
        try {
            this.mailSender.send(preparator);
            logger.info("El cliente "+nombre+" ["+email+"] ha enviado una consulta");
        } catch (MailException ex) {
            logger.error("Error al enviar la consulta", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the consulta
     */
    public String getConsulta() {
        return consulta;
    }

    /**
     * @param consulta the consulta to set
     */
    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
