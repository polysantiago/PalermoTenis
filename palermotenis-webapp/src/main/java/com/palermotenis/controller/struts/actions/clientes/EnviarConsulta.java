package com.palermotenis.controller.struts.actions.clientes;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.opensymphony.xwork2.ActionSupport;

public class EnviarConsulta extends ActionSupport {
    private static final long serialVersionUID = -7456837798427645845L;

    private String nombre;
    private String email;
    private String telefono;
    private String consulta;

    private static final Logger logger = Logger.getLogger(EnviarConsulta.class);

    private JavaMailSender mailSender;

    @Override
    public String execute() {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                mimeMessage
                    .setRecipient(Message.RecipientType.TO, new InternetAddress("consultas@palermotenis.com.ar"));
                mimeMessage.setFrom(new InternetAddress(nombre + "<" + email + ">"));
                mimeMessage.setReplyTo(InternetAddress.parse(nombre + "<" + email + ">"));
                mimeMessage.setContent(new MimeMultipart("alternative"));
                mimeMessage.setSubject("Consulta desde PalermoTenis.com.ar", "ISO_8859-1");
                mimeMessage.setText("Nombre : " + getNombre() + "\n" + "Teléfono : " + getTelefono() + "\n"
                        + "Consulta : " + getConsulta(), "ISO-8859-1");
            }
        };
        try {
            this.mailSender.send(preparator);
            logger.info("El cliente " + nombre + " [" + email + "] ha enviado una consulta");
        } catch (MailException ex) {
            logger.error("Error al enviar la consulta", ex);
            return ERROR;
        }
        return SUCCESS;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
