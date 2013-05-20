/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.palermotenis.jobs;

import com.palermotenis.model.services.exporters.impl.ExcelExporterServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author Poly
 */
public class EnviarListadoStockJob {

    private ExcelExporterServiceImpl excelExporter;
    private JavaMailSender mailSender;
    private static final Logger logger = Logger.getLogger(EnviarListadoStockJob.class);
    private static final String DATE_NOW =  new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_NOW_NO_CONFLICT =  new SimpleDateFormat("dd_MM_yyyy").format(new Date());
    private static final String TIME_NOW = new SimpleDateFormat("HH:mm:ss").format(new Date());
    private static final String FILENAME = "ListadoDeStock" + DATE_NOW_NO_CONFLICT + ".xls";
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";
    private static final String[] HEADERS = {"Marca", "Producto", "Valor Clasificatorio", "Presentación", "Stock", "Pago", "Moneda", "Precio", "En Oferta", "Oferta"};

    public void doIt(){
        try {
            logger.info("Enviando listado de stock...");
            excelExporter.setHeaders(HEADERS);
            ByteArrayOutputStream out = (ByteArrayOutputStream) excelExporter.export();
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("matias@palermotenis.com.ar");
            helper.setFrom("PalermoTenis <noreply@palermotenis.com.ar>");
            helper.setSubject("Listado de stock - " + DATE_NOW);
            helper.setText("Se adjunta listado de stock al " + DATE_NOW + " a las " + TIME_NOW);
            helper.addAttachment(FILENAME, new ByteArrayResource(out.toByteArray()),CONTENT_TYPE);
            mailSender.send(message);
            out.close();
            logger.info("Se enviado el listado de stock con éxito!");
        } catch (IOException ex) {
            logger.error("No se pudo enviar el listado de stock", ex);
        } catch (MessagingException ex) {
            logger.error("No se pudo enviar el listado de stock", ex);
        }
    }

    public void setExcelExporter(ExcelExporterServiceImpl excelExporter) {
        this.excelExporter = excelExporter;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
