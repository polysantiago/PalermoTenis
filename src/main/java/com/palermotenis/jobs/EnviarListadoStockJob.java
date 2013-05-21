package com.palermotenis.jobs;

import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.palermotenis.model.service.exporters.ExcelExporterService;

public class EnviarListadoStockJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger(EnviarListadoStockJob.class);
    private static final String DATE_NOW = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    // private static final String DATE_NOW_NO_CONFLICT = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
    private static final String TIME_NOW = new SimpleDateFormat("HH:mm:ss").format(new Date());
    // private static final String FILENAME = "ListadoDeStock" + DATE_NOW_NO_CONFLICT + ".xls";
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String[] HEADERS =
        { "Marca", "Producto", "Valor Clasificatorio", "Presentación", "Stock", "Pago", "Moneda", "Precio",
                "En Oferta", "Oferta" };

    private ExcelExporterService excelExporterService;

    private JavaMailSender mailSender;

    private String to;

    private String from;

    private String subject;

    private String fileName;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            ByteArrayOutputStream out = excelExporterService.export(HEADERS);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText("Se adjunta listado de stock al " + DATE_NOW + " a las " + TIME_NOW);
            helper.addAttachment(fileName, new ByteArrayResource(out.toByteArray()), CONTENT_TYPE);
            mailSender.send(message);

            IOUtils.closeQuietly(out);

        } catch (MessagingException ex) {
            LOGGER.error("No se pudo enviar el listado de stock", ex);
        }
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = MessageFormat.format(subject, new Date());
    }

    public void setFileName(String fileName) {
        this.fileName = MessageFormat.format(fileName, new Date());
    }

    @Required
    public void setExcelExporterService(ExcelExporterService excelExporterService) {
        this.excelExporterService = excelExporterService;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
