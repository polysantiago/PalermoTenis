package com.palermotenis.jobs;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.palermotenis.infrastructre.testsupport.base.BaseSpringTestCase;

public class EnviarListadoStockJobTest extends BaseSpringTestCase {

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    @Value("${templates.mail.job.to}")
    private String to;

    @Value("${templates.mail.job.from}")
    private String from;

    @Value("${templates.mail.job.subject}")
    private String subject;

    @Value("${templates.mail.job.filename}")
    private String fileName;

    @Before
    public void setUp() {
        Mailbox.clearAll();
    }

    @Test
    public void testExecuteInternalJobExecutionContext() throws SchedulerException, InterruptedException,
            MessagingException, IOException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = scheduler.getJobDetail(new JobKey("enviarListadoStockJob"));

        scheduler.start();

        Trigger trigger = TriggerBuilder.newTrigger().startNow().forJob(jobDetail).build();
        scheduler.scheduleJob(trigger);

        Thread.sleep(1000);

        scheduler.shutdown();

        List<Message> inbox = Mailbox.get(to);
        assertNotNull(inbox);
        assertThat(inbox, is(not(empty())));
        assertEquals(1, inbox.size());

        MimeMessage message = (MimeMessage) inbox.get(0);
        Address address = message.getFrom()[0];
        assertEquals(from, address.toString());
        assertEquals(MessageFormat.format(subject, new Date()), message.getSubject());

        MimeMultipart content = (MimeMultipart) message.getContent();
        for (int i = 0; i < content.getCount(); i++) {
            MimeBodyPart mimeBodyPart = (MimeBodyPart) content.getBodyPart(i);
            if (Part.ATTACHMENT.equals(mimeBodyPart.getDisposition())) {
                assertThat(mimeBodyPart.getFileName(), equalTo(MessageFormat.format(fileName, new Date())));
                assertThat(mimeBodyPart.getContentType(), startsWith("application/vnd.ms-excel"));
            }
        }

    }

}
