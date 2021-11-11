package eventlistener.service.mail;

import eventlistener.model.event.EventContentDTO;
import eventlistener.model.notificationuser.NotificationUser;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void handleMailAction(EventContentDTO eventDetails, List<NotificationUser> users) throws MailException {
        for (NotificationUser user : users) {
            String subject = eventDetails.getEnvironmentName() + " " + " Notification";
            sendMail(user.getEmail(), subject, eventDetails.getContent());
        }

    }

    private void sendMail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("events@zapliance.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

}
