/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

package eventlistener.service.mail;

import eventlistener.model.event.EventContentDTO;
import eventlistener.model.notificationuser.NotificationUser;

import org.springframework.mail.MailSendException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class MailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void handleMailAction(EventContentDTO eventDetails, List<NotificationUser> users){
        for (NotificationUser user : users) {
            String content = templateEngine.process("event", setMailContext(user, eventDetails));
            String subject = eventDetails.getEnvironmentName() + " " + " Notification";
            try{
                sendMail(subject, content, user);
            }catch (MessagingException e) {
                throw new MailSendException("Failure sending Mail for Event with ID: "+eventDetails.getId(), e);
            }
        }

    }
    private Context setMailContext (NotificationUser user, EventContentDTO event){
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("event", event);
        return context;
    }

    private void sendMail(String subject, String content, NotificationUser user) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setFrom("events@zapliance.com");
        helper.setText(content, true);
        helper.setTo(user.getEmail());
        emailSender.send(mimeMessage);

    }

}
